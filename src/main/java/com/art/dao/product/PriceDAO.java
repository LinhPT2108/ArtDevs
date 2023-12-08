package com.art.dao.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.product.Price;
import com.art.models.product.ProductDetail;



public interface PriceDAO extends JpaRepository<Price, Integer>{

	
//	@SuppressWarnings("unchecked")
	// Thêm hoặc cập nhật một hình ảnh
//	Image save(Image image);
//	
//	// Xóa một hình ảnh dựa trên ID
//	void deleteById(int imageId);
//	
//	// Tìm hình ảnh theo ID
//	Optional<Image> findById(int imageId);
//	
//	// Tìm hình ảnh thông qua sản phẩm
//	List<Image> findByProduct(Product product);
	
//	void deleteByProduct(Product product);
	

	@Query("SELECT  p FROM Price p WHERE p.productDetail.id =:r ORDER BY (p.createdDate) DESC")
	List<Price> sortPriceByDatecreate(int r);
	
	List<Price> findByProductDetail(ProductDetail productDetail);
	
}
