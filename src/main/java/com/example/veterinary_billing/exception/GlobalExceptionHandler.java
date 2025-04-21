package com.example.veterinary_billing.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEventNotFoundException(InvoiceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Factura no encontrada");
        errorResponse.put("message", ex.getMessage()); // Aquí se incluye el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleServiceNotFoundException(ServiceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Servicio no encontrado");
        errorResponse.put("message", ex.getMessage()); // Aquí se incluye el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
