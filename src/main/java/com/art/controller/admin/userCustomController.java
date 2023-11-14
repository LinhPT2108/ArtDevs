package com.art.controller.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.art.dao.user.RoleDAO;
import com.art.dao.user.UserCustomDAO;
import com.art.models.user.Account;
import com.art.models.user.Role;
import com.art.service.ParamService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class userCustomController {
	@Autowired
	RoleDAO roleDAO;
	@Autowired
	UserCustomDAO userCustomDAO;
	@Autowired
	HttpServletResponse response;
	@Autowired
	ParamService paramService;

	@GetMapping("/userCustom")
	public String UserCustom(@ModelAttribute("userCustom") Account userCustom, Model model) {
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		List<Role> listRole = roleDAO.findAll();
		List<Account> Accounts = userCustomDAO.findAll();
		model.addAttribute("roles", listRole);
		model.addAttribute("userCustoms", Accounts);
		return "admin/UserCustom";
	}

	@PostMapping("/userCustom/create")
	public String createUserCustom(@Valid @ModelAttribute("userCustom") Account usercustom , BindingResult bd,
			Model model, @RequestParam("avatar") MultipartFile avatar) {
		List<Role> listRole = roleDAO.findAll();
//		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (bd.hasErrors()) {
			return "admin/UserCustom";
			
		}
		if (!avatar.isEmpty()) {
			usercustom.setImage(paramService.save(avatar, "images/avatar").getName());
		}
		userCustomDAO.save(usercustom);
		return "redirect:/admin/userCustom";
	}

	@GetMapping("/userCustom/edit/{userId}")
	public String editUserCustom(@ModelAttribute("userCustom") Account userCustom, Model model,
			@PathVariable("userId") String userId) {
		try {
			model.addAttribute("views", "userCustom-form");
			model.addAttribute("title", "Quản lí tài khoản");
			model.addAttribute("typeButton", "Thêm");
			model.addAttribute("updateButton", "Cập nhật");
			model.addAttribute("deleteButton", "Xóa");
			List<Role> listRole = roleDAO.findAll();
			List<Account> userCustoms = userCustomDAO.findAll();
			Account usercus = userCustomDAO.getById(userId);
			model.addAttribute("userCustom", usercus);
			model.addAttribute("roles", usercus.getRoleName());
			model.addAttribute("userCustoms", userCustoms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/UserCustom";
	}

	@RequestMapping("/userCustom/update")
	public String updateUserCustom(@ModelAttribute("userCustom") Account userCustom, BindingResult bd, Model model,
			@RequestParam("avatar") MultipartFile avatar) {
		List<Role> listRole = roleDAO.findAll();
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (bd.hasErrors()) {
			return "admin/index";
		}
		String userId = paramService.getString("userId", "");
		Account currentUser = userCustomDAO.getById(userId);
		if (!avatar.isEmpty()) {
			userCustom.setImage(paramService.save(avatar, "images/avatar").getName());
		}else {
			userCustom.setImage(currentUser.getImage());
		}
		userCustomDAO.save(userCustom);

		return "redirect:/admin/userCustom/edit/" + userCustom.getAccountId();
	}

	@RequestMapping("/userCustom/delete/{userId}")
	public String deleteUserCustom(@PathVariable("userId") String userId, Model model,@ModelAttribute("userCustom") Account userCustom,
			@RequestParam("avatar") MultipartFile avatar) {
		List<Role> listRole = roleDAO.findAll();
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (!avatar.isEmpty()) {
			userCustom.setImage(paramService.save(avatar, "images/avatar").getName());
		}else {
			userCustom.setImage(userCustomDAO.getById(userId).getImage());
		}
		userCustom.setDel(true);
		userCustomDAO.save(userCustom);
		return "redirect:/admin/userCustom";
	}
}
