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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
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

	@Column
	private BigDecimal totalAmount;
	
	@Column
	private int status;
	
	@Column
	@Nationalized
	private String deliveryAddress;
	
	@Column
	@Nationalized
	private String note;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "orderStatus")
	private List<DeliveryStatus> orderStatus;
}
