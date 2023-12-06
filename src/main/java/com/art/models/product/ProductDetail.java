package com.art.models.product;

import java.util.Date;
import java.util.List;

import com.art.models.activity.Cart;
import com.art.models.activity.Comment;
import com.art.models.promotion.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int quantityInStock;

	@Column
	private String size;
	
	@Column
	private String color;
	
	@Column
	private double weight;
	
	@Column
	private double power;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date productionDate;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "productDetail")
	@JsonIgnore
	private List<Cart> productCarts;
	
	@OneToMany(mappedBy = "productDetail", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Price> productPrice;
	
	@OneToMany(mappedBy = "productDetail")
	@JsonIgnore
	private List<OrderDetail> productOrderDetail;

	@OneToMany(mappedBy = "productDetail")
	@JsonIgnore
	private List<Comment> productComment;

	public ProductDetail(int id, int quantityInStock, String size, String color, double weight, double power,
			Date productionDate, List<Price> productPrice) {
		this.id = id;
		this.quantityInStock = quantityInStock;
		this.size = size;
		this.color = color;
		this.weight = weight;
		this.power = power;
		this.productionDate = productionDate;
		this.productPrice = productPrice;
	}

	
}
