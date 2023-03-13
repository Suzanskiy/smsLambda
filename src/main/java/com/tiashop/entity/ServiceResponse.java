package com.tiashop.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ServiceResponse {
    String orderReference;
    String status;
    Long time;
    String signature;
}
