package com.tiashop.services;


import com.tiashop.dao.SmsMessage;
import com.tiashop.entity.ServiceRequest;


public interface SmsService {

	void sendSms(ServiceRequest request);

	SmsMessage validateRecipient(ServiceRequest request);

}
