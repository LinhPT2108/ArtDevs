package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.promotion.Invoice;
import com.art.models.user.Account;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {

	@Query("SELECT i.invoiceDate FROM Invoice i GROUP BY i.invoiceDate")
	List<String> getLabelsRevenueByDate();
	@Query("SELECT SUM(i.totalAmount) FROM Invoice i GROUP BY i.invoiceDate")
	List<Double> getPriceRevenueByDate();
	@Query("SELECT MONTH(i.invoiceDate) FROM Invoice i GROUP BY MONTH(i.invoiceDate)")
	List<Integer> getMonthLabelsRevenue();
	@Query("SELECT SUM(i.totalAmount) FROM Invoice i GROUP BY MONTH(i.invoiceDate)")
	List<Double> getTotalAmountByMonth();
	@Query("SELECT YEAR(i.invoiceDate) FROM Invoice i GROUP BY YEAR(i.invoiceDate)")
	List<Integer> getYearLabelsRevenue();
	@Query("SELECT SUM(i.totalAmount) FROM Invoice i GROUP BY YEAR(i.invoiceDate)")
	List<Double> getTotalAmountByYear();
	
	List<Invoice> findAllByOrderByInvoiceDateAsc();
	List<Invoice> findAllByOrderByInvoiceDateDesc();
	
    Invoice findById(int id);
//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một Invoice
//    Invoice save(Invoice invoice);
//
//    @SuppressWarnings("unchecked")
//	// Phương thức cập nhật Invoice
//    Invoice saveAndFlush(Invoice invoice);
//
//    // Xóa Invoice theo id
//    void deleteById(int flashSaleId);
//	
//    // Kiểm tra sự tồn tại của FlashSale theo ID
//    boolean existsById(int flashSaleId);
//    
	// tìm kiếm hóa đơn theo người dùng
	List<Invoice> findByUser(Account user);

	//hóa đơn theo người dùng và trạng thái hóa đơn
	
	Page<Invoice> findByUserAndStatus(Account user, int Status,Pageable pageable);
//	
//	// Tìm kiếm các hóa đơn có ngày nằm trong khoảng từ một ngày bắt đầu đến một ngày kết thúc
//    List<Invoice> findByInvoiceDateBetween(Date startDate, Date endDate);
//
//    // Tìm kiếm các hóa đơn có tổng số tiền lớn hơn hoặc bằng một số tiền cụ thể
//    List<Invoice> findByTotalAmountGreaterThanEqual(BigDecimal minAmount);
//
//    // Tìm kiếm các hóa đơn dựa trên nội dung ghi chú chứa một từ khóa cụ thể
//    List<Invoice> findByNoteContaining(String keyword);
//
//    // Tìm kiếm các hóa đơn dựa trên người dùng và tổng số tiền lớn hơn hoặc bằng một số tiền cụ thể
//    List<Invoice> findByUserAndTotalAmountGreaterThanEqual(UserCustom user, BigDecimal minAmount);
}
