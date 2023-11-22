package com.art.models.promotion;

import com.art.models.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PromotionalDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "flashSale_id")
	private FlashSale flashSale;

	@Column
	private double discountedPrice;
	
	@Column
	private double discountedQuantity;

	@Column
	private boolean status = true;

}
