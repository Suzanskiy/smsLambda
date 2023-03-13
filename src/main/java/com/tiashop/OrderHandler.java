package com.tiashop;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.tiashop.entity.ServiceRequest;
import com.tiashop.entity.ServiceResponse;
import com.tiashop.services.SmsService;
import com.tiashop.services.imp.SmsServiceDefault;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class OrderHandler implements RequestHandler<ServiceRequest, ServiceResponse> {
	public static final String MERCHANT_SECRET = System.getenv("MERCHANT_SECRET");
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	SmsService smsService = new SmsServiceDefault();

	@Override
	public ServiceResponse handleRequest(ServiceRequest requestEvent, Context context) {
		Util.logEnvironment(requestEvent, context, gson);

		smsService.sendSms(requestEvent);

		ServiceResponse response = calculateServiceResponse(requestEvent);
		Util.logEnvironment(response, context, gson);
		return response;
	}

	private ServiceResponse calculateServiceResponse(ServiceRequest request) {
		String orderReference = request.getOrderReference();
		String status = "accept";
		Long time = System.currentTimeMillis() / 1000;
		String data = orderReference.concat(";").concat(status).concat(";").concat(String.valueOf(time));
		String signature = stringToHMACMD5(data, MERCHANT_SECRET);
		return new ServiceResponse(orderReference, status, time, signature);
	}

	public static String stringToHMACMD5(String s, String keyString) {
		String sEncodedString = null;
		final String algorithm = "HmacMD5";

		try {
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes(StandardCharsets.UTF_8), algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(key);

			byte[] bytes = mac.doFinal(s.getBytes(StandardCharsets.US_ASCII));

			StringBuffer hash = new StringBuffer();

			for (byte aByte : bytes) {
				String hex = Integer.toHexString(0xFF & aByte);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			sEncodedString = hash.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
		}
		return sEncodedString;
	}
}