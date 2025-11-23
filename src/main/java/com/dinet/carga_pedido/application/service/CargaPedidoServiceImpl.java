package com.dinet.carga_pedido.application.service;

import com.dinet.carga_pedido.application.validator.IdempotencyValidatorService;
import com.dinet.carga_pedido.application.validator.PedidoValidatorService;
import com.dinet.carga_pedido.domain.model.CargaIdempotenciaModel;
import com.dinet.carga_pedido.domain.model.ClienteModel;
import com.dinet.carga_pedido.domain.model.PedidoModel;
import com.dinet.carga_pedido.domain.model.ZonaModel;
import com.dinet.carga_pedido.domain.port.out.*;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.entity.ClienteEntity;
import com.dinet.carga_pedido.infraestructure.adapter.out.persistence.mapper.PedidoMapper;
import com.dinet.carga_pedido.shared.dto.FilaDetalle;
import com.dinet.carga_pedido.shared.dto.ResumenDto;
import com.dinet.carga_pedido.application.port.in.CargaPedidoService;
import com.dinet.carga_pedido.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CargaPedidoServiceImpl implements CargaPedidoService {

    private final FileReader fileReader;

    private final PedidoValidatorService pedidoValidatorService;
    private final IdempotencyValidatorService idempotencyValidatorService;
    private final PedidoRepository pedidoRepository;
    private final ZonaRepository zonaRepository;
    private final CargaIdempotenciaRepository cargaIdempotenciaRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper pedidoMapper;

    @Value("${pedidos.batch.size}")
    private int batchSize;

    @Autowired
    public CargaPedidoServiceImpl(
            FileReader fileReader,
            PedidoValidatorService pedidoValidatorService,
            IdempotencyValidatorService idempotencyValidatorService,
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ZonaRepository zonaRepository,
            CargaIdempotenciaRepository cargaIdempotenciaRepository,
            PedidoMapper pedidoMapper) {
        this.fileReader = fileReader;
        this.pedidoValidatorService = pedidoValidatorService;
        this.idempotencyValidatorService = idempotencyValidatorService;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.zonaRepository = zonaRepository;
        this.cargaIdempotenciaRepository = cargaIdempotenciaRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public ResumenDto cargarPedidosDesdeCsv(MultipartFile multipartFile, String idempotencyKey) throws Exception {
        AtomicInteger procesados = new AtomicInteger(0);
        AtomicInteger guardados = new AtomicInteger(0);
        AtomicInteger conError = new AtomicInteger(0);

        Map<String, Integer> erroresPorTipo = new HashMap<>();
        List<FilaDetalle> errores = new ArrayList<>();
        List<PedidoModel> batch = new ArrayList<>();

        byte[] archivoBytes = multipartFile.getBytes();
        String hashCode = HashUtil.calcularSHA256(archivoBytes);
        idempotencyValidatorService.validar(idempotencyKey, hashCode);

        Set<String> clienteIds = new HashSet<>();
        Set<String> zonaIds = new HashSet<>();
        Set<String> numeroPedidosCSV = new HashSet<>();

        fileReader.readStream(multipartFile.getInputStream(), dto -> {
            clienteIds.add(dto.getClienteId());
            zonaIds.add(dto.getZonaEntrega());
            numeroPedidosCSV.add(dto.getNumeroPedido());
        });

        Set<String> pedidosExistentes = new HashSet<>(pedidoRepository.findAllNumeroPedidos());
        Map<String, ClienteModel> clientesMap = clienteRepository.findByIdIn(clienteIds)
                .stream().collect(Collectors.toMap(ClienteModel::getId, c -> c));
        Map<String, ZonaModel> zonasMap = zonaRepository.findByIdIn(zonaIds)
                .stream().collect(Collectors.toMap(ZonaModel::getId, z -> z));

        try {
            fileReader.readStream(multipartFile.getInputStream(), pedido -> {
                procesados.incrementAndGet();

                try {

                    //pedidoValidatorService.validarConDb(pedido);

                    if (pedidosExistentes.contains(pedido.getNumeroPedido()) || batch.stream().anyMatch(p -> p.getNumeroPedido().equals(pedido.getNumeroPedido()))) {
                        throw new RuntimeException("DUPLICADO: El numero de pedido "+pedido.getNumeroPedido()+" ya existe");
                    }

                    if (!clientesMap.containsKey(pedido.getClienteId())) {
                        throw new RuntimeException("CLIENTE_NO_ENCONTRADO: el cliente con id "+pedido.getClienteId()+" no existe");
                    }

                    if (!zonasMap.containsKey(pedido.getZonaEntrega())) {
                        throw new RuntimeException("ZONA_INVALIDA: la zona con id "+pedido.getZonaEntrega()+" no existe");
                    }

                    ZonaModel zona = zonasMap.get(pedido.getZonaEntrega());
                    if (zona.isSoporteRegrigeracion() && !zona.isSoporteRegrigeracion()) {
                        throw new RuntimeException("CADENA_FRIO_NO_SOPORTADA: la zona con id "+pedido.getZonaEntrega()+" no tiene soporte de refrigeracion");
                    }

                    PedidoModel pedidoModel = pedidoMapper.dtoToDomain(pedido);
                    pedidoModel.validarNegocio();

                    batch.add(pedidoModel);
                    if (batch.size() >= batchSize) {
                        pedidoRepository.saveAll(batch);
                        batch.clear();
                    }

                    guardados.incrementAndGet();

                } catch (Exception e) {
                    conError.incrementAndGet();
                    errores.add(new FilaDetalle(procesados.get(), e.getMessage()));

                    String tipoError = clasificarError(e);
                    contarError(erroresPorTipo, tipoError);
                }
            });

            if (!batch.isEmpty()) {
                pedidoRepository.saveAll(batch);
            }

            if (guardados.get() > 0) {
                CargaIdempotenciaModel cargaIdempotenciaModel = new CargaIdempotenciaModel();
                cargaIdempotenciaModel.setArchivoHash(hashCode);
                cargaIdempotenciaModel.setCreatedAt(LocalDateTime.now());
                cargaIdempotenciaModel.setIdempotencyKey(idempotencyKey);

                cargaIdempotenciaRepository.saveCargaIdempotencia(cargaIdempotenciaModel);
            }


        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

        ResumenDto resumen = new ResumenDto();
        resumen.setTotalProcesados(procesados.get());
        resumen.setTotalGuardados(guardados.get());
        resumen.setTotalConError(conError.get());
        resumen.setFilaDetalleList(errores);
        resumen.setErroresPorTipo(erroresPorTipo);

        log.info("Flujo de carga de pedido se completo exitosamente");
        return resumen;
    }

    private void contarError(Map<String, Integer> mapa, String tipo) {
        mapa.put(tipo, mapa.getOrDefault(tipo, 0) + 1);
    }

    private String clasificarError(Exception e) {
        String msg = e.getMessage();

        if (msg.contains("NUMERO_PEDIDO_INVALIDO")) return "NUMERO_PEDIDO_INVALIDO";
        if (msg.contains("ESTADO_INVALIDO")) return "ESTADO_INVALIDO";
        if (msg.contains("FECHA_INVALIDA")) return "FECHA_INVALIDA";
        if (msg.contains("FECHA_ENTREGA_PASADA")) return "FECHA_ENTREGA_PASADA";
        if (msg.contains("DUPLICADO")) return "DUPLICADO";
        if (msg.contains("CLIENTE_NO_ENCONTRADO")) return "CLIENTE_NO_ENCONTRADO";
        if (msg.contains("ZONA_INVALIDA")) return "ZONA_INVALIDA";
        if (msg.contains("CADENA_FRIO_NO_SOPORTADA")) return "CADENA_FRIO_NO_SOPORTADA";

        return "DESCONOCIDO";
    }

}
