package kr.strato.cloudinterface.common.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Log4j2
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    private HttpHeaders headers;

    public RestTemplateHeaderModifierInterceptor(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().addAll(headers);
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }
}
