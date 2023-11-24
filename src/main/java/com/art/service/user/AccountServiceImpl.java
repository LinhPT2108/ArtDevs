package com.art.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.user.AccountDAO;
import com.art.models.user.Account;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public Account findByEmail(String email) {
		if(accountDAO.findByEmail(email)!=null) {
			System.out.println("Full name: " +accountDAO.findByEmail(email).getFullname());
		}else {
			System.out.println("null");
		}
		return accountDAO.findByEmail(email);
	}

	@Override
	public Account findByUsername(String username) {
		return accountDAO.findById(username).orElse(null);
	}

}
