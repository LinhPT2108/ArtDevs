package com.art.dto.promotion;

import java.util.List;

import com.art.dto.product.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
@AllArgsConstructor
public class PromotionalDetailsListDTO {
    private int id;
    private ProductDTO product;
    private double discountedPrice;
    private double discountedQuantity;
    private int quantitySold;
    private Boolean status;
}
