package com.art.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.promotion.FlashSaleDTO;
import com.art.dto.promotion.PromotionalDetailsListDTO;
import com.art.models.promotion.FlashSale;

public class FlashSaleMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	PromotionalDetailsDAO proDao;
	@Autowired
	FlashSaleDAO fDAO;

	public static FlashSaleDTO convertToDto(FlashSale flashSale, PromotionalDetailsDAO proDao, FlashSaleDAO fDAO, ProductDAO productDAO) {
		FlashSaleDTO flashSaleDTO = modelMapper.map(flashSale, FlashSaleDTO.class);
		flashSaleDTO.setPromotionalDetailsList(getPromotionalDetailsListDTO(flashSale, proDao, fDAO, productDAO));
		return flashSaleDTO;
	}

	private static List<PromotionalDetailsListDTO> getPromotionalDetailsListDTO(FlashSale flashSale,
			PromotionalDetailsDAO proDao, FlashSaleDAO fDAO, ProductDAO productDAO) {
		return flashSale.getPromotionalDetailsList().stream()
				.map(pmt -> new PromotionalDetailsListDTO(pmt.getId(),
						ProductMapper.convertToDto(pmt.getProduct(), proDao, fDAO, productDAO),
						pmt.getDiscountedPrice(), pmt.getDiscountedQuantity(), pmt.getQuantitySold(), pmt.isStatus()))
				.collect(Collectors.toList());
	}
}
