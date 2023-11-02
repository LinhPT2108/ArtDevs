package com.art.models.user;

import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.art.models.activity.Banner;
import com.art.models.activity.Cart;
import com.art.models.activity.Comment;
import com.art.models.activity.RecentlyViewed;
import com.art.models.activity.WishList;
import com.art.models.product.Category;
import com.art.models.product.Manufacturer;
import com.art.models.product.Product;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.Invoice;
import com.art.models.promotion.Voucher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Account {

	@Id
	private String accountId;

	@ManyToOne
	@JoinColumn(name = "role")
	private Role roleName;
	
	@Column
	@Nationalized
	@NotBlank(message = "Vui lòng nhập họ tên")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]*$", message = "Họ tên không hợp lệ")
	private String fullname;
	
	@Column
	private String image;
	
	@Column
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, message = "Mật khẩu phải chứa ít nhất 6 ký tự")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "Mật khẩu phải chứa ít nhất một chữ cái")
    private String password;

	@Column
	@Email(message = "Email không hợp lệ")
	private String email;
	
	@Column
	private String verifyCode;
	
	@Column
	private boolean del;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<InforAddress> userInfor;
	
	@OneToMany(mappedBy = "user")
	private List<Banner> userBanner;

	@OneToMany(mappedBy = "user")
	private List<Cart> userCart;

	@OneToMany(mappedBy = "user")
	private List<Comment> userComment;

	@OneToMany(mappedBy = "user")
	private List<RecentlyViewed> userRecentlyViewed;

	@OneToMany(mappedBy = "user")
	private List<WishList> userWishList;

	@OneToMany(mappedBy = "user")
	private List<Category> userCategory;

	@OneToMany(mappedBy = "user")
	private List<Manufacturer> userManufacturer;

	@OneToMany(mappedBy = "user")
	private List<Product> userProduct;

	@OneToMany(mappedBy = "user")
	private List<FlashSale> userFlashSale;

	@OneToMany(mappedBy = "user")
	private List<Invoice> userInvoice;

	@OneToMany(mappedBy = "user")
	private List<Voucher> userVoucher;

	@OneToMany(mappedBy = "user")
	private List<Voucher> userKeyword;
	
	@Override
	public String toString() {
		return "UserCustom [userId=" + accountId + ", fullname=" + fullname + ", password=" + password + ", email=" + email
				+ ", del=" + del + "]";
	}

	
}
