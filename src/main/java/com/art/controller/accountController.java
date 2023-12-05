package com.art.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.dao.user.AccountDAO;
import com.art.models.user.Account;

@Controller
@RequestMapping("/account")
public class accountController {
	
	@Autowired
	private AccountDAO aDAO;

	@GetMapping(value = "/login")
	public String getLogin() {
		return "index";
	}

	@GetMapping(value = "/register")
	public String getRegister() {
		return "index";
	}
	
	/*
	 * Cập nhật thông tin người dùng
	 */
	@PutMapping("/verify-code/{accountId}")
	public ResponseEntity<?> verifyCode(@PathVariable("accountId") String accountId, @RequestBody String verify) {
	    Account account = aDAO.findById(accountId).orElseThrow();
	    String message = "";

	    Map<String, Object> messages = new HashMap<>();
	    if (account != null) {
	        if (account.getVerifyCode().equalsIgnoreCase(verify)) {
	            account.setVerifyCode(null);
	            account.setStatus(false);
	            aDAO.save(account);
	            message = "Xác thực thành công !";
	    	    messages.put("message", message);
	    	    return ResponseEntity.ok(messages);
	        } else {
	            message = "Mã xác nhận không hợp lệ. Vui lòng kiểm tra lại !";
	            messages.put("message", message);
	    	    return ResponseEntity.badRequest().body(messages);
	        }
	    } else {
	        message = "Tài khoản không tồn tại !";
	        messages.put("message", message);
	        return ResponseEntity.badRequest().body(messages);
	    }
	}

	@GetMapping(value = "/profile")
	public String getProfile(Model model) {
		return "index";
	}

	@GetMapping(value = "/verify-code/**")
	public String getVerirfyCode(Model model) {
		return "index";
	}

	@GetMapping(value = "/address")
	public String getAddress(Model model) {
		return "index";
	}

	@GetMapping(value = "/change-password")
	public String getChangePassword(Model model) {
		return "index";
	}

	@GetMapping(value = "/forgot-password")
	public String getForgotPassword(Model model) {
		return "index";
	}

	// @GetMapping(value = "/purchase-order/{type}")
	// public String getOrder(Model model) {
	// 	return "index";
	// }
}
