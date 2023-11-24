package com.art.service.user;

import com.art.models.user.Account;

public interface AccountService {
	Account findByEmail(String email);

	Account findByUsername(String username);
}
