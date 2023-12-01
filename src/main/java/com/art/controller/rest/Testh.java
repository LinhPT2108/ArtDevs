package com.art.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.art.config.auth.AuthenticationRequest;
import com.art.config.auth.AuthenticationResponse;
import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.user.AccountDAO;
import com.art.service.auth.AuthenticationService;
import com.art.service.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Testh {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	FlashSaleDAO fDAO;

	@Autowired
	PromotionalDetailsDAO promotionDAO;

	@Autowired
	ProductDAO pdDAO;

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenUtil;

//	@PostMapping("/api/login")
//	public ResponseEntity<AuthenticationResponse> login(@RequestParam("email") String email,
//			@RequestParam("password") String password) {
//		System.out.println("Email" + email);
//		AuthenticationRequest request = new AuthenticationRequest(email, password);
//		return ResponseEntity.ok(authenticationService.authenticate(request));
//	}
	
	@PostMapping("/api/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}

	@GetMapping("/demo/access")
	public ResponseEntity<String> loginAccess(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return ResponseEntity.ok("Authenticaion and Authorization is Succeed");
	}

//	@PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public String login(
//	        @RequestParam("email") String email,
//	        @RequestParam("password") String password,
//	        RedirectAttributes redirectAttributes
//	) {
//	    AuthenticationRequest request = new AuthenticationRequest(email, password);
//	    Account user = accountDAO.findByEmail(email).orElseThrow();
//	    List<AccountRole> accountRoles = null;
//	    if (user != null) {
//	        accountRoles = accountDAO.getAccountRoles(user.getEmail());
//	    }
//
//	    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//	    List<AccountRole> sets = new ArrayList<>();
//	    accountRoles.stream().forEach(accountRole -> sets.add(new AccountRole(accountRole.getUser(), accountRole.getRole())));
//	    user.setUserRole(sets);
//	    sets.stream().forEach(set -> authorities.add(new SimpleGrantedAuthority(set.getRole().getRoleName())));
//
//	    // Tạo token và thêm vào header
//	    String token = jwtTokenUtil.generateToken(user, authorities);
//
//	    // Chuyển hướng đến trang chủ
//	    RedirectView redirectView = new RedirectView();
//	    redirectView.setUrl("/");  // Điều này giả sử rằng đường dẫn "/" là trang chủ của bạn.
//
//	    redirectAttributes.addFlashAttribute("message", "Đăng nhập thành công!");
//	    String redirectUrl = "/ArtDevs";
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add(HttpHeaders.AUTHORIZATION, token);
//	    headers.add(HttpHeaders.LOCATION, redirectUrl);
//
//	    return "redirect:/";
//	    		
//	    //return new ResponseEntity<>(headers, HttpStatus.FOUND);
//	}

}
