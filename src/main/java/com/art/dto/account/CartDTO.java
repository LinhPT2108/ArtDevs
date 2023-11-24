package com.art.dto.account;

import com.art.dto.product.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	private int cartId;
	private ProductDTO productDTO;
	private int quantityInCart;
}
