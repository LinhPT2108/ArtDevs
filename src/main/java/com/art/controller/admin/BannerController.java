package com.art.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.art.dao.activity.BannerDAO;
import com.art.models.activity.Banner;
import com.art.service.ParamService;

@Controller
@RequestMapping("/admin")
public class BannerController {

	@Autowired
	private ParamService paramService;
	@Autowired
	private BannerDAO bannerDao;

	@GetMapping({ "/", "/banner" })
	public String showIndex(Model model) {
		model.addAttribute("views", "banner");
		model.addAttribute("title", "banner");
		List<Banner> banners = bannerDao.findAll();
		model.addAttribute("banners", banners);

		return "admin/baner-form";
	}

	@PostMapping("/banner")
	public String addBanner(Model model, @RequestParam("banner") MultipartFile file) {
		model.addAttribute("views", "banner");
		model.addAttribute("title", "banner");
		Banner banner = new Banner();
		banner.setBannerName(paramService.save(file, "images/banner").getName());
//		banner.setUser(sessionService.get("userLogin"));
        bannerDao.save(banner);
		return "redirect:/admin/banner";
	}
/////////
	@GetMapping("/banner/delete/{id}")
	public String deleteBanner(@PathVariable("id") Integer id) {
		// Tìm banner cần xóa theo ID
		Banner banner = bannerDao.findById(id).orElse(null);
		if (banner != null) {
			//nó thục hiện xóa  banner từ cơ sở dữ liệu
			bannerDao.delete(banner);
		}
		return "redirect:/admin/banner";
	}

	
	//////
	
	
//	@PostMapping("/banner/update/{id}")
//	public String updateBanner(@PathVariable("id") Integer id, @RequestParam("banner") MultipartFile file,
//			Model model) {
//		//  find banner cần cập nhật theo ID
//		Banner banner = bannerDao.findById(id).orElse(null);
//		if (banner != null) {
//			// update thông tin banner
//			if (file != null && !file.isEmpty()) {
//				// Nếu đã tải lên thành công thì sẽ hiển thị lên một hình mới một tệp hình ảnh
//				// mới
//				banner.setBannerName(paramService.save(file, "images/banner").getName());
//			}
//			// sau đó lưu lại
//			bannerDao.save(banner);
//		}
//
//		return "redirect:/admin/banner";
//	}
	

}
