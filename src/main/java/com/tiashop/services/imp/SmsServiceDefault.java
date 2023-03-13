package com.tiashop.services.imp;


import static com.tiashop.enums.TransactionStatuses.*;

import java.util.ArrayList;
import java.util.List;

import com.tiashop.dao.SmsMessage;
import com.tiashop.entity.ServiceRequest;
import com.tiashop.services.SmsService;


public class SmsServiceDefault implements SmsService {
	private static final String SMS_SENDER_NAME = System.getenv("SMS_SENDER_NAME");
	private final SmsClient smsClient = new SmsClient();

	public SmsServiceDefault() {
	}

	@Override
	public void sendSms(ServiceRequest entity) {
		SmsMessage message = validateRecipient(entity);
		if (message != null && message.getPhone() != null) {
			System.out.println("Sending sms: " + message);
			smsClient.sendSmsMsg(message);
		} else {
			System.out.println("SMS not validated");
		}
	}

	@Override
	public SmsMessage validateRecipient(ServiceRequest request) {
		SmsMessage sms = new SmsMessage();
		List<String> phones = new ArrayList<>();

		String transactionStatus = request.getTransactionStatus();
		Double amount = request.getAmount();
		String currency = request.getCurrency();

		if (transactionStatus != null) {
			switch (transactionStatus) {
				case APPROVED:
					sms.setMessage(
							"Замовлення на суму " + amount + "" + currency + " сплачено та прийнято в роботу. Дякуємо!");
					break;
				case PAID_ON_DELIVERY:
					sms.setMessage(
							"Замовлення на суму " + amount + "" + currency + " з оплатою при отриманні прийнято. Дякуємо!");
					phones.add(request.getClientPhone());
					break;
				case REFUNDED:
					sms.setMessage(
							"Повернення коштів на суму " + amount + "" + currency + "! Будемо раді бачити вас знову!");
					phones.add(request.getDeliveryPhone());
					break;
				case DECLINED:
					sms.setMessage("Упс.. щось не так з вашим замовленням! Будь-ласка, спробуйте ще раз!");
					break;
				case EXPIRED:
					sms.setMessage("Лише пару кліків до створення замовлення на https://tia-shop.com, не зволікайте!");
					break;
			}
		}
		if (sms.getMessage() == null) {
			System.out.println("No valid status");
			return null;
		}
		if (request.getPhone() != null) {
			phones.add(request.getPhone());
		}
		sms.setPhone(phones);
		sms.setSrc_addr(SMS_SENDER_NAME);
		if (sms.getMessage() != null) {
			return sms;
		} else {
			return null;
		}
	}
}
