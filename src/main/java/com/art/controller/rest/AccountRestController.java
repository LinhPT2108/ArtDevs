package com.art.controller.rest;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.art.dao.activity.WishListDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.user.AccountDAO;
import com.art.dao.user.AccountRoleDAO;
import com.art.dao.user.InforAddressDAO;
import com.art.dao.user.RoleDAO;
import com.art.dto.account.AccountDTO;
import com.art.dto.account.ChangePasswordDTO;
import com.art.mapper.AccountMapper;
import com.art.models.MailInfo;
import com.art.models.activity.WishList;
import com.art.models.product.Product;
import com.art.models.promotion.Order;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;
import com.art.models.user.InforAddress;
import com.art.models.user.Role;
import com.art.service.MailerServiceImpl;
import com.art.service.ParamService;
import com.art.utils.Path;
import com.art.utils.validUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class AccountRestController {
	@Autowired
	AccountDAO aDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	AccountRoleDAO accountRoleDAO;

	@Autowired
	InforAddressDAO inforAddressDAO;

	@Autowired
	FlashSaleDAO fDAO;

	@Autowired
	PromotionalDetailsDAO promotionDAO;

	@Autowired
	ProductDAO pdDAO;

	@Autowired
	HttpSession session;

	@Autowired
	MailerServiceImpl mailer;

	@Autowired
	ParamService paramService;

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	WishListDAO wishListDAO;

	private PasswordEncoder passwordEncoder;

	@GetMapping(value = "/userLogin")
	public ResponseEntity<AccountDTO> getArtDev(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("authentication: " + authentication);
		try {
			if (authentication != null) {
				AccountDTO accountDTO = AccountMapper.convertToDto(aDAO.findByEmail(authentication.getName()),
						promotionDAO, fDAO, pdDAO);
				return ResponseEntity.ok(accountDTO);
			}
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok(null);
		}

		return ResponseEntity.ok(null);
	}

	/*
	 * Lấy tất cả tài khoản
	 */
	@GetMapping("/account")
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = aDAO.findAll();
		List<AccountDTO> accountDTOs = accounts.stream()
				.map(account -> AccountMapper.convertToDto(account, promotionDAO, fDAO, pdDAO))
				.collect(Collectors.toList());
		return ResponseEntity.ok(accountDTOs);
	}

	@GetMapping(value = "/account/user")
	public Principal getLogin(Principal principal) {
		System.out.println("co gì hoong : " + principal.toString());
		return principal;
	}
	// @GetMapping("/account")
	// public ResponseEntity<List<Account>> getAccounts() {
	// List<Account> accounts = aDAO.findAll();
	//// List<AccountDTO> accountDTOs =
	// accounts.stream().map(AccountMapper::convertToDto).collect(Collectors.toList());
	// return ResponseEntity.ok(accounts);
	// }

	/*
	 * Lấy tất cả tài khoản theo id
	 */
	@GetMapping("/account/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") String key) {
		if (!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		Account accounts = aDAO.findById(key).get();
		AccountDTO accountDTOs = AccountMapper.convertToDto(accounts, promotionDAO, fDAO, pdDAO);
		return ResponseEntity.ok(accountDTOs);
	}

	@GetMapping("/account/findAccount/{email}")
	public ResponseEntity<Account> getAccountByEmail(@PathVariable("email") String email) {
		Account account = aDAO.findByEmail(email);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		Account accounts = aDAO.findByEmail(email);
		return ResponseEntity.ok(accounts);
	}

	/*
	 * Thêm người dùng mới
	 */
	@PostMapping("/account")
	public ResponseEntity<Account> create(@RequestBody AccountDTO accountDTO) throws MessagingException {
		Account account = AccountMapper.convertToAccount(accountDTO);
		if (aDAO.existsById(account.getAccountId())) {
			return ResponseEntity.badRequest().build();
		}
		List<AccountRole> accountRoles = new ArrayList<>();
		List<String> roleNames = AccountMapper.getRoles(accountDTO);

		if (roleNames != null) {
			for (String roleName : roleNames) {
				Role role = roleDAO.findByRoleName(roleName);
				if (role != null) {
					AccountRole accountRole = new AccountRole();
					accountRole.setUser(account);
					accountRole.setRole(role);
					accountRoles.add(accountRole);
				}
			}
		}
		String verifyCode = getVerifyCode();
		account.setStatus(true);
		account.setVerifyCode(verifyCode);
		account.setUserRole(accountRoles);
		account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

		aDAO.save(account);

		mailer.sendVerify(new MailInfo(account.getEmail(), "Xác nhận tài khoản ArtGroupES",
				"Chào mừng bạn đến với ART GROUP EST.2023. Đây là mã xác nhận của bạn: " + verifyCode));
		return ResponseEntity.ok(account);
	}

	private String getVerifyCode() {
		String randomString = UUID.randomUUID().toString().replace("-", "");
		String randomPart = randomString.substring(0, 8);
		String timestampPart = String.valueOf(System.currentTimeMillis());
		String accountId = randomPart + timestampPart;
		return accountId;
	}

	/*
	 * Cập nhật thông tin người dùng
	 */
	@PutMapping("/account/{id}")
	public ResponseEntity<?> update(@RequestBody AccountDTO accountDTO, @PathVariable("id") String key) {
		System.out.println(accountDTO.toString());
		Account account = AccountMapper.convertToAccount(accountDTO);
		Map<String, String> errors = new HashMap<>();
		try {
			System.out.println(account);

			if (!aDAO.existsById(key)) {
				return ResponseEntity.notFound().build();
			}
			if (accountDTO.getFullname().isBlank()) {
				errors.put("fullname", "Vui lòng nhập họ tên");
			} else if (validUtil.containsSpecialCharacters(accountDTO.getFullname())
					|| validUtil.containsNumber(accountDTO.getFullname())) {
				errors.put("fullname", "Họ tên không được chứa số và kí tự đặt biệt");
			}

			if (!errors.isEmpty()) {
				return ResponseEntity.ok(errors);
			} else {
				List<AccountRole> accountRoles = new ArrayList<>();
				List<String> roleNames = AccountMapper.getRoles(accountDTO);

				if (roleNames != null) {
					for (String roleName : roleNames) {
						Role role = roleDAO.findByRoleName(roleName);
						if (role != null) {
							AccountRole accountRole = new AccountRole();
							accountRole.setUser(account);
							accountRole.setRole(role);
							accountRoles.add(accountRole);
						}
					}
				}
				account.setUserRole(accountRoles);
				aDAO.save(account);
				return ResponseEntity.ok(account);
			}
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.ok(null);
		}

	}

	// cập nhật ảnh người dùng
	@PutMapping(value = "/account/update-avatar/{id}")
	public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody MultipartFile avatar) {
		try {
			Account account = aDAO.findById(id).get();
			String avatarName = paramService.saveFile(avatar, "/avatar").getName();
			account.setImage(avatarName);
			System.out.println(avatarName);
			Map<String, String> rs = new HashMap<String, String>();
			rs.put("avatarName", avatarName);
			aDAO.save(account);
			return ResponseEntity.ok(rs);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
	}

	/*
	 * Xóa người dùng
	 */
	@DeleteMapping("/account/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String key) {
		if (!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		aDAO.deleteById(key);
		return ResponseEntity.ok().build();
	}

	/*
	 * Lấy ra tất cả địa chỉ
	 */
	@GetMapping("/account/address")
	public ResponseEntity<List<InforAddress>> getInfors() {
		return ResponseEntity.ok(inforAddressDAO.findAll());
	}

	/*
	 * Lấy ra tất cả địa chỉ của một người dùng
	 */
	@GetMapping("/account/{id}/address")
	public ResponseEntity<List<InforAddress>> getInforByUser(@PathVariable("id") String key) {
		List<InforAddress> inforAddresses = inforAddressDAO.findByUser(aDAO.findById(key).get());
		return ResponseEntity.ok(inforAddresses);
	}

	/*
	 * Lấy địa chỉ theo số điện thoại người dùng
	 */
	@GetMapping(value = { "/account/{id}/address/{phone}", "/account/address/{phone}" })
	public ResponseEntity<InforAddress> getInforByPhone(@PathVariable("id") Optional<String> key,
			@PathVariable("phone") String phone) {
		InforAddress infor = inforAddressDAO.findById(phone).get();
		if (!inforAddressDAO.existsById(phone)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(infor);
	}

	/*
	 * Thêm địa chỉ mới
	 */
	@PostMapping("/account/{id}/address")
	public ResponseEntity<?> createAddress(@RequestBody InforAddress inforAddress, @PathVariable("id") String key) {
		System.out.println("line 267: " + key);
		Account account = aDAO.findById(key).get();
		boolean isExist = inforAddressDAO.existsById(inforAddress.getPhoneNumber());
		if (isExist) {
			return ResponseEntity.ok(409);
		}
		inforAddress.setUser(account);
		inforAddressDAO.save(inforAddress);

		return ResponseEntity.ok(inforAddress);
	}

	/*
	 * Cập nhật địa chỉ
	 */
	@PutMapping("/account/{id}/address/{phone}")
	public ResponseEntity<?> updateAddress(@RequestBody InforAddress inforAddress, @PathVariable("id") String key,
			@PathVariable("phone") String phone) {
		Account account = aDAO.findById(key).get();
		inforAddress.setUser(account);
		if (!inforAddressDAO.existsById(phone)) {
			return ResponseEntity.ok(404);
		}
		inforAddressDAO.save(inforAddress);
		return ResponseEntity.ok(inforAddress);
	}

	/*
	 * Xóa địa chỉ
	 */
	@DeleteMapping("/account/{id}/address/{phone}")
	public ResponseEntity<?> deleteAddress(@PathVariable("id") String id, @PathVariable("phone") String phone) {
		if (!inforAddressDAO.existsById(phone)) {
			return ResponseEntity.ok(404);
		}
		inforAddressDAO.deleteById(phone);
		return ResponseEntity.ok(200);
	}

	@PostMapping("/account/change-password")
	public ResponseEntity<?> postMethodName(@RequestBody ChangePasswordDTO e) {
		System.out.println(e.getCurrentPassword() + " - " +
				e.getConfirmPassword() + " - " +
				e.getNewPassword());
		try {
			Map<String, String> errors = new HashMap<>();
			passwordEncoder = new BCryptPasswordEncoder();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (e.getConfirmPassword() != "" || e.getCurrentPassword() != "" || e.getNewPassword() != "") {
				System.out.println(authentication.getName());
				Account account = aDAO.findByEmail(authentication.getName());
				String userLoginPassword = account.getPassword();
				System.out.println(userLoginPassword);
				System.out.println(passwordEncoder.matches(e.getCurrentPassword(), userLoginPassword));
				if (passwordEncoder.matches(e.getCurrentPassword(), userLoginPassword)) {
					account.setPassword(new BCryptPasswordEncoder().encode(e.getConfirmPassword()));
					aDAO.save(account);
					return ResponseEntity.ok(AccountMapper.convertToDto(account, promotionDAO, fDAO, pdDAO));
				} else {
					errors.put("currentPasswordError", "Mật khẩu hiện tại không chính xác");
					return ResponseEntity.ok(errors);
				}
			} else {
				return ResponseEntity.ok(500);
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return ResponseEntity.ok(500);
		}
	}

	@GetMapping("/account/purchase-order/{type}/{accountId}")
	public ResponseEntity<?> getOrderType(@PathVariable("type") int type,
			@PathVariable("accountId") String accountId) {
		System.out.println(accountId + " - " + type);
		Account account = aDAO.findById(accountId).get();
		List<Order> orders = orderDAO.findByUser(account);
		return ResponseEntity.ok(orders);
	}



}
