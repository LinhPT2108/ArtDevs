package com.art.models.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.art.models.activity.Banner;
import com.art.models.activity.Cart;
import com.art.models.activity.Comment;
import com.art.models.activity.RecentlyViewed;
import com.art.models.activity.WishList;
import com.art.models.product.Category;
import com.art.models.product.Manufacturer;
import com.art.models.product.Product;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.Order;
import com.art.models.promotion.Voucher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Account implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String accountId;
	
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
	private boolean status = false;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<InforAddress> userInfor;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<AccountRole> userRole;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Banner> userBanner;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Cart> userCart;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> userComment;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<RecentlyViewed> userRecentlyViewed;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<WishList> userWishList;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Category> userCategory;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Manufacturer> userManufacturer;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Product> userProduct;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<FlashSale> userFlashSale;


	@OneToMany(mappedBy = "user")
	private List<Order> userOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Voucher> userVoucher;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<KeywordSuggestions> userKeyword;
	
	@Override
	public String toString() {
		return "UserCustom [userId=" + accountId + ", fullname=" + fullname + ", password=" + password + ", email=" + email
				+ ", del=" + status + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		userRole.stream().forEach(i-> authorities.add(new SimpleGrantedAuthority(i.getRole().getRoleName())));
		return List.of(new SimpleGrantedAuthority(authorities.toString()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !this.status;
	}

	
}
