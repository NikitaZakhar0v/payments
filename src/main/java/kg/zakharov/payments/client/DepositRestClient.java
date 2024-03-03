package kg.zakharov.payments.client;

import kg.zakharov.payments.model.request.PaymentRequest;
import kg.zakharov.payments.model.response.DepositPaymentResponse;
import kg.zakharov.payments.service.converter.JsonConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class DepositRestClient extends PayTechRestClient{
    private final JsonConverter jsonConverter;

    @Value("${PayTechRestClient.payments}")
    private String path;
    public DepositRestClient(HttpClient httpClient, JsonConverter jsonConverter) {
        super(httpClient);
        this.jsonConverter = jsonConverter;
    }

    public DepositPaymentResponse.Result getDepositResponse(PaymentRequest paymentRequest){
        String json = jsonConverter.toJson(paymentRequest);

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");

        HttpResponse<String> httpResponse = sendRequest(path, HttpMethod.POST, headers, json);

        HttpStatusCode statusCode = HttpStatusCode.valueOf(httpResponse.statusCode());
        if (statusCode != HttpStatus.OK) {
            String errorMessage = String.format("HTTP request failed with status code %d for Path: %s. With response %s",
                    statusCode.value(), path, httpResponse.body());
            throw new HttpClientErrorException(statusCode, errorMessage);
        }
        String body = Objects.requireNonNull(httpResponse.body(), "Answer has null body");

        DepositPaymentResponse depositPaymentResponse = jsonConverter.toModel(body, DepositPaymentResponse.class);

        return depositPaymentResponse.result();
    }

}
