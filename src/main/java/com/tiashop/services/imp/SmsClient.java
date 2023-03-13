package com.tiashop.services.imp;

import com.tiashop.HttpClient;
import com.tiashop.dao.SmsMessage;

import okhttp3.Request;


public class SmsClient extends HttpClient {

	private static final String SMS_SERVICE_PROVIDER_URL = "https://im.smsclub.mobi/sms/send";

	public void sendSmsMsg(SmsMessage sms) {
		Request request = new Request.Builder().url(SMS_SERVICE_PROVIDER_URL).headers(getRequiredHttpHeaders()).post(
				createRequestBody(getGson().toJson(sms))).build();
		checkSuccessResponse(request);
	}
}
