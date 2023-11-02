package com.art.models.promotion;

import java.util.Date;
import java.util.List;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlashSale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date startDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date endDay;

	@Column
	private boolean isStatus;

	@OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL)
	private List<PromotionalDetails> promotionalDetailsList;
	
	@ManyToOne
	@JoinColumn(name="userFlashSale")
	private Account user;

}
