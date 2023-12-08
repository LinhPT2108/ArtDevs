package com.art.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.art.dao.product.PriceDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.models.product.Price;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;
import com.art.service.ParamService;

@RequestMapping("/admin")
@Controller
public class ProductDetailController {
	@Autowired
	ParamService paramService;
	
	@Autowired
	ProductDAO pdDAO;

	@Autowired
	ProductDetailDAO productdetailDAO;

	@Autowired
	PriceDAO pricedao;

	@GetMapping("/ProductDetail/{id}")
	public String getProduct(@ModelAttribute("ProductDetail") ProductDetail productdetail, BindingResult rs,
			@PathVariable("id") String productID, Model model) {
		model.addAttribute("title", "Chi Tiết Sản Phẩm");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		
		System.out.println("productID" + productID);
		Product pd = pdDAO.getById(productID);
		List<ProductDetail> pdDetail = productdetailDAO.findByProduct(pd);
		for (ProductDetail p : pdDetail) {
			List<Price> myList = new ArrayList<>();
			myList.add(pricedao.sortPriceByDatecreate(p.getId()).get(0));

			p.setProductPrice(myList);
		}

		model.addAttribute("ProductDetails", pdDetail);
		model.addAttribute("idProduct", productID);

		return "/admin/ProductDetail-form";
	}

	@PostMapping("/ProductDetail/create")
	public String addProduct(@ModelAttribute("productdetail") ProductDetail productdetail, BindingResult rs,
			Model model) {
		
		String ProductID = paramService.getString("idProduct", null);
		System.out.println("product ID" + ProductID);
		productdetail.setProductionDate(new Date());
		productdetail.setProduct(pdDAO.getById(ProductID));
		System.out.println("Product new" + pdDAO.getById(ProductID));
		System.out.println(productdetail.getProductPrice());
		System.out.println(productdetail.getPower());
//		productdetailDAO.save(productdetail);
		return "redirect:/admin/ProductDetail/" + ProductID;
	}
	
	@RequestMapping("/ProductDetail/{ProductID}/edit/{ProductDetailID}")
	public String getProduct(@PathVariable("ProductID") String ProductID,@PathVariable("ProductDetailID") int pddetailID, Model model ) {
		model.addAttribute("title", "Chi Tiết Sản Phẩm");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		System.out.println("productID" + ProductID);
		ProductDetail prodetail = productdetailDAO.getById(pddetailID);
		
		
		Product pd = pdDAO.getById(ProductID);
		List<ProductDetail> pdDetail = productdetailDAO.findByProduct(pd);
		
		List<Price> price =pricedao.sortPriceByDatecreate(prodetail.getId());
		
		prodetail.setProductPrice(price);
		
		System.out.println("price"+price);
		
		
		model.addAttribute("ProductDetail",prodetail );
		model.addAttribute("ProductDetails", pdDetail);
		return "/admin/ProductDetail-form";
	}
	

}
