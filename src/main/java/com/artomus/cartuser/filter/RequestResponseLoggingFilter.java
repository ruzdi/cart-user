package com.artomus.cartuser.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(urlPatterns = "/*")
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // Wrap the request to cache the body
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);

        // Log request details
        logger.info("Incoming request data:");
        logger.info("Request URI: " + wrappedRequest.getRequestURI());
        logger.info("Method: " + wrappedRequest.getMethod());
        logger.info("Query String: " + wrappedRequest.getQueryString());

        // Log headers
        wrappedRequest.getHeaderNames().asIterator().forEachRemaining(headerName ->
                logger.info("Header: {} = {}", headerName, wrappedRequest.getHeader(headerName)));

        // Pass the wrapped request along the filter chain
        chain.doFilter(wrappedRequest, response);

        // Now log the request body after the request has been processed
        byte[] contentAsByteArray = wrappedRequest.getContentAsByteArray();
        if (contentAsByteArray.length > 0) {
            String body = new String(contentAsByteArray, StandardCharsets.UTF_8);
            logger.info("Body: " + body);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
