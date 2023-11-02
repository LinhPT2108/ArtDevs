package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.promotion.InvoiceDetail;

public interface InvoiceDetailDAO extends JpaRepository<InvoiceDetail, Integer> {

	@Query("SELECT id.product, p.productName, COUNT(id.product) FROM InvoiceDetail id JOIN id.product p GROUP BY id.product, p.productName ORDER BY COUNT(id.product) DESC")
	List<Object[]> countProductsOrderByCountDesc();
//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một InvoiceDetail
//	InvoiceDetail save(InvoiceDetail invoiceDetail);
//
//	// Phương thức xóa InvoiceDetail theo ID
//	void deleteById(int invoiceDetailId);
//
//	// Phương thức kiểm tra sự tồn tại của InvoiceDetail theo ID
//	boolean existsById(int invoiceDetailId);
//
//	// Phương thức tìm kiếm InvoiceDetail theo ID
//	Optional<InvoiceDetail> findById(int invoiceDetailId);
//
//	// Tìm kiếm InvoiceDetail theo Invoice
//	List<InvoiceDetail> findByInvoice(Invoice invoice);
//
//	// Tìm kiếm InvoiceDetail theo Product
//	List<InvoiceDetail> findByProduct(Product product);

}
