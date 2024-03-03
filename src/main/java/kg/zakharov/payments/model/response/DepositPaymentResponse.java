package kg.zakharov.payments.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;

@NotNull(message = "DepositPaymentResponse can't be null")
public record DepositPaymentResponse(
        String timestamp,
        int status,
        String error,
        String message,
        List<HashMap<String, Object>> errors,
        String path,
        Result result) {
    @NotNull(message = "Deposit response body has null result")
    public record Result(
            String id,
            String paymentType,
            String state,
            int amount,
            String currency,
            @NotBlank(message = "Deposit redirect URL is null or empty, can't redirect")
            String redirectUrl) {
    }
}
