package com.art.models.product;

import java.util.Date;
import java.util.List;
import java.lang.*;

import org.hibernate.annotations.Nationalized;

import com.art.models.activity.Comment;
import com.art.models.activity.RecentlyViewed;
import com.art.models.activity.WishList;
import com.art.models.promotion.OrderDetail;
import com.art.models.promotion.PromotionalDetails;
import com.art.models.user.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product {

	@Id
	private String productId;

	@Column
	@Nationalized
	@NotBlank(message = "Vui lòng nhập tên sản phẩm")
	private String productName;

//	@Column
//	@NotNull(message = "Vui lòng nhập số lượng trong kho")
//	@Min(value = 1, message = "Số lượng phải lớn 0")
//	@Digits(integer = 32, fraction = 0, message = "Số lượng phải là số nguyên")
//	private int quantityInStock;

	@Column
	private boolean available = true;

//	@Column
//	@NotNull(message = "Vui lòng nhập giá sản phẩm")
//	@DecimalMin(value = "1", message = "Giá sản phẩm phải lớn 0")
//	private BigDecimal price;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDate = new Date();

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "userProduct")
	private Account user;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "category")
	@NotNull(message = "Vui lòng chọn loại sản phẩm")
	private Category categoryProduct;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "manufacturer")
	@NotNull(message = "Vui lòng chọn thương hiệu")
	private Manufacturer manufacturerProduct;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<Comment> productComment;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<RecentlyViewed> productRecentlyViewed;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<WishList> productWishList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<ProductDetail> productDetail;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<DetailDescription> productDescriptions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Image> productImage;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<OrderDetail> productOrderDetail;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<PromotionalDetails> productPromotionalDetails;

}
