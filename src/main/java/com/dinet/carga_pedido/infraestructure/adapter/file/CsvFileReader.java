package com.dinet.carga_pedido.infraestructure.adapter.file;

import com.dinet.carga_pedido.domain.port.out.FileReader;
import com.dinet.carga_pedido.shared.dto.PedidoDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class CsvFileReader implements FileReader {

    @Override
    public List<PedidoDto> read(InputStream input) throws Exception {
        List<PedidoDto> pedidos = new ArrayList<>();

        try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {

            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true)
                    .setTrim(true)
                    .build();

            Iterable<CSVRecord> records = format.parse(reader);

            for (CSVRecord record : records) {
                PedidoDto dto = new PedidoDto();
                dto.setNumeroPedido(record.get("numeroPedido"));
                dto.setClienteId(record.get("clienteId"));
                dto.setFechaEntrega(LocalDate.parse(record.get("fechaEntrega")));
                dto.setEstado(record.get("estado"));
                dto.setZonaEntrega(record.get("zonaEntrega"));
                dto.setRequiereRefrigeracion(
                        Boolean.parseBoolean(record.get("requiereRefrigeracion"))
                );

                pedidos.add(dto);
            }
        }

        return pedidos;
    }

    @Override
    public void readStream(InputStream input, Consumer<PedidoDto> consumidor) throws IOException {
        try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {

            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true)
                    .setTrim(true)
                    .build();

            Iterable<CSVRecord> records = format.parse(reader);

            for (CSVRecord record : records) {
                PedidoDto dto = new PedidoDto();
                dto.setNumeroPedido(record.get("numeroPedido"));
                dto.setClienteId(record.get("clienteId"));
                dto.setFechaEntrega(LocalDate.parse(record.get("fechaEntrega")));
                dto.setEstado(record.get("estado"));
                dto.setZonaEntrega(record.get("zonaEntrega"));
                dto.setRequiereRefrigeracion(
                        Boolean.parseBoolean(record.get("requiereRefrigeracion"))
                );

                consumidor.accept(dto);
            }
        }
    }

}
