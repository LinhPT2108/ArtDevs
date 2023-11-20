package com.art.models.activity;

import java.util.List;

import com.art.models.product.ProductCart;
import com.art.models.user.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	@ManyToOne
	@JoinColumn(name = "userId")
	private Account user;

	@OneToMany(mappedBy = "cart")
	private List<ProductCart> productCarts;

	@Column
	private int quantity;
	
	@Column
	private double price;
	
	

}
