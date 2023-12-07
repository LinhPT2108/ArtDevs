package com.art.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import com.art.models.MailInfo;
import com.art.models.user.Account;
import com.art.service.MailerServiceImpl;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/account")
public class accountController {

	@Autowired
	private AccountDAO aDAO;

	@Autowired
	MailerServiceImpl mailer;

	@GetMapping(value = "/register")
	public String getRegister() {
		return "index";
	}

	@GetMapping(value = "/login")
	public String getLogin() {
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

	@PutMapping("/forgot-password/{accountId}")
	public ResponseEntity<?> forgotPassword(@PathVariable("accountId") String accountId) throws MessagingException {
		Account account = aDAO.findById(accountId).get();
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		String verifyCode = getVerifyCode();
		account.setVerifyCode(verifyCode);
		aDAO.save(account);
		mailer.sendVerify(new MailInfo(account.getEmail(), "Quên mật khẩu - ART GROUP EST.2023",
				"Đây là mã xác nhận của bạn: " + verifyCode));
		return ResponseEntity.ok().build();
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

	private String getVerifyCode() {
		String randomString = UUID.randomUUID().toString().replace("-", "");
		String randomPart = randomString.substring(0, 8);
		String timestampPart = String.valueOf(System.currentTimeMillis());
		String accountId = randomPart + timestampPart;
		return accountId;
	}

	@GetMapping(value = "/purchase-order/{type}")
	public String getOrder(Model model) {
		return "index";
	}
}
