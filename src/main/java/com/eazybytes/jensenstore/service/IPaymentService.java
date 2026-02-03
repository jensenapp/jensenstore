package com.eazybytes.jensenstore.service;


import com.eazybytes.jensenstore.dto.PaymentIntentRequestDto;
import com.eazybytes.jensenstore.dto.PaymentIntentResponseDto;

public interface IPaymentService {

    PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto requestDto);
}
