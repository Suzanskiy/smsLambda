package com.tiashop.entity;


import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@RequiredArgsConstructor

public class ServiceRequest {

	String merchantAccount;
	String orderReference;
	String merchantSignature;
	Double amount;
	String currency;
	String authCode;
	String email;
	String phone;
	Integer createdDate;
	Integer processingDate;
	String cardPan;
	String cardType;
	String issuerBankCountry;
	String issuerBankName;
	String recToken;
	String transactionStatus;
	String reason;
	String reasonCode;
	Double fee;
	String paymentSystem;
	String acquirerBankName;
	String cardProduct;
	String clientName;
	String orderNo;
	String repayUrl;
	String deliveryPhone;
	String clientPhone;
}
