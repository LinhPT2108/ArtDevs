package com.art.models.product;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.Data;
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
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	

	@OneToMany(mappedBy = "productDetail")
	@JsonIgnore
	private List<ProductCart> productCarts;
	
	@OneToMany(mappedBy = "productDetail", fetch = FetchType.EAGER)
	private List<Price> productPrice;

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
