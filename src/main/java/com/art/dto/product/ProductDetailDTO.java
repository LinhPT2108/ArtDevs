package com.art.dto.product;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
	private int id;
	private int quantityInStock;
	private String size;
	private String color;
	private double weight;
	private double power;
	private Date productionDate;
	private List<PriceDTO> prices;
	private double discountPrice;
}