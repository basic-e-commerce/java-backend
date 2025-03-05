package com.example.ecommercebasic.controller.payment;


import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.service.payment.PaymentService;
import com.iyzipay.model.InstallmentInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> processCreditCardPayment(@RequestBody PaymentCreditCardRequestDto paymentCreditCardRequestDto,
                                                           HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(paymentService.processCreditCardPayment(paymentCreditCardRequestDto,httpServletRequest),HttpStatus.OK);
    }


    @PostMapping("/payCallBack")
    public void payCallBack(@RequestParam Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        paymentService.payCallBack(collections,httpServletResponse);
    }

    @GetMapping("/bin")
    public ResponseEntity<InstallmentInfoDto> getBin(@RequestParam String bin,@RequestParam BigDecimal amount) {
        return new ResponseEntity<>(paymentService.getBin(bin,amount),HttpStatus.OK);
    }



}
