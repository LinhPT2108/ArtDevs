package com.art.models.activity;

import com.art.models.product.ProductDetail;
import com.art.models.user.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name="product")
	private ProductDetail productDetail;

	@Column
	private int quantity;


	public Cart(int cartId, int quantity, Account account) {
		super();
		this.cartId = cartId;
		this.quantity = quantity;
		this.user = account;
	}
}
