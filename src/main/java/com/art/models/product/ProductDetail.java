package com.art.models.product;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@OneToMany(mappedBy = "productDetail")
	private List<DetailDescription> detailDescriptions;
	
	@JsonBackReference("productDetailReference")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
