package kg.zakharov.payments.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.zakharov.payments.model.enums.Currency;
import kg.zakharov.payments.model.enums.PaymentMethod;
import kg.zakharov.payments.model.enums.PaymentType;
import kg.zakharov.payments.model.objects.BillingAddress;
import kg.zakharov.payments.model.objects.Card;
import kg.zakharov.payments.model.objects.Customer;
import kg.zakharov.payments.model.objects.Subscription;

import java.math.BigDecimal;
import java.util.Map;

public record PaymentRequest(
        String referenceId,
        @NotNull(message = "The PaymentType can't be null")
        PaymentType paymentType,
        PaymentMethod paymentMethod,
        @Positive(message = "The Amount must be greater than 0")
        BigDecimal amount,
        @NotNull(message = "The Currency can't be null")
        Currency currency,
        String parentPaymentId,
        String description,
        Card card,
        Customer customer,
        BillingAddress billingAddress,
        String returnUrl,
        String webhookUrl,
        Boolean startRecurring,
        String recurringToken,
        Subscription subscription,
        Map<String, String> additionalParameters) {
}
