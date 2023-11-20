package com.art.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.art.dto.account.AccountDTO;
import com.art.dto.account.KeywordSuggestionsDTO;
import com.art.models.user.Account;

public class AccountMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static AccountDTO convertToDto(Account account) {
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
		accountDTO.setRoles(getRoles(account));
		accountDTO.setKeywordSuggestions(getKeywordSuggestions(account));
		return accountDTO;
	}

	private static List<String> getRoles(Account account) {
		return account.getUserRole().stream().map(accountRole -> accountRole.getRole().getRoleName())
				.collect(Collectors.toList());
	}

	private static List<KeywordSuggestionsDTO> getKeywordSuggestions(Account account) {
		return account.getUserKeyword().stream()
				.map(keywordSuggestions -> new KeywordSuggestionsDTO(keywordSuggestions.getId(),
						keywordSuggestions.getDate(), keywordSuggestions.getKeywords()))
				.collect(Collectors.toList());
	}
}
