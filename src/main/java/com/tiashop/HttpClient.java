package com.tiashop;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.*;


public abstract class HttpClient {

	private static final String SMS_CLUB_TOKEN = System.getenv("SMS_CLUB_TOKEN");
	protected final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	private Gson gson;

	public Headers getRequiredHttpHeaders() {
		return new Headers.Builder().add("Authorization", "Bearer " + SMS_CLUB_TOKEN).add("Content-Type",
				"application/json").build();
	}

	private static OkHttpClient okHttpClient;

	public HttpClient() {
		gson = new GsonBuilder().create();
	}

	public Gson getGson() {
		return gson;
	}

	protected OkHttpClient getHttpClient() {
		if (okHttpClient == null) {
			okHttpClient = new OkHttpClient();
		}
		return okHttpClient;
	}

	protected RequestBody createRequestBody(String json) {
		return RequestBody.create(JSON, json);
	}


	public Response checkSuccessResponse(Request request) {
		Response response = null;
		try {
			response = getHttpClient().newCall(request).execute();
		} catch (IOException exception) {
			System.out.println(exception);
		}
		return response;
	}

}
