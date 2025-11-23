package com.dinet.carga_pedido.application.validator;

import com.dinet.carga_pedido.domain.port.out.ClienteRepository;
import com.dinet.carga_pedido.domain.port.out.PedidoRepository;
import com.dinet.carga_pedido.domain.port.out.ZonaRepository;
import com.dinet.carga_pedido.shared.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoValidatorService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ZonaRepository zonaRepository;

    @Autowired
    public PedidoValidatorService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ZonaRepository zonaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.zonaRepository = zonaRepository;
    }

    public void validarConDb(PedidoDto dto) {
        validarDuplicadoBD(dto.getNumeroPedido());
        validarClienteExiste(dto.getClienteId());
        validarZonaExiste(dto.getZonaEntrega());
        validarRefrigeracion(dto.getZonaEntrega(), dto.isRequiereRefrigeracion());
    }

    private void validarDuplicadoBD(String numeroPedido) {
        if (pedidoRepository.existsByNumeroPedido(numeroPedido)) {
            throw new RuntimeException("DUPLICADO: El numero de pedido "+numeroPedido+" ya existe");
        }
    }

    public void validarClienteExiste(String clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("CLIENTE_NO_ENCONTRADO: el cliente con id "+clienteId+" no existe");
        }
    }

    public void validarZonaExiste(String zonaId) {
        if (!zonaRepository.existsById(zonaId)) {
            throw new RuntimeException("ZONA_INVALIDA: la zona con id"+zonaId+" no existe");
        }
    }

    public void validarRefrigeracion(String zonaId, boolean refrigeracion) {
        if(refrigeracion) {
            if(!zonaRepository.getSoporteRefrigeracion(zonaId)) {
                throw new RuntimeException("CADENA_FRIO_NO_SOPORTADA: la zona con id"+zonaId+" no tiene soporte de refrigeracion");
            };
        }
    }

}
