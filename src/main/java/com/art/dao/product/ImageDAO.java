package com.art.dao.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.product.Image;
import com.art.models.product.Product;


public interface ImageDAO extends JpaRepository<Image, Integer>{

	
	@SuppressWarnings("unchecked")
	// Thêm hoặc cập nhật một hình ảnh
	Image save(Image image);
//	
//	// Xóa một hình ảnh dựa trên ID
//	void deleteById(int imageId);
//	
//	// Tìm hình ảnh theo ID
//	Optional<Image> findById(int imageId);
//	
//	// Tìm hình ảnh thông qua sản phẩm
//	List<Image> findByProduct(Product product);
	
	void deleteByProduct(Product product);
	
	
}
