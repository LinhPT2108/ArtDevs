package com.art.models.activity;

import java.util.Date;

import com.art.models.product.Product;
import com.art.models.user.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WishList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userWishList")
	private Account user;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date wishlistDate;
}
