package com.example.ecommercebasic.controller.payment;


import com.example.ecommercebasic.dto.payment.InstallmentInfoDto;
import com.example.ecommercebasic.dto.product.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebasic.service.payment.PaymentService;
import com.iyzipay.model.InstallmentInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> payCallBack(@RequestParam Map<String, String> collections) {
        return new ResponseEntity<>(paymentService.payCallBack(collections),HttpStatus.OK);
    }

    @GetMapping("/bin")
    public ResponseEntity<InstallmentInfoDto> getBin(@RequestParam String bin) {
        return new ResponseEntity<>(paymentService.getBin(bin),HttpStatus.OK);
    }



}
