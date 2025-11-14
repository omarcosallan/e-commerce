package dev.marcos.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marcos.ecommerce.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    public CustomAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse pd = new ErrorResponse(LocalDateTime.now(), "Você não tem permissão para acessar este recurso", request.getRequestURI(), status, status.value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        response.getWriter().write(mapper.writeValueAsString(pd));
    }
}
