package com.art.dao.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.activity.Cart;
import com.art.models.user.Account;

public interface CartDAO extends JpaRepository<Cart, Integer> {
	
	// Thêm hoặc cập nhật một giỏ hàng
//	@SuppressWarnings("unchecked")
//	Cart save(Cart cart);
//	
//	// Xóa một giỏ hàng theo ID
//	void deleteById(int cartId);
//	// Tìm giỏ hàng theo ID
//	Optional<Cart> findById(int cartId);
//	
//	// Lấy tất cả các giỏ hàng của một người dùng
	List<Cart> findByUser(Account user);
//	
//	// Lấy tất cả các giỏ hàng chứa một sản phẩm
//	List<Cart> findByProduct(Product product);
//	
//	Cart findByProductAndUser(Product product, Account user);
//	
//	void deleteByUser(Account user);

}
