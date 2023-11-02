package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.promotion.PromotionalDetails;

public interface PromotionalDetailsDAO extends JpaRepository<PromotionalDetails, Integer> {
	// Thêm PromotionalDetails
//	@SuppressWarnings("unchecked")
//	PromotionalDetails save(PromotionalDetails promotionalDetails);
//
//	// Cập nhật PromotionalDetails
//	@SuppressWarnings("unchecked")
//	PromotionalDetails saveAndFlush(PromotionalDetails promotionalDetails);
//
//	// Xóa PromotionalDetails theo ID
//	void deleteById(int promotionalId);
//
//	// Kiểm tra sự tồn tại của PromotionalDetails theo ID
//	boolean existsById(int promotionalId);
//
//	// Tìm PromotionalDetails theo ID
//	PromotionalDetails findById(int promotionalId);
//
//	// Tìm tất cả PromotionalDetails
//	List<PromotionalDetails> findAll();
//
//	// Tìm PromotionalDetails theo trạng thái promotion_status
//	List<PromotionalDetails> findByStatus(boolean promotionStatus);
//
//	// Tìm PromotionalDetails theo ID của Product
	List<PromotionalDetails> findByProduct_ProductId(String productId);
//
//	// Tìm PromotionalDetails theo ID của FlashSale
	List<PromotionalDetails> findByFlashSale_Id(int id);
	Page<PromotionalDetails> findByFlashSale_Id(int id, Pageable pageable);
	@Query("SELECT c FROM PromotionalDetails c WHERE c.product.id = :productId")
	PromotionalDetails findByProductId(String productId);
}
