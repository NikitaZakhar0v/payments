package kg.zakharov.payments.service.deposit;

import kg.zakharov.payments.model.request.PaymentRequest;

public interface DepositService {
    String getUrlForRedirect(PaymentRequest request);
}
