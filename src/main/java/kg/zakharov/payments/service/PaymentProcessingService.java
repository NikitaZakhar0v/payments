package kg.zakharov.payments.service;

import kg.zakharov.payments.model.request.PaymentRequest;

public interface PaymentProcessingService {
    String doProcess(PaymentRequest request);
}
