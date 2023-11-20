package com.art.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.art.dao.product.CategoryDAO;
import com.art.dao.product.ManufacturerDAO;
import com.art.dto.product.CommentDTO;
import com.art.dto.product.PriceDTO;
import com.art.dto.product.ProductDTO;
import com.art.dto.product.ProductDetailDTO;
import com.art.models.product.Category;
import com.art.models.product.DetailDescription;
import com.art.models.product.Manufacturer;
import com.art.models.product.Price;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;

public class ProductMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static ProductDTO convertToDto(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		productDTO.setImages(getImagesDTO(product));
		productDTO.setComments(getCommentDTO(product));
		productDTO.setProductDetails(getProductDetailsDTO(product));
		return productDTO;
	}

	private static List<String> getImagesDTO(Product product) {
		return product.getProductImage().stream().map(image -> image.getImage()).collect(Collectors.toList());
	}

	private static List<CommentDTO> getCommentDTO(Product product) {
		return product
				.getProductComment().stream().map(comment -> new CommentDTO(comment.getId(), comment.getStar(),
						comment.getContent(), comment.getDate(), comment.getUser().getFullname()))
				.collect(Collectors.toList());
	}

	private static List<ProductDetailDTO> getProductDetailsDTO(Product product) {
		List<ProductDetailDTO> prDetailDTOs = product.getProductDetail().stream()
				.map(pd -> new ProductDetailDTO(pd.getId(), pd.getQuantityInStock(), pd.getSize(), pd.getColor(),
						pd.getWeight(), pd.getPower(), pd.getProductionDate(), getPriceDTO(pd)))
				.collect(Collectors.toList());
		return prDetailDTOs;
	}

	private static List<PriceDTO> getPriceDTO(ProductDetail productDetail) {
		return productDetail.getProductPrice().stream()
				.map(price -> new PriceDTO(price.getId(), price.getPrice(), price.getCreatedDate()))
				.collect(Collectors.toList());
	}

	public static Product convertToProduct(ManufacturerDAO manuDAO,CategoryDAO cateDAO,ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		product.setManufacturerProduct(getManufacturer(manuDAO,productDTO));
		product.setCategoryProduct(getCategory(cateDAO,productDTO));
		return product;
	}

	private static Category getCategory(CategoryDAO cateDAO, ProductDTO productDTO) {
		Optional<Category> category = cateDAO.findById(productDTO.getCategory().getCategoryId());
		return category.orElse(null);
	}

	private static Manufacturer getManufacturer(ManufacturerDAO manuDAO, ProductDTO productDTO) {
		System.out.println("id: "+productDTO.getManufacturer().getId());
		Manufacturer manu = manuDAO.findById(productDTO.getManufacturer().getId());
		return manu;
	}

	public static List<ProductDetail> getProductDetail(ProductDTO productDTO) {
		List<ProductDetail> productDetails = productDTO.getProductDetails().stream()
				.map(pd -> new ProductDetail(pd.getId(), pd.getQuantityInStock(), pd.getSize(), pd.getColor(),
						pd.getWeight(), pd.getPower(), pd.getProductionDate(), getPrice(pd)))
				.collect(Collectors.toList());
		return productDetails;
	}

	private static List<Price> getPrice(ProductDetailDTO productDetailDTO) {
		List<Price> prices = productDetailDTO.getPrices().stream()
				.map(pr -> new Price(pr.getId(),pr.getPrice(),pr.getCreatedDate(), convertoProductDetail(productDetailDTO)))
				.collect(Collectors.toList());
		return prices;
	}

	private static ProductDetail convertoProductDetail(ProductDetailDTO productDetailDTO) {
		ProductDetail pd = modelMapper.map(productDetailDTO, ProductDetail.class);
		return pd;
	}
	
	public static List<String> getImages(ProductDTO productDTO) {
		return productDTO.getImages();
	}

	public static List<DetailDescription> getdDescriptions(ProductDTO productDTO) {
		List<DetailDescription> descriptions = productDTO.getDescriptions().stream()
				.map(detail -> modelMapper.map(detail, DetailDescription.class))
				.collect(Collectors.toList());
		return descriptions;
	}
}