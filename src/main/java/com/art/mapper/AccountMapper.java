package com.art.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.art.dto.account.AccountDTO;
import com.art.dto.account.KeywordSuggestionsDTO;
import com.art.models.user.Account;
import com.art.models.user.InforAddress;

public class AccountMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static AccountDTO convertToDto(Account account) {
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
		accountDTO.setRoles(getRoles(account));
		accountDTO.setKeywordSuggestions(getKeywordSuggestions(account));
		accountDTO.setInforAddresses(getAddress(account));
		return accountDTO;
	}

	private static List<String> getRoles(Account account) {
		return account.getUserRole().stream().map(accountRole -> accountRole.getRole().getRoleName())
				.collect(Collectors.toList());
	}

	private static List<InforAddress> getAddress(Account account) {
		return account
				.getUserInfor().stream().map(infor -> new InforAddress(infor.getPhoneNumber(), infor.getCity(),
						infor.getDistrict(), infor.getWard(), infor.getSpecific(), account))
				.collect(Collectors.toList());
	}

	private static List<KeywordSuggestionsDTO> getKeywordSuggestions(Account account) {
		return account.getUserKeyword().stream()
				.map(keywordSuggestions -> new KeywordSuggestionsDTO(keywordSuggestions.getId(),
						keywordSuggestions.getDate(), keywordSuggestions.getKeywords()))
				.collect(Collectors.toList());
	}

	public static Account convertToAccount(AccountDTO accountDTO) {
		Account account = modelMapper.map(accountDTO, Account.class);
		return account;
	}

	public static List<String> getRoles(AccountDTO accountDTO) {
		return accountDTO.getRoles();
	}
}
