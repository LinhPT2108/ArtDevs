package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.Voucher;

public interface VoucherDAO extends JpaRepository<Voucher, Integer> {

//	@SuppressWarnings("unchecked")
//	// Thêm Voucher
//	Voucher save(Voucher voucher);
//
//	// Cập nhật Voucher
//	@SuppressWarnings("unchecked")
//	Voucher saveAndFlush(Voucher voucher);
//
//	// Xóa Voucher theo ID
//	void deleteById(int voucherId);
//
//	// Kiểm tra sự tồn tại của Voucher theo ID
//	boolean existsById(int voucherId);
//
//	// Tìm Voucher theo ID
//	Voucher findById(int voucherId);
//
//	// Tìm tất cả Voucher
//	List<Voucher> findAll();
//
//	// Tìm Voucher theo discount
//	List<Voucher> findByDiscount(double discount);

}
