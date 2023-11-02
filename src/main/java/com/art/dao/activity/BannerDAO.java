package com.art.dao.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.activity.Banner;
import com.art.models.user.Account;

public interface BannerDAO extends JpaRepository<Banner, Integer> {
	//Tìm tất cả các banner theo người dùng
	List<Banner> findByUser(Account user);
	
	//Tìm theo tên banner
	List<Banner> findByBannerName(String bannerName);
	
	//Tìm banner theo ID
	Banner findById(int id);
	
	@SuppressWarnings("unchecked")
	//Thêm hoặc cập nhật một banner
	Banner save(Banner banner);
	
	//Xóa một banner theo ID
	void deleteById(int id);
}
