package com.art.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.art.models.user.Account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Kiểm tra trạng thái đăng nhập bằng session
		Account user = (Account) sessionService.get("userLogin");

		// Kiểm tra đường dẫn yêu cầu
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		// Kiểm tra quyền truy cập
		if (user == null && requiresAuthentication(requestURI)) {
			response.sendRedirect("/account/login");
			return false;
		} else if (user != null && !hasAccess(user, requestURI)) {
			System.out.println(1);
			response.sendRedirect("/access-denied");
			return false;
		} else if (user != null && (requestURI.contains("login") || requestURI.contains("register"))) {
			System.out.println(3);
			response.sendRedirect("/");
			return false;
		}
		System.out.println(2);

		return true;
	}

	private boolean requiresAuthentication(String requestURI) {
		return !requestURI.equals("/account/login") && !requestURI.equals("/account/register")
				&& !requestURI.equals("/account/forgot-password") && !requestURI.equals("/account/send-code")
				&& !requestURI.contains("/account/verify-code");
	}

	private boolean hasAccess(Account user, String requestURI) {
		if (user.getUserRole().get(0).getRole().getRoleName().equals("admin") && !requestURI.startsWith("/checkout")
				&& !requestURI.contains("cart")) {
			return true;
		} else if (user.getUserRole().get(0).getRole().getRoleName().equals("user") && !requestURI.contains("/admin")) {
			return true;
		} else if (requestURI.contains("login") || requestURI.contains("register")) {
			return true;
		}
		return false;
	}
}
