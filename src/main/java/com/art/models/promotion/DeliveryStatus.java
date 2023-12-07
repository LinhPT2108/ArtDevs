package com.art.models.promotion;

import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeliveryStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "orderStatus")
	private Order orderStatus;
	
	@Column
	private String DetailUpdate;

	@Nationalized
	private String nameStatus;

	@OneToMany(mappedBy = "deliveryStatus")
	@JsonIgnore
	private List<OrderDelieveryStatus> orderDelieveryStatus;


}
