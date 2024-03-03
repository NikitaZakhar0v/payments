package kg.zakharov.payments.service;

import kg.zakharov.payments.model.request.PaymentRequest;
import kg.zakharov.payments.service.deposit.DepositService;
import kg.zakharov.payments.service.refund.RefundService;
import kg.zakharov.payments.service.withdrawal.WithdrawalService;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessingServiceImpl implements PaymentProcessingService {
    private final DepositService depositService;
    private final WithdrawalService withdrawalService;
    private final RefundService refundService;

    public PaymentProcessingServiceImpl(DepositService depositService, WithdrawalService withdrawalService, RefundService refundService) {
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
        this.refundService = refundService;
    }


    @Override
    public String doProcess(PaymentRequest request) {
        return switch (request.paymentType()){
            case DEPOSIT -> depositService.getUrlForRedirect(request);
            case REFUND -> refundService.doSomething(request);
            case WITHDRAWAL -> withdrawalService.doSomething(request);
        };
    }
}
