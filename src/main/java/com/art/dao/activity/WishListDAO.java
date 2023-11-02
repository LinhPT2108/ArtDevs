package com.art.dao.activity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.art.models.activity.WishList;
import com.art.models.product.Product;
import com.art.models.user.Account;

@Transactional
public interface WishListDAO extends JpaRepository<WishList, Integer> {

//	// Thêm hoặc cập nhật một sản phẩm vào WishList
	@SuppressWarnings("unchecked")
	WishList save(WishList wishList);

//
//	// Xóa một sản phẩm khỏi danh sách mong muốn theo ID
//	void deleteById(int wishListId);
//
//	// Tìm kiếm sản phẩm trong danh sách mong muốn theo ID
//	Optional<WishList> findById(int wishListId);
//
//	// Lấy tất cả các sản phẩm trong danh sách mong muốn của một người dùng
	List<WishList> findByUser(Account user);

	Page<WishList> findByUser(Account user,Pageable pageable);

	List<WishList> findByUserAndProduct(Account user, Product product);
//
//	// Lấy tất cả các người dùng đã thêm một sản phẩm vào WishList
//	List<WishList> findByProduct(Product product);

	void deleteByUserAndProduct(Account user, Product product);
}
