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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userInvoice")
	private Account user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column 
	private Date invoiceDate;

	@Column
	private BigDecimal totalAmount;
	
	@Column
	private int status;
	
	@Column
	@Nationalized
	private String note;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<InvoiceDetail> invoiceDetails;
	
	@OneToMany(mappedBy = "invoiceStatus")
	private List<DeliveryStatus> invoiceStatus;
}
