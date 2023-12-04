package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

	@Query("SELECT id.product, p.productName, COUNT(id.product) FROM OrderDetail id JOIN id.product p GROUP BY id.product, p.productName ORDER BY COUNT(id.product) DESC")
	List<Object[]> countProductsOrderByCountDesc();
//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một OrderDetail
//	OrderDetail save(OrderDetail OrderDetail);
//
//	// Phương thức xóa OrderDetail theo ID
//	void deleteById(int OrderDetailId);
//
//	// Phương thức kiểm tra sự tồn tại của OrderDetail theo ID
//	boolean existsById(int OrderDetailId);
//
//	// Phương thức tìm kiếm OrderDetail theo ID
//	Optional<OrderDetail> findById(int OrderDetailId);
//
//	// Tìm kiếm OrderDetail theo Order
//	List<OrderDetail> findByOrder(Order Order);
//
//	// Tìm kiếm OrderDetail theo Product
//	List<OrderDetail> findByProduct(Product product);

	List<OrderDetail> findByOrder(Order order);

	@Query("SELECT a.price FROM Price a WHERE a.productDetail.product.productId =:r ")
	long findPriceByProductId(String r);

}
