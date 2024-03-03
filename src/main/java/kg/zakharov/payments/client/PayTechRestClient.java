package kg.zakharov.payments.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public abstract class PayTechRestClient {
    private final HttpClient httpClient;

    @Value("${PayTechRestClient.baseUrl}")
    private String baseUrl;
    @Value("${PayTechRestClient.token}")
    private String token;

    PayTechRestClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    HttpResponse<String> sendRequest(String path, HttpMethod method, Map<String, String> headers,
                                            Map<String, String> queryParams, String requestBody){

        path = buildPathWithParams(path, queryParams);

        HttpRequest.Builder requestBuilder = createHttpRequestBuilder(path, method, requestBody);

        addHeadersToRequest(requestBuilder, headers);

        HttpRequest request = requestBuilder.build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpClientErrorException(HttpStatus.REQUEST_TIMEOUT, e.getMessage());
        }
    }
    HttpResponse<String> sendRequest(String path, HttpMethod method, Map<String, String> headers, String requestBody){

        return sendRequest(path, method, headers, null, requestBody);
    }

    private HttpRequest.Builder createHttpRequestBuilder(String url, HttpMethod method, String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create((baseUrl + url)))
                .method(method.name(), HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Authorization", token);
    }

    private void addHeadersToRequest(HttpRequest.Builder requestBuilder, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }
    }

    private String buildPathWithParams(String path, Map<String, String> queryParams) {
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder queryParamsBuilder = new StringBuilder();
            queryParams.forEach((key, value) -> queryParamsBuilder.append("&").append(key).append("=").append(value));
            path += "?" + queryParamsBuilder.substring(1);
        }
        return path;
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD
    }

}
