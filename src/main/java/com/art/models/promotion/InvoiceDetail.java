package com.art.models.promotion;

import java.math.BigDecimal;

import com.art.models.activity.Comment;
import com.art.models.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvoiceDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	private int quantity;

	@Column
	private BigDecimal price;

	@OneToOne(mappedBy = "invoiceDetail")
	private Comment comment;
}
