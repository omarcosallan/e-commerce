package dev.marcos.ecommerce.exception;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String path;
    private HttpStatus error;
    private int statusCode;

    private Map<String, Object> properties;

    public ErrorResponse(LocalDateTime timestamp, String message, String path, HttpStatus error, int statusCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.error = error;
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getError() {
        return error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setProperty(String key, Object value) {
        this.properties = (Map<String, Object>) (this.properties != null ? this.properties : new LinkedHashMap());
        this.properties.put(key, value);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return this.properties;
    }
}
