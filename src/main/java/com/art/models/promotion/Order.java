package com.art.models.promotion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.art.models.user.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private Account user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date orderDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date expected_delivery_time;
	@Column
	private BigDecimal totalAmount;

	@Column
	@Nationalized
	private String deliveryAddress;

	@Column
	@Nationalized
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "payment_id")
	private PaymentMethod paymentMethod;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "orderStatus")
	private List<OrderDelieveryStatus> orderStatus;

	public Order(int id, Account user, Date orderDate, BigDecimal totalAmount, String deliveryAddress,
			String note) {
		this.id = id;
		this.user = user;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.deliveryAddress = deliveryAddress;
		this.note = note;
	}

	
}
