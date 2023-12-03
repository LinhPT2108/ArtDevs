package com.art.dto.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.art.dto.account.CartDTO;
import com.art.models.user.InforAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class orderDTO {
    private List<CartDTO> cartDTO;
    private BigDecimal totalAmount;
    private String userId;
    private InforAddress deliveryAddress;
    private String note;
    private Date expectedDeliveryTime;
}
