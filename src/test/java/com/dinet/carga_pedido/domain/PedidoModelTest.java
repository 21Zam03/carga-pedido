package com.dinet.carga_pedido.domain;

import com.dinet.carga_pedido.domain.model.PedidoModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoModelTest {

    @Test
    void validarNegocio_valido_noLanzaExcepcion() {
        PedidoModel pedido = new PedidoModel("ABC123", "CL01", "PENDIENTE",
                LocalDate.now().plusDays(0), "Lima", false);

        assertDoesNotThrow(pedido::validarNegocio);
    }

    @Test
    void validarNegocio_numeroPedidoInvalido_lanzaExcepcion() {
        PedidoModel pedido = new PedidoModel("ABC 123", "CL01", "PENDIENTE",
                LocalDate.now().plusDays(1), "Lima", false);

        RuntimeException e = assertThrows(RuntimeException.class, pedido::validarNegocio);
        assertEquals("NUMERO_PEDIDO_INVALIDO", e.getMessage());
    }

    @Test
    void validarNegocio_estadoInvalido_lanzaExcepcion() {
        PedidoModel pedido = new PedidoModel("ABC123", "CL01", "CANCELADO",
                LocalDate.now().plusDays(1), "Lima", false);

        RuntimeException e = assertThrows(RuntimeException.class, pedido::validarNegocio);
        assertEquals("ESTADO_INVALIDO", e.getMessage());
    }

    @Test
    void validarNegocio_fechaEntregaPasada_lanzaExcepcion() {
        PedidoModel pedido = new PedidoModel("ABC123", "CL01", "PENDIENTE",
                LocalDate.now().minusDays(1), "Lima", false);

        RuntimeException e = assertThrows(RuntimeException.class, pedido::validarNegocio);
        assertEquals("FECHA_ENTREGA_PASADA", e.getMessage());
    }

    @Test
    void validarNegocio_fechaEntregaNula_lanzaExcepcion() {
        PedidoModel pedido = new PedidoModel("ABC123", "CL01", "PENDIENTE",
                null, "Lima", false);

        RuntimeException e = assertThrows(RuntimeException.class, pedido::validarNegocio);
        assertEquals("FECHA_INVALIDA", e.getMessage());
    }

}
