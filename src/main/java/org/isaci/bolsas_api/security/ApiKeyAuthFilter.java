package org.isaci.bolsas_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.config.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private static final String HEADER_NAME = "X-API-KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Ignora requisições de preflight (CORS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        // ✅ Só valida a API key em rotas protegidas
        if (path.startsWith("/admin/")) {
            String requestApiKey = request.getHeader(HEADER_NAME);
            String configuredKey = securityProperties.getKey();

            if (configuredKey == null || configuredKey.isBlank()) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json");
                response.getWriter().write("""
                        {
                          "error": "ConfigurationError",
                          "message": "Server API key not configured."
                        }
                        """);
                return;
            }

            if (requestApiKey == null || !requestApiKey.equals(configuredKey)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("""
                        {
                          "error": "Unauthorized",
                          "message": "Missing or invalid API key"
                        }
                        """);
                return;
            }
        }

        // ✅ Continua o processamento normal
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 🔹 Também evita rodar o filtro para preflights
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}
