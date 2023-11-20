package com.art.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.user.AccountDAO;
import com.art.dto.account.AccountDTO;
import com.art.mapper.AccountMapper;
import com.art.models.user.Account;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class AccountRestController {
	@Autowired
	AccountDAO aDAO;

	@GetMapping("/account")
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = aDAO.findAll();
		List<AccountDTO> accountDTOs = accounts.stream().map(AccountMapper::convertToDto).collect(Collectors.toList());
		return ResponseEntity.ok(accountDTOs);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") String key) {
		if(!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		Account accounts = aDAO.findById(key).get();
		AccountDTO accountDTOs = AccountMapper.convertToDto(accounts);
		return ResponseEntity.ok(accountDTOs);
	}

	@PostMapping("/account")
	public ResponseEntity<Account> create(@RequestBody Account account) {
		if (aDAO.existsById(account.getAccountId())) {
			return ResponseEntity.badRequest().build();
		}
		aDAO.save(account);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/account/{id}")
	public ResponseEntity<Account> update(@RequestBody Account account, @PathVariable("id") String key) {
		if (!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		aDAO.save(account);
		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") String key) {
		if (!aDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		aDAO.deleteById(key);
		return ResponseEntity.ok().build();
	}
}
