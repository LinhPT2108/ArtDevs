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
public class ProductDTO {
	private String productId;
	private String productName;
	private boolean available = true;
	private Date createdDate = new Date();
	private CategoryDTO category;
	private ManufacturerDTO manufacturer;
	private List<String> images;
	private List<ProductDetailDTO> productDetails;
	private List<DetailDescriptionDTO> descriptions;
	private List<CommentDTO> comments;
	private boolean isSale = false;
	private double discountPrice;
	
}