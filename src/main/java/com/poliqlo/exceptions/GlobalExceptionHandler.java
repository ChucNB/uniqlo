package com.poliqlo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String message = String.format("Tham số không hợp lệ", field);

        return ResponseEntity
                .badRequest()
                .body(Map.of("error", message));
    }

    // Xử lý lỗi truyền thiếu @RequestParam hoặc @PathVariable
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParam(MissingServletRequestParameterException ex) {
        String message = String.format("Thiếu tham số bắt buộc: %s", ex.getParameterName());
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", message));
    }

    // Xử lý lỗi validation từ @Valid + BindingResult
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", message));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public Object handleAuthorizationDeniedException(AuthorizationDeniedException ex) {

        return new RedirectView("/error/403.html");

    }

    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception ex) {
        String accept = request.getHeader("Accept");
        ex.printStackTrace();

        if (accept != null && accept.contains("text/html")) {
            // Gửi redirect tới trang HTML tĩnh
            return new RedirectView("/error/500.html");
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Có lỗi xảy ra, vui lòng thử lại sau.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
