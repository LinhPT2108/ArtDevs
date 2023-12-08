package com.art.models.activity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.art.models.product.ProductDetail;
import com.art.models.promotion.OrderDetail;
import com.art.models.user.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToOne;
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
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int star;

	@Column
	@Nationalized
	private String content;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userComment")
	private Account user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "orderDetailId")
	private OrderDetail orderDetail;

	@OneToMany(mappedBy = "comment", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ImageComment> commentImages;
}
