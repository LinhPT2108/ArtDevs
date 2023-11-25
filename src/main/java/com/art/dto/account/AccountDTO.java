package com.art.dto.account;

import java.util.List;

import com.art.models.user.InforAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	private String accountId;
	private String fullname;
	private String image;
	private String password;
	private String email;
	private String verifyCode;
	private boolean status;
	private List<String> roles;
	private List<InforAddress> inforAddresses;
	private List<KeywordSuggestionsDTO> keywordSuggestions;
	private List<CartDTO> carts; 
//	private List<WishList> wishLists;
	

}