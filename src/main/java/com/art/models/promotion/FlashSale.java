package com.art.models.promotion;

import java.util.Date;
import java.util.List;

import com.art.models.user.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
	private boolean status = true;

	@OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL)
	private List<PromotionalDetails> promotionalDetailsList;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="userFlashSale")
	private Account user;

}
