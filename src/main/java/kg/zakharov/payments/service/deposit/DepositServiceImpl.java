package kg.zakharov.payments.service.deposit;

import kg.zakharov.payments.client.DepositRestClient;
import kg.zakharov.payments.model.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class DepositServiceImpl implements DepositService {
    private final DepositRestClient depositRestClient;

    public DepositServiceImpl(DepositRestClient depositRestClient) {
        this.depositRestClient = depositRestClient;
    }


    @Override
    public String getUrlForRedirect(PaymentRequest paymentRequest) {
        return depositRestClient.getDepositResponse(paymentRequest).redirectUrl();

    }
}
