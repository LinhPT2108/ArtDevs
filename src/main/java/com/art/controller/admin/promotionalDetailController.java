package com.art.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.models.product.Product;
import com.art.models.promotion.PromotionalDetails;
import com.art.service.ParamService;
import com.art.service.SessionService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class promotionalDetailController {
	@Autowired
	PromotionalDetailsDAO promotionDetailDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	HttpServletResponse response;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	FlashSaleDAO flashSaleDAO;
	@ModelAttribute("productList")
	public Map<String, String> getManufacturers() {
		List<Product> listProduct = productDAO.findByAvailable(false);
		Map<String, String> map = new HashMap<>();
		for (Product c : listProduct) {
			map.put(c.getProductId(), c.getProductName());
		}
		return map;
	}
	@GetMapping("/promotionalDetail/{id}")
	public String promotiondetailbyid(@ModelAttribute("promotionalDetail") PromotionalDetails promotionalDetail,Model model,@PathVariable("id") Integer id) {
		try {
			model.addAttribute("views", "promotionalDetail-form");
			model.addAttribute("title", "Quản lí sản phẩm khuyến mãi");
			model.addAttribute("typeButton", "Thêm");
			model.addAttribute("updateButton", "Cập nhật"); 
			model.addAttribute("deleteButton", "Xóa");
			List<Product> products=productDAO.findAll();
			List<PromotionalDetails> promotionalDetails=promotionDetailDAO.findByFlashSale_Id(id);
			model.addAttribute("products",products);
			model.addAttribute("promotionalDetails",promotionalDetails);
			model.addAttribute("idflashSale",id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/index";
	}
	@PostMapping("/promotionalDetail/create")
	public String createpromotionalDetail(@ModelAttribute("promotionalDetail") PromotionalDetails promotionalDetail
			, BindingResult bd,Model model) {
		model.addAttribute("views", "promotionalDetail-form");
		model.addAttribute("title", "Quản lí sản phẩm khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		String productIdString=paramService.getString("product","");
		promotionalDetail.setProduct(productDAO.getById(productIdString));
		List<PromotionalDetails> promotionalDetails=promotionDetailDAO.findAll();
		model.addAttribute("promotionalDetails",promotionalDetails);
		int flashSaleIdString=paramService.getInt("idflashSale", -1);
		System.out.println(flashSaleIdString);
		promotionalDetail.setFlashSale(flashSaleDAO.getById(flashSaleIdString));
		promotionDetailDAO.save(promotionalDetail);
		
		return "redirect:/admin/promotionalDetail/" +flashSaleIdString;
	}
	@RequestMapping("/promotionalDetail/{idflashSale}/update")
	public String updatepromotionalDetail(@ModelAttribute("promotionalDetail") PromotionalDetails promotionalDetail
			, BindingResult bd,Model model,@PathVariable("idflashSale") Integer idflashSale) {
		model.addAttribute("views", "promotionalDetail-form");
		model.addAttribute("title", "Quản lí sản phẩm khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		String productIdString=paramService.getString("product","");
		promotionalDetail.setProduct(productDAO.getById(productIdString));
		List<PromotionalDetails> promotionalDetails=promotionDetailDAO.findByFlashSale_Id(idflashSale);
		model.addAttribute("promotionalDetails",promotionalDetails);
		int flashSaleIdString=paramService.getInt("idflashSale", -1);
		System.out.println(flashSaleIdString);
		promotionalDetail.setFlashSale(flashSaleDAO.getById(flashSaleIdString));
		promotionDetailDAO.save(promotionalDetail);
		model.addAttribute("idflashSale",idflashSale);
		return "redirect:/admin/promotionalDetail/"+idflashSale +"/edit/"+promotionalDetail.getId();
	}
	@RequestMapping("/promotionalDetail/{idflashSale}/edit/{id}")
	public String editpromotionalDetail(@ModelAttribute("promotionalDetail") PromotionalDetails promotionalDetail,Model model,
			@PathVariable("id") Integer id,@PathVariable("idflashSale") Integer idflashSale) {
		try {
			model.addAttribute("views", "promotionalDetail-form");
			model.addAttribute("title", "Quản lí sản phẩm khuyến mãi");
			model.addAttribute("typeButton", "Thêm");
			model.addAttribute("updateButton", "Cập nhật"); 
			model.addAttribute("deleteButton", "Xóa");
			List<PromotionalDetails> promotionalDetails=promotionDetailDAO.findByFlashSale_Id(idflashSale);
			PromotionalDetails promotionalDetailbyid=promotionDetailDAO.findById(id).get();
			model.addAttribute("promotionalDetail",promotionalDetailbyid);
			model.addAttribute("promotionalDetails",promotionalDetails);
			model.addAttribute("idflashSale",idflashSale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/index";
	}
	@RequestMapping("/promotionalDetail/{idflashSale}/delete/{id}")
	public String deletepromotionalDetail(@PathVariable("id") Integer id,
			@ModelAttribute("promotionalDetail") PromotionalDetails promotionalDetail,BindingResult bd,Model model
			,@PathVariable("idflashSale") Integer idflashSale) {
		model.addAttribute("views", "promotionalDetail-form");
		model.addAttribute("title", "Quản lí sản phẩm khuyến mãi");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật"); 
		model.addAttribute("deleteButton", "Xóa");
		List<PromotionalDetails> promotionalDetails=promotionDetailDAO.findByFlashSale_Id(idflashSale);
		model.addAttribute("promotionalDetails",promotionalDetails);
		int flashSaleIdString=paramService.getInt("idflashSale", -1);
		System.out.println(flashSaleIdString);
		promotionalDetail.setFlashSale(flashSaleDAO.getById(flashSaleIdString)); 
		promotionalDetail.setStatus(true);
		model.addAttribute("idflashSale",idflashSale);
		promotionDetailDAO.save(promotionalDetail);
		return "redirect:/admin/promotionalDetail/" +flashSaleIdString;
			
	}
}
