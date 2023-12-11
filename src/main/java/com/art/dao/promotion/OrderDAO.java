package com.art.dao.promotion;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.art.models.promotion.Order;
import com.art.models.user.Account;

public interface OrderDAO extends JpaRepository<Order, Integer> {

	@Query("SELECT i.orderDate FROM Order i GROUP BY i.orderDate")
	List<String> getLabelsRevenueByDate();
	@Query("SELECT SUM(i.totalAmount) FROM Order i GROUP BY i.orderDate")
	List<Double> getPriceRevenueByDate();
	@Query("SELECT MONTH(i.orderDate) FROM Order i GROUP BY MONTH(i.orderDate)")
	List<Integer> getMonthLabelsRevenue();
	@Query("SELECT SUM(i.totalAmount) FROM Order i GROUP BY MONTH(i.orderDate)")
	List<Double> getTotalAmountByMonth();
	@Query("SELECT YEAR(i.orderDate) FROM Order i GROUP BY YEAR(i.orderDate)")
	List<Integer> getYearLabelsRevenue();
	@Query("SELECT SUM(i.totalAmount) FROM Order i GROUP BY YEAR(i.orderDate)")
	List<Double> getTotalAmountByYear();
	
	List<Order> findAllByOrderByOrderDateAsc();
	List<Order> findAllByOrderByOrderDateDesc();
	
    Order findById(int id);
//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một Order
//    Order save(Order Order);
//
//    @SuppressWarnings("unchecked")
//	// Phương thức cập nhật Order
//    Order saveAndFlush(Order Order);
//
//    // Xóa Order theo id
//    void deleteById(int flashSaleId);
//	
//    // Kiểm tra sự tồn tại của FlashSale theo ID
//    boolean existsById(int flashSaleId);
//    
	// tìm kiếm hóa đơn theo người dùng
	List<Order> findByUser(Account user);

	//hóa đơn theo người dùng và trạng thái hóa đơn
	
//	Page<Order> findByUserAndStatus(Account user, int Status,Pageable pageable);
//	
//	// Tìm kiếm các hóa đơn có ngày nằm trong khoảng từ một ngày bắt đầu đến một ngày kết thúc
//    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
//
//    // Tìm kiếm các hóa đơn có tổng số tiền lớn hơn hoặc bằng một số tiền cụ thể
//    List<Order> findByTotalAmountGreaterThanEqual(BigDecimal minAmount);
//
//    // Tìm kiếm các hóa đơn dựa trên nội dung ghi chú chứa một từ khóa cụ thể
//    List<Order> findByNoteContaining(String keyword);
//
//    // Tìm kiếm các hóa đơn dựa trên người dùng và tổng số tiền lớn hơn hoặc bằng một số tiền cụ thể
//    List<Order> findByUserAndTotalAmountGreaterThanEqual(UserCustom user, BigDecimal minAmount);
	
	@Query("SELECT COUNT(o) FROM Order o JOIN o.orderStatus s WHERE s.deliveryStatus.id = :deliveryStatusId AND o.orderDate >= CURRENT_DATE AND s.status=true")
    long countOrdersByDeliveryStatusToday(@Param("deliveryStatusId") int deliveryStatusId);
	
	@Query("SELECT COUNT(o) FROM Order o JOIN o.orderStatus s WHERE s.deliveryStatus.id = :deliveryStatusId AND o.orderDate >= :startDate AND s.status=true")
    long countOrdersByDeliveryStatusThisYear(@Param("deliveryStatusId") int deliveryStatusId, @Param("startDate") Date startDate);
	
//	@Query("SELECT COUNT(o) FROM Order o JOIN o.orderStatus s WHERE s.deliveryStatus.id = :deliveryStatusId AND o.orderDate >= :startDate AND s.status=true")
//    long countOrdersByDeliveryStatusThisYearNow(@Param("deliveryStatusId") int deliveryStatusId, @Param("startDate") Date startDate);
	
}
