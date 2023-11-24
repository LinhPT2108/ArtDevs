package com.art.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.art.dao.user.AccountDAO;
import com.art.models.user.Account;

@Service("myAccountService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountDAO accountDAO;

  public UserDetailsServiceImpl(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = accountDAO.findById(username).get();
    if (user == null) {
      throw new UsernameNotFoundException("User with username " + username + " not found");
    }
    return (UserDetails) user;
  }
}

