package com.art.dao.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.user.InforAddress;
import com.art.models.user.Account;

public interface InforAddressDAO extends JpaRepository<InforAddress, String>{

//	@SuppressWarnings("unchecked")
//	// Thêm hoặc cập nhật một địa chỉ thông tin
//	InforAddress save(InforAddress inforAddress);
//
//	// Xóa một địa chỉ thông tin dựa trên số điện thoại
//    void deleteById(String phoneNumber);
//
//    // Kiểm tra số điện thoại đã tồn tại hay chưa
   boolean existsById(String phoneNumber);
//	
//	// Tìm kiếm các địa chỉ thông qua người dùng

	List<InforAddress> findByUser(Account user);
	
	Page<InforAddress> findByUser(Account user, Pageable pageable);
//
//    // Tìm kiếm các địa chỉ thông tin dựa trên địa chỉ chứa một từ khóa
//    List<InforAddress> findByAddressContainingIgnoreCase(String keyword);
	
}
