package com.dinet.carga_pedido.infraestructure.adapter.in.web.exception;

import com.dinet.carga_pedido.application.exception.ServiceException;
import com.dinet.carga_pedido.shared.dto.ErrorResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {

        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorResponse error = ErrorResponse.builder()
                .code(statusCode)
                .message(ex.getMessage())
                .correlationId(MDC.get("correlationId"))
                .build();

        return ResponseEntity.status(statusCode).body(error);
    }


}

