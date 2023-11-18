package com.art.models.product;

import java.util.Date;
import java.util.List;

import com.art.models.activity.Cart;
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
import lombok.NoArgsConstructor;

@Data
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
	
	@JsonBackReference("productDetailReference")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	@JsonIgnore
	private Cart cart;
	
	@OneToMany(mappedBy = "productDetail", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Price> productPrice;
}
