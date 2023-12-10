package com.art.dto.vnpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class vnpayDTO {
    private int amount;
    private String orderInfo;
}
