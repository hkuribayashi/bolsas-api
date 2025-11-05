package org.isaci.bolsas_api.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;

@Controller
public class ApiErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ApiErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(WebRequest request) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(
                request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        return ResponseEntity
                .status((int) body.getOrDefault("status", 500))
                .body(body);
    }
}

