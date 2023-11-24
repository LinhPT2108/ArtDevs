package com.art.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.dao.product.ManufacturerDAO;
import com.art.dao.user.AccountDAO;
import com.art.models.product.Manufacturer;

@Controller
@RequestMapping("admin")
public class manufacturerController {
	@Autowired
	ManufacturerDAO mnDAO;
	@Autowired
	AccountDAO usDAO;
	@Autowired
	ManufacturerDAO manufacturerReponsitory;
	@Autowired
	ManufacturerDAO mnreps;
	

	@ModelAttribute("mns")
	public List<Manufacturer> getUsercustoms() {
		return mnDAO.findAll();
	}

	
	@GetMapping("/manufacturer")
	public String manufacturer(@ModelAttribute("mn") Manufacturer mn, Model model) {
		model.addAttribute("views", "manufacturer-form");
		model.addAttribute("title", "Thương hiệu sản phẩm");
		model.addAttribute("typeButton", "Thêm");

		return "admin/manufacturer-form";
	}
	
	@RequestMapping("/manufacturer/edit/{id}")
	public String edit(@ModelAttribute("mn") Manufacturer mn, Model model, @PathVariable("id") Integer id) {
		model.addAttribute("views", "manufacturer-form");
		model.addAttribute("title", "Thương hiệu sản phẩm");

		mn = mnDAO.findById(id).get();
		model.addAttribute("mn", mn);
		List<Manufacturer> mns = mnDAO.findAll();
		model.addAttribute("mns", mns);
		return "admin/index";
	}
	
	@PostMapping("/manufacturer/create")
	public String createManufacturer(@ModelAttribute("mn") Manufacturer mn) {
//		Account user = sessionService.get("userLogin");
//		mn.setUser(user);
//		mn.setDel(true);
		mnDAO.save(mn);
		return "redirect:/admin/manufacturer";
	}

	@RequestMapping("/manufacturer/update")
	public String updateManufacturer(@ModelAttribute("mn") Manufacturer mn) {
//		Account user = sessionService.get("userLogin");
//		mn.setUser(user);
//		mn.setDel(true);
		mnDAO.save(mn);
		return "redirect:/admin/manufacturer";
	}
	
	@RequestMapping("/manufacturer/delete")
	public String delete(@ModelAttribute("mn") Manufacturer mn) {

//		Account user = sessionService.get("userLogin");
//		mn.setUser(user);
//		mn.setDel(false);
		mnreps.save(mn);
		return "redirect:/admin/manufacturer";
	}
	
}
