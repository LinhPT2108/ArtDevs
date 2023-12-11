package com.art.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.dao.product.PriceDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.models.product.Price;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;
import com.art.service.ParamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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
	
	@Autowired
	HttpServletRequest req;

	@GetMapping("/ProductDetail/{pdid}")
	public String getProduct(@ModelAttribute("ProductDetail") ProductDetail productdetail, BindingResult rs,
			@PathVariable("pdid") String productID, Model model) {
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
		System.out.println("id:"+productdetail.getId());
		return "/admin/ProductDetail-form";
	}

	@PostMapping("/ProductDetail/create")
	public String addProduct(@Valid @ModelAttribute("productdetail") ProductDetail productdetail, BindingResult rs,
			Model model) {
		
		Price price = new Price();
		String ProductID = paramService.getString("idProduct", null);
		 long priceValue = productdetail.getProductPrice().get(0).getPrice();
		System.out.println("pridetail"+ priceValue);
		System.out.println("product ID" + ProductID);
		if(rs.hasErrors()) {
			System.out.println("Lỗi ròi");
			return "/admin/ProductDetail-form";
		}
		productdetail.setProductionDate(new Date());
		productdetail.setProduct(pdDAO.getById(ProductID));
		productdetail.setStatus(true);
		System.out.println("Product new" + pdDAO.getById(ProductID));
		price.setPrice(priceValue);
		price.setCreatedDate(new Date());
		price.setProductDetail(productdetail);
		productdetailDAO.save(productdetail);
		pricedao.save(price);
		return "redirect:/admin/ProductDetail/" + ProductID;
	}
	
	@RequestMapping("/ProductDetail/{ProductID}/edit/{ProductDetailID}")
	public String getProduct(@PathVariable("ProductID") String ProductID,@PathVariable("ProductDetailID") int pddetailID, Model model ) {
		model.addAttribute("title", "Chi Tiết Sản Phẩm");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("idProduct" , ProductID);
		ProductDetail prodetail = productdetailDAO.getById(pddetailID);
		Product pd = pdDAO.getById(ProductID);
		List<ProductDetail> pdDetail = productdetailDAO.findByProduct(pd);
		List<Price> price =pricedao.sortPriceByDatecreate(prodetail.getId());
		prodetail.setProductPrice(price);
		System.out.println("price"+price);
		for (ProductDetail p : pdDetail) {
			List<Price> myList = new ArrayList<>();
			myList.add(pricedao.sortPriceByDatecreate(p.getId()).get(0));

			p.setProductPrice(myList);
		}
		model.addAttribute("ProductDetail",prodetail );
		model.addAttribute("ProductDetails", pdDetail);
		return "/admin/ProductDetail-form";
	}
	@RequestMapping("/ProductDetail/{ProductID}/update")
	public String upateProductDetail(@ModelAttribute("productdetail") ProductDetail productdetail,@PathVariable("ProductID") String ProductID,Model model) {
		model.addAttribute("title", "Chi Tiết Sản Phẩm");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("idProduct" , ProductID);
		long priceValue = productdetail.getProductPrice().get(0).getPrice();
		Product pd = pdDAO.getById(ProductID);
		productdetail.setProduct(pd);
		productdetail.setProductionDate(new Date());
		productdetail.setStatus(true);
		Price price = new Price();
		price.setPrice(priceValue);
		price.setCreatedDate(new Date());
		price.setProductDetail(productdetail);
		System.out.println("id produc detail" +productdetail.getId());
		
		List<Price> myList = pricedao.sortPriceByDatecreate(productdetail.getId());
		System.out.println("price cu :" + myList.get(0).getPrice());
		if(myList.get(0).getPrice() != priceValue) {
			pricedao.save(price);
		}

		productdetailDAO.save(productdetail);
		
		return "redirect:/admin/ProductDetail/" + ProductID;
		
	}
	@RequestMapping("/ProductDetail/{ProductID}/delete")
	public String deleteProduct(@ModelAttribute("productdetail") ProductDetail productdetail,@PathVariable("ProductID") String ProductID, Model model ) {
		productdetail.setProductionDate(new Date());
		productdetail.setProduct(pdDAO.getById(ProductID));
		productdetail.setStatus(false);
		productdetail.setQuantityInStock(0);
		productdetailDAO.save(productdetail);
		return "redirect:/admin/ProductDetail/" + ProductID;
	}
	
	@RequestMapping("/ProductDetail/123")
	public String HistoryPrice(@ModelAttribute("productdetail") ProductDetail productdetail,@PathVariable("ProductID") String ProductID, Model model ) {
		productdetail.setProductionDate(new Date());
		productdetail.setProduct(pdDAO.getById(ProductID));
		productdetail.setStatus(false);
		productdetail.setQuantityInStock(0);
		productdetailDAO.save(productdetail);
		return "redirect:/admin/ProductDetail/" + ProductID;
	}
}
