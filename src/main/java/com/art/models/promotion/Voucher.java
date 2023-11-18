package com.art.models.promotion;

import java.util.Date;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voucher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "10000")
	private int id;

	@Column
	private double discount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date startDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date endDay;
	
	@Column
	private int numberOfUses;
	
	@Column
	private int maximumNumberOfUses;
	
	@Column
	private double maximumPriceDiscount;
	
	@ManyToOne
	@JoinColumn(name="userVoucher")
	private Account user;

}
