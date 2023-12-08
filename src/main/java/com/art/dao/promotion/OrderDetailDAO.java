package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.promotion.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

	@Query("SELECT od.productDetail.product, COUNT(od.productDetail.product) FROM OrderDetail od GROUP BY od.productDetail.product ORDER BY COUNT(od.productDetail.product) DESC")
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

}
