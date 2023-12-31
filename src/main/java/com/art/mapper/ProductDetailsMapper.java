package com.art.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.product.PriceDTO;
import com.art.dto.product.ProductDetailDTO;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.PromotionalDetails;

public class ProductDetailsMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ProductDetailDTO convertToDto(ProductDetail productDetail) {
        ProductDetailDTO productDetailDTO = modelMapper.map(productDetail, ProductDetailDTO.class);
        productDetailDTO.setPrices(getPrice(productDetail));
        return productDetailDTO;
    }

    private static List<PriceDTO> getPrice(ProductDetail productDetail) {
        return productDetail.getProductPrice().stream()
                .map(price -> new PriceDTO(price.getId(), price.getPrice(), price.getCreatedDate()))
                .collect(Collectors.toList());
    }

}
