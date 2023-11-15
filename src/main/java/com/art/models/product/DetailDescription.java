package com.art.models.product;

import org.hibernate.annotations.Nationalized;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetailDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Nationalized
	private String title;

	@Column(columnDefinition = "nvarchar(MAX)")
	@Nationalized
	private String description;

	@JsonBackReference("productDetailReference")
	@ManyToOne
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
}
