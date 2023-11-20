package com.art.dao.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.product.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

//	// Thêm hoặc cập nhật một danh mục
//	@SuppressWarnings("unchecked")
//	Category save(Category category);
//
//	// Xóa một danh mục theo ID
//	void deleteById(int categoryId);
//
//	// Tìm danh mục theo ID
//	Optional<Category> findById(int categoryId);
//
//	// Lấy danh mục dựa trên tên danh mục
//	Optional<Category> findByCategoryName(String categoryName);
//
//	// Lấy tất cả các danh mục với thông tin admin
//	List<WishList> findByUser(UserCustom user);
//
//	// Lấy tất cả các danh mục thông qua trạng thái
	List<Category> findByStatus(boolean status);
}
