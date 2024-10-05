package com.github.bruce_mig.paypal_integration.service;

import com.github.bruce_mig.paypal_integration.dto.CreatePaymentRequestDTO;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final APIContext apiContext;

    public Payment createPayment(
            CreatePaymentRequestDTO paymentRequestDTO,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(paymentRequestDTO.currency());
        amount.setTotal(String.format(Locale.forLanguageTag(paymentRequestDTO.currency()),"%.2f",paymentRequestDTO.total()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentRequestDTO.description());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentRequestDTO.method());

        Payment payment = new Payment();
        payment.setIntent(paymentRequestDTO.intent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
