package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.FlashSale;

public interface FlashSaleDAO extends JpaRepository<FlashSale, Integer> {

//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một Flash Sale
//    FlashSale save(FlashSale flashSale);
//
//    // Xóa một Flash Sale dựa trên ID của nó
//    void deleteById(int flashSaleId);
//
//    // Kiểm tra xem một Flash Sale có tồn tại dựa trên ID của nó hay không
//    boolean existsById(int flashSaleId);
//	
//    // Tìm kiếm các Flash Sale có ngày bắt đầu lớn hơn hoặc bằng một ngày cụ thể
//    List<FlashSale> findByStartDayGreaterThanEqual(Date startDate);
//
//    // Tìm kiếm các Flash Sale có ngày kết thúc nhỏ hơn hoặc bằng một ngày cụ thể
//    List<FlashSale> findByEndDayLessThanEqual(Date endDate);
//
    // Tìm kiếm các Flash Sale dựa trên trạng thái (đã kích hoạt hoặc chưa kích hoạt)
    FlashSale findByStatus(boolean status);
    
//
//    // Tìm kiếm các Flash Sale dựa trên người tạo
//    List<FlashSale> findByUser(UserCustom user);

}
