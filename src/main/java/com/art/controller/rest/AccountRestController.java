package com.art.controller.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.user.AccountDAO;
import com.art.dao.user.AccountRoleDAO;
import com.art.dao.user.InforAddressDAO;
import com.art.dao.user.RoleDAO;
import com.art.dto.account.AccountDTO;
import com.art.mapper.AccountMapper;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;
import com.art.models.user.InforAddress;
import com.art.models.user.Role;
import com.art.utils.Path;

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

	@GetMapping(value = "/userLogin")
	public ResponseEntity<AccountDTO> getArtDev(Model model) {
		// Lấy thông tin người dùng từ SecurityContextHolder
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			AccountDTO accountDTO = AccountMapper.convertToDto(aDAO.findByEmail(authentication.getName()), promotionDAO,
					fDAO,pdDAO);
			return ResponseEntity.ok(accountDTO);
		}
		return ResponseEntity.notFound().build();
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
//	@GetMapping("/account")
//	public ResponseEntity<List<Account>> getAccounts() {
//		List<Account> accounts = aDAO.findAll();
////		List<AccountDTO> accountDTOs = accounts.stream().map(AccountMapper::convertToDto).collect(Collectors.toList());
//		return ResponseEntity.ok(accounts);
//	}

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

	/*
	 * Thêm người dùng mới
	 */
	@PostMapping("/account")
	public ResponseEntity<Account> create(@RequestBody AccountDTO accountDTO) {
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

		account.setUserRole(accountRoles);
		aDAO.save(account);

		return ResponseEntity.ok(account);
	}

	/*
	 * Cập nhật thông tin người dùng
	 */
	@PutMapping("/account/{id}")
	public ResponseEntity<Account> update(@RequestBody AccountDTO accountDTO, @PathVariable("id") String key) {
		Account account = AccountMapper.convertToAccount(accountDTO);
		if (!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
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

		account.setUserRole(accountRoles);
		aDAO.save(account);
		return ResponseEntity.ok(account);
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
	public ResponseEntity<InforAddress> createAddress(@RequestBody InforAddress inforAddress,
			@PathVariable("id") String key) {
		Account account = aDAO.findById(key).get();
		inforAddress.setUser(account);
		inforAddressDAO.save(inforAddress);
		return ResponseEntity.ok(inforAddress);
	}

	/*
	 * Cập nhật địa chỉ
	 */
	@PutMapping("/account/{id}/address/{phone}")
	public ResponseEntity<InforAddress> updateAddress(@RequestBody InforAddress inforAddress,
			@PathVariable("id") String key, @PathVariable("phone") String phone) {
		Account account = aDAO.findById(key).get();
		inforAddress.setUser(account);
		if (!inforAddressDAO.existsById(phone)) {
			return ResponseEntity.notFound().build();
		}
		inforAddressDAO.save(inforAddress);
		return ResponseEntity.ok(inforAddress);
	}

	/*
	 * Xóa địa chỉ
	 */
	@DeleteMapping("/account/{id}/address/{phone}")
	public ResponseEntity<Void> deleteAddress(@PathVariable("id") String id, @PathVariable("phone") String phone) {
		if (!inforAddressDAO.existsById(phone)) {
			return ResponseEntity.notFound().build();
		}
		inforAddressDAO.deleteById(phone);
		return ResponseEntity.ok().build();
	}
}
