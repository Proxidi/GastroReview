package WebSiters.GastroReview.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {


    private Map<String, Object> base(HttpStatus status, String message, HttpServletRequest req) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (req != null) body.put("path", req.getRequestURI());
        return body;
    }

    private ResponseEntity<Map<String, Object>> respond(HttpStatus status, String message, HttpServletRequest req) {
        return ResponseEntity.status(status).body(base(status, message, req));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> badJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return respond(HttpStatus.BAD_REQUEST, "JSON mal formado o incompatible", req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> badRequest(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validación fallida", req);
        Map<String, String> details = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        e -> (e instanceof FieldError fe) ? fe.getField() : e.getObjectName(),
                        e -> e.getDefaultMessage(),
                        (a, b) -> a
                ));
        body.put("details", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> constraint(ConstraintViolationException ex, HttpServletRequest req) {
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validación fallida", req);
        Map<String, String> details = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        v -> v.getMessage(),
                        (a, b) -> a
                ));
        body.put("details", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> typeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Tipo de parámetro inválido", req);
        body.put("details", Map.of(
                "param", ex.getName(),
                "requiredType", ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido",
                "value", String.valueOf(ex.getValue())
        ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(EntityNotFoundException ex, HttpServletRequest req) {
        return respond(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> noHandler(NoHandlerFoundException ex, HttpServletRequest req) {
        return respond(HttpStatus.NOT_FOUND, "Recurso no encontrado", req);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> methodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return respond(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), req);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> unsupportedMedia(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        return respond(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage(), req);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> conflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        String msg = "Conflicto con los datos (p. ej. valor duplicado)";
        Throwable cause = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause() : ex;
        String c = cause.getMessage() != null ? cause.getMessage() : "";
        if (c.contains("23505") || c.toLowerCase().contains("duplicate") || c.toLowerCase().contains("unique")) {
            msg = "Ya existe un registro con ese valor único";
        }
        return respond(HttpStatus.CONFLICT, msg, req);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> duplicate(DuplicateKeyException ex, HttpServletRequest req) {
        return respond(HttpStatus.CONFLICT, "Clave duplicada", req);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> rse(ResponseStatusException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String msg = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
        return ResponseEntity.status(status).body(base(status, msg, req));
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<Map<String, Object>> forbidden(HttpClientErrorException.Forbidden ex, HttpServletRequest req) {
        String msg = ex.getResponseBodyAsString();
        if (msg == null || msg.isBlank()) msg = ex.getStatusText();
        return respond(HttpStatus.FORBIDDEN, msg, req);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Map<String, Object>> notFoundClient(HttpClientErrorException.NotFound ex, HttpServletRequest req) {
        String msg = ex.getResponseBodyAsString();
        if (msg == null || msg.isBlank()) msg = "Recurso no encontrado";
        return respond(HttpStatus.NOT_FOUND, msg, req);
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<Map<String, Object>> conflictClient(HttpClientErrorException.Conflict ex, HttpServletRequest req) {
        String msg = ex.getResponseBodyAsString();
        if (msg == null || msg.isBlank()) msg = ex.getStatusText();
        return respond(HttpStatus.CONFLICT, msg, req);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception ex, HttpServletRequest req) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", req);
    }
}
