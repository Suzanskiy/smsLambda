package com.tiashop.domain;


import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class Sms {

	@JsonIgnore
	private Integer id;
	String phone;
	String message;
	String src_addr;

}
