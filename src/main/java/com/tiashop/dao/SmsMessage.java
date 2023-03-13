package com.tiashop.dao;

import java.util.List;

import lombok.Data;


@Data
public class SmsMessage {
    List<String> phone;
    String message;
    String src_addr;
}
