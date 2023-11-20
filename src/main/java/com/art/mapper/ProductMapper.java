package com.art.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.art.dto.product.CommentDTO;
import com.art.dto.product.PriceDTO;
import com.art.dto.product.ProductDTO;
import com.art.dto.product.ProductDetailDTO;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;

public class ProductMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static ProductDTO convertToDto(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		productDTO.setImages(getImages(product));
		productDTO.setComments(getComment(product));
		productDTO.setProductDetails(getProductDetails(product));
		return productDTO;
	}

	private static List<String> getImages(Product product) {
		return product.getProductImage().stream().map(image -> image.getImage()).collect(Collectors.toList());
	}

	private static List<CommentDTO> getComment(Product product) {
		return product
				.getProductComment().stream().map(comment -> new CommentDTO(comment.getId(), comment.getStar(),
						comment.getContent(), comment.getDate(), comment.getUser().getFullname()))
				.collect(Collectors.toList());
	}

	private static List<ProductDetailDTO> getProductDetails(Product product) {
		List<ProductDetailDTO> prDetailDTOs = product.getProductDetail().stream().map(pd -> new ProductDetailDTO(
				pd.getId(), pd.getQuantityInStock(), pd.getSize(),pd.getColor(),pd.getWeight(),pd.getPower(),pd.getProductionDate(),getPrice(pd)
				)).collect(Collectors.toList());
		return prDetailDTOs;
	}

	private static List<PriceDTO> getPrice(ProductDetail productDetail) {
		return productDetail.getProductPrice().stream()
				.map(price -> new PriceDTO(price.getId(), price.getPrice(), price.getCreatedDate()))
				.collect(Collectors.toList());
	}
}
