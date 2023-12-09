package com.art.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.art.dao.user.AccountDAO;
import com.art.dao.user.AccountRoleDAO;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;
import com.art.models.user.Role;
import com.art.service.ParamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class userCustomController {
	@Autowired
	RoleDAO roleDAO;

	@Autowired
	HttpServletRequest req;

	@Autowired
	AccountRoleDAO acRoleDAO;
	@Autowired
	AccountDAO userCustomDAO;
	@Autowired
	HttpServletResponse response;
	@Autowired
	ParamService paramService;

	@GetMapping("/userCustom")
	public String UserCustom(@ModelAttribute("userCustom") Account userCustom, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authUser", authentication);
		System.out.println("authUser" + authentication.getName());
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		List<Role> listRole = roleDAO.findAll();
		List<Account> Accounts = userCustomDAO.findAll();
		model.addAttribute("roles", listRole);
		model.addAttribute("userCustoms", Accounts);
		System.out.println("count: " + userCustomDAO.checkCountAdmin("admin"));
		return "admin/UserCustom";
	}

	@PostMapping("/userCustom/create")
	public String createUserCustom(@Valid @ModelAttribute("userCustom") Account usercustom, BindingResult bd,
			Model model, @RequestParam("avatar") MultipartFile avatar) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authUser", authentication);
		List<Role> listRole = roleDAO.findAll();
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (bd.hasErrors()) {
			List<Account> Accounts = userCustomDAO.findAll();
			model.addAttribute("userCustoms", Accounts);
			return "admin/UserCustom";

		}
		if (!avatar.isEmpty()) {
			try {
				usercustom.setImage(paramService.saveFile(avatar, "/avatar").getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<AccountRole> acrole = new ArrayList<>();
		for (Role role : listRole) {
			String param = req.getParameter(role.getRoleName());
			if (param != null) {
				acrole.add(new AccountRole(usercustom, role));
			}
		}
		usercustom.setUserRole(acrole);
		usercustom.setPassword(new BCryptPasswordEncoder().encode(usercustom.getPassword()));
		userCustomDAO.save(usercustom);
		return "redirect:/admin/userCustom";
	}

	@RequestMapping("/userCustom/update")
	public String updateUserCustom(@ModelAttribute("userCustom") Account userCustom, BindingResult bd, Model model,
			@RequestParam("avatar") MultipartFile avatar) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authUser", authentication);
		List<Role> listRole = roleDAO.findAll();
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (bd.hasErrors()) {
			List<Account> Accounts = userCustomDAO.findAll();
			model.addAttribute("userCustoms", Accounts);
			return "admin/UserCustom";
		}

		Account currentUser = userCustomDAO.getById(userCustom.getAccountId());
		if (!avatar.isEmpty()) {
			try {
				userCustom.setImage(paramService.saveFile(avatar, "/avatar").getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			userCustom.setImage(currentUser.getImage());
		}
//		List<AccountRole> listDeleteRole = acRoleDAO.findByUser(userCustom);
//		System.out.println("list Delete " + listDeleteRole.size());
//		for (AccountRole accountRole : listDeleteRole) {
//			acRoleDAO.delete(accountRole);
//		}
		acRoleDAO.deleteByUser(userCustom);
		List<AccountRole> acrole = new ArrayList<>();
		for (Role role : listRole) {
			String param = req.getParameter(role.getRoleName());
			if (param != null) {
				acrole.add(new AccountRole(userCustom, role));
			}
		}
		
		userCustom.setUserRole(acrole);
		userCustom.setPassword(currentUser.getPassword());
		
		userCustomDAO.save(userCustom);

		return "redirect:/admin/userCustom/edit/" + userCustom.getAccountId();
	}

	@GetMapping("/userCustom/edit/{userId}")
	public String editUserCustom(@ModelAttribute("userCustom") Account userCustom, Model model,
			@PathVariable("userId") String userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authUser", authentication);
		try {
			model.addAttribute("views", "userCustom-form");
			model.addAttribute("title", "Quản lí tài khoản");
			model.addAttribute("typeButton", "Thêm");
			model.addAttribute("updateButton", "Cập nhật");
			model.addAttribute("deleteButton", "Xóa");
			List<Role> listRole = roleDAO.findAll();
			List<Account> userCustoms = userCustomDAO.findAll();
			Account usercus = userCustomDAO.getById(userId);
			System.out.println("password" + usercus.getPassword());
			List<AccountRole> accRole = usercus.getUserRole();
//			List<AccountRole> accRole = acRoleDAO.findByUser(usercus);
			List<Integer> IntRole = new ArrayList<Integer>();
			if (accRole != null) {
				for (AccountRole acr : accRole) {
					IntRole.add(acr.getRole().getId());
				}
			}
			model.addAttribute("IndexRole", IntRole);
			model.addAttribute("roles", listRole);
			model.addAttribute("accroles", accRole);
			model.addAttribute("userCustom", usercus);
			model.addAttribute("userCustoms", userCustoms);
			model.addAttribute("CheckEdit", true);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "admin/UserCustom";
	}

	@RequestMapping("/userCustom/delete/{userId}")
	public String deleteUserCustom(@PathVariable("userId") String userId, Model model,
			@ModelAttribute("userCustom") Account userCustom, @RequestParam("avatar") MultipartFile avatar) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("authUser", authentication);
		List<Role> listRole = roleDAO.findAll();
		model.addAttribute("views", "userCustom-form");
		model.addAttribute("title", "Quản lí tài khoản");
		model.addAttribute("typeButton", "Thêm");
		model.addAttribute("updateButton", "Cập nhật");
		model.addAttribute("deleteButton", "Xóa");
		model.addAttribute("roles", listRole);
		if (!avatar.isEmpty()) {
			userCustom.setImage(paramService.save(avatar, "images/avatar").getName());
		} else {
			userCustom.setImage(userCustomDAO.getById(userId).getImage());
		}
		int a = userCustomDAO.checkCountAdmin("admin");
		if (a > 1) {
			userCustom.setStatus(true);
			userCustomDAO.save(userCustom);
		} else {
			System.out.println("Lỗi");
		}

		return "redirect:/admin/userCustom";
	}
}
