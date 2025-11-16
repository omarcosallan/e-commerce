package dev.marcos.ecommerce.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse error = toErrorResponse(e.getMessage(), req, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest req) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse error = toErrorResponse(e.getMessage(), req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse error = toErrorResponse("Dados ausentes ou inválidos", req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest req) {
        Map<String, String> errors = getErrorsDetails(e);
        ErrorResponse error = toErrorResponse("Dados inválidos", req, HttpStatus.BAD_REQUEST);
        error.setProperty("errors", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e, WebRequest req) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse error = toErrorResponse("Não é possível acessar este recurso", req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class
    })
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception e, WebRequest req) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponse error = toErrorResponse("Credenciais inválidas", req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e, WebRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse error = toErrorResponse("Rota não encontrada", req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, WebRequest req) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorResponse error = toErrorResponse("Método não suportado", req, status);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest req) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse error = toErrorResponse("Ocorreu um erro interno", req, status);
        return new ResponseEntity<>(error, status);
    }

    private ErrorResponse toErrorResponse(String message, WebRequest req, HttpStatus status) {
        return new ErrorResponse(
                LocalDateTime.now(),
                message,
                req.getDescription(false).replace("uri=", ""),
                status,
                status.value()
        );
    }

    private Map<String, String> getErrorsDetails(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
