package com.employee.management.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final RestAuthenticationEntryPoint responseWriter;

    public RestAccessDeniedHandler(RestAuthenticationEntryPoint responseWriter) {
        this.responseWriter = responseWriter;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        responseWriter.write(response, HttpStatus.FORBIDDEN, "You do not have permission to perform this action",
                request.getRequestURI());
    }
}
