package kg.zakharov.payments.controller;

import kg.zakharov.payments.model.request.PaymentRequest;
import kg.zakharov.payments.service.PaymentProcessingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PaymentController {
    private final PaymentProcessingServiceImpl processingService;

    public PaymentController(PaymentProcessingServiceImpl processingService) {
        this.processingService = processingService;
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @PostMapping("/payment")
    public String getUserLimitWithdrawPage(@Validated PaymentRequest request) {
        return "redirect:" + processingService.doProcess(request);
    }
}
