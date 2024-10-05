package com.github.bruce_mig.paypal_integration.service;

import com.github.bruce_mig.paypal_integration.dto.CreatePaymentRequestDTO;
import com.github.bruce_mig.paypal_integration.exceptions.PaymentFailedException;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequiredArgsConstructor
@Slf4j
public class PayPalController {

    public static final String USD = "USD";
    public static final String APPROVAL_URL = "approval_url";
    @Value("${paypal.cancel-url}")
    private String cancelUrl;

    @Value("${paypal.success-url}")
    private String successUrl;

    private final PayPalService payPalService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("intent") String intent,
            @RequestParam("description") String description
            ){

        CreatePaymentRequestDTO paymentRequest = CreatePaymentRequestDTO.builder()
                    .total(Double.valueOf(amount))
                    .currency(currency)
                    .method(method)
                    .intent(intent)
                    .description(description)
                    .build();
        try {
            Payment payment = payPalService.createPayment(
                    paymentRequest,
                    cancelUrl,
                    successUrl
            );

            for (Links links: payment.getLinks()){
                if (links.getRel().equals(APPROVAL_URL)) {
                    return new RedirectView(links.getHref());
                }
            }


        } catch (Exception e){
            log.error("Error occurred: ", e);
            throw new PaymentFailedException(String.format("Error occurred: %s", e.getMessage()));
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = payPalService.executePayment(paymentId,payerId);
            if (payment.getState().equals("approved")){
                return "paymentSuccess";
            }
        } catch (Exception e) {
            log.error("Error occurred: ", e);
            throw new PaymentFailedException(String.format("Error occurred: %s", e.getMessage()));

        }
        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "paymentError";
    }

}
