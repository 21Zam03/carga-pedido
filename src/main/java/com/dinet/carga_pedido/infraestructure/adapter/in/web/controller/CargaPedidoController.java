package com.dinet.carga_pedido.infraestructure.adapter.in.web.controller;

import com.dinet.carga_pedido.application.port.in.CargaPedidoService;
import com.dinet.carga_pedido.shared.dto.ResumenDto;
import com.dinet.carga_pedido.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(CargaPedidoController.API_PATH)
public class CargaPedidoController {

    public static final String API_PATH = "api";

    private final CargaPedidoService cargaPedidoService;
    private final JwtUtil jwtUtil;

    @Autowired
    public CargaPedidoController(CargaPedidoService cargaPedidoService, JwtUtil jwtUtil) {
        this.cargaPedidoService = cargaPedidoService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/carga-pedido/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResumenDto> cargarCsv(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        ResumenDto resumen = cargaPedidoService.cargarPedidosDesdeCsv(file, idempotencyKey);
        return ResponseEntity.ok(resumen);
    }

    @PostMapping("/token")
    ResponseEntity<String> getToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "Jose Zambrano");
        String token = jwtUtil.generateToken(claims);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
