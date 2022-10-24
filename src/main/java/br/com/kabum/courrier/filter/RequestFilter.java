package br.com.kabum.courrier.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Filter class to show logs of the request and response in the console for the API.
 */
@Component
@WebFilter(urlPatterns = "/*")
@Order(-999)
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
    private final Boolean includeResponsePayload = true;

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
        if (requestBody.length() > 0){
            log.info("Request body:\n{}", requestBody);
        }

        String responseBody = this.getContentAsString(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding());
        if (responseBody.length() > 0 && Boolean.TRUE.equals(includeResponsePayload)){
            log.info("Response body:\n{}", responseBody);
        }

        wrappedResponse.copyBodyToResponse();

    }
    private String getContentAsString(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {

        if(contentAsByteArray == null || contentAsByteArray.length == 0){
            return "";
        }
        return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
    }

}
