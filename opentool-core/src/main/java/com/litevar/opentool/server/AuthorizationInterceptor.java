package com.litevar.opentool.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    
    @Value("#{'${opentool.api-key:}'.split(',')}")
    private List<String> apiKeys;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        logger.debug("Processing authorization for {} {}", method, requestUri);
        
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String apiKey = extractApiKey(authorization);

        if (!isValidApiKey(apiKey)) {
            logger.warn("Unauthorized access attempt to {} {} - invalid API key", method, requestUri);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        logger.debug("Authorization successful for {} {}", method, requestUri);
        return true;
    }

    private String extractApiKey(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private boolean isValidApiKey(String providedApiKey) {
        if (providedApiKey == null) {
            logger.debug("No API key provided");
            return false;
        }

        if (apiKeys == null || apiKeys.isEmpty()) {
            logger.debug("No API keys configured, allowing access");
            return true;
        }

        boolean isValid = apiKeys.contains(providedApiKey);
        if (!isValid) {
            logger.debug("Provided API key does not match any configured keys");
        }
        return isValid;
    }
}