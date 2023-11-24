package com.art.controller.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.CommentDAO;
import com.art.dao.activity.ImageCommentDAO;
import com.art.dao.activity.RecentlyViewedDAO;
import com.art.dao.activity.WishListDAO;
import com.art.dao.product.CategoryDAO;
import com.art.dao.product.DetailDescriptionDAO;
import com.art.dao.product.ImageDAO;
import com.art.dao.product.ManufacturerDAO;
import com.art.dao.product.PriceDAO;
import com.art.dao.product.ProductCartDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.product.ProductDTO;
import com.art.mapper.ProductMapper;
import com.art.models.activity.Comment;
import com.art.models.activity.ImageComment;
import com.art.models.activity.RecentlyViewed;
import com.art.models.activity.WishList;
import com.art.models.product.DetailDescription;
import com.art.models.product.Image;
import com.art.models.product.Price;
import com.art.models.product.Product;
import com.art.models.product.ProductCart;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.OrderDetail;
import com.art.models.promotion.PromotionalDetails;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class ProductRestController {
	@Autowired
	ProductDAO proDAO;
	@Autowired
	ManufacturerDAO manuDAO;
	@Autowired
	CategoryDAO cateDAO;
	@Autowired
	ProductDetailDAO pdDAO;
	@Autowired
	DetailDescriptionDAO detailDescriptionDAO;
	@Autowired
	ImageDAO imageDAO;
	@Autowired
	CommentDAO commentDAO;
	@Autowired
	ImageCommentDAO imageCommentDAO;
	@Autowired
	PriceDAO priceDAO;
	@Autowired
	ProductCartDAO productCartDAO;
	@Autowired
	PromotionalDetailsDAO promDao;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	RecentlyViewedDAO recentlyViewedDAO;
	@Autowired
	WishListDAO wishListDAO;

	@Autowired
	FlashSaleDAO fDAO;

	/*
	 * lấy danh sách sản phẩm
	 */
	@GetMapping("/product")
	public ResponseEntity<List<ProductDTO>> getProducts() {
		System.out.println("product123");
		List<Product> products = proDAO.findAll();
		List<ProductDTO> productDTOs = products.stream()
				.map(product -> ProductMapper.convertToDto(product, promDao, fDAO, proDAO))
				.collect(Collectors.toList());
		return ResponseEntity.ok(productDTOs);
	}

	// @GetMapping("/product")
	// public ResponseEntity<List<Product>> getProducts() {
	// 	System.out.println("product123");
	// 	List<Product> products = proDAO.findAll();
	// 	return ResponseEntity.ok(products);
	// }

	@GetMapping("/product-today")
	public ResponseEntity<List<ProductDTO>> getProductsToday() {
		System.out.println("product today");
		List<Product> products = proDAO.findByAvailable(true);
		Collections.shuffle(products);

		List<Product> randomProducts = products.stream().limit(24).collect(Collectors.toList());

		List<ProductDTO> productDTOs = randomProducts.stream()
				.map(product -> ProductMapper.convertToDto(product, promDao, fDAO))
				.collect(Collectors.toList());
		return ResponseEntity.ok(productDTOs);
	}

	/*
	 * Lấy sản phẩm theo mã sản phẩm
	 */
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") String key) {
		if (!proDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		Product product = proDAO.findById(key).get();
		ProductDTO productDTO = ProductMapper.convertToDto(product, promDao, fDAO, proDAO);

		return ResponseEntity.ok(productDTO);
	}

	/*
	 * Thêm sản phẩm mới
	 */
	@PostMapping("/product")
	public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO) {
		Product product = ProductMapper.convertToProduct(manuDAO, cateDAO, productDTO);
		if (proDAO.existsById(productDTO.getProductId())) {
			return ResponseEntity.badRequest().build();
		}
		proDAO.save(product);
		for (ProductDetail productDetail : ProductMapper.getProductDetail(productDTO)) {
			productDetail.setProduct(product);
			pdDAO.save(productDetail);
		}
		for (DetailDescription detailDescription : ProductMapper.getdDescriptions(productDTO)) {
			detailDescription.setProduct(product);
			detailDescriptionDAO.save(detailDescription);
		}
		for (int i = 0; i < ProductMapper.getImages(productDTO).size(); i++) {
			Image imageProduct = new Image(i + 1, ProductMapper.getImages(productDTO).get(i), product);
			imageDAO.save(imageProduct);
		}

		return ResponseEntity.ok(product);
	}

	/*
	 * Cập nhật sản phẩm
	 */
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> update(@RequestBody ProductDTO productDTO, @PathVariable("id") String key) {
		Product product = ProductMapper.convertToProduct(manuDAO, cateDAO, productDTO);
		if (!proDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		proDAO.save(product);
		for (ProductDetail productDetail : ProductMapper.getProductDetail(productDTO)) {
			productDetail.setProduct(product);
			pdDAO.save(productDetail);
		}
		for (DetailDescription detailDescription : ProductMapper.getdDescriptions(productDTO)) {
			detailDescription.setProduct(product);
			detailDescriptionDAO.save(detailDescription);
		}
		for (int i = 0; i < ProductMapper.getImages(productDTO).size(); i++) {
			Image imageProduct = new Image(i + 1, ProductMapper.getImages(productDTO).get(i), product);
			imageDAO.save(imageProduct);
		}
		return ResponseEntity.ok(product);
	}

	/*
	 * Xóa sản phẩm theo mã sản phảm
	 */
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String key) {
		if (!proDAO.existsById(key)) {
			return ResponseEntity.notFound().build();
		}
		Product product = proDAO.findById(key).get();
		deleteImagesProduct(product);
		deleteProductDetail(product);
		deleteProductDescription(product);
		deleteComment(product);
		deletePromotionDetail(product);
		deleteOrderDetail(product);
		deleteWishList(product);
		deleteRecentlyView(product);
		proDAO.deleteById(key);
		return ResponseEntity.ok().build();
	}

	/*
	 * Xóa ảnh sản phẩm
	 */
	private void deleteImagesProduct(Product product) {
		List<Image> images = product.getProductImage();
		if (images != null) {
			for (Image image : images) {
				imageDAO.deleteById(image.getId());
			}
		}
	}

	/*
	 * Xóa chi tiết sản phẩm
	 */
	private void deleteProductDetail(Product product) {
		List<ProductDetail> productDetails = product.getProductDetail();
		if (productDetails != null) {
			for (ProductDetail productDetail : productDetails) {
				List<Price> prices = productDetail.getProductPrice();
				if (prices != null) {
					for (Price price : prices) {
						priceDAO.deleteById(price.getId());
					}
				}
				List<ProductCart> productCarts = productDetail.getProductCarts();
				if (productCarts != null) {
					for (ProductCart productCart : productCarts) {
						productCartDAO.deleteById(productCart.getId());
					}
				}
				pdDAO.deleteById(productDetail.getId());
			}
		}
	}

	/*
	 * Xóa mô tả sản phẩm
	 */
	private void deleteProductDescription(Product product) {
		List<DetailDescription> descriptions = product.getProductDescriptions();
		if (descriptions != null) {
			for (DetailDescription detailDescription : descriptions) {
				detailDescriptionDAO.deleteById(detailDescription.getId());
			}
		}
	}

	/*
	 * Xóa bình luận sản phẩm
	 */
	private void deleteComment(Product product) {
		List<Comment> comments = product.getProductComment();
		if (comments != null) {
			for (Comment comment : comments) {
				List<ImageComment> imageComments = comment.getCommentImages();
				for (ImageComment imageComment : imageComments) {
					imageCommentDAO.deleteById(imageComment.getId());
				}
				commentDAO.deleteById(comment.getId());
			}
		}
	}

	/*
	 * Xóa sản phẩm khuyến mãi
	 */
	private void deletePromotionDetail(Product product) {
		List<PromotionalDetails> promotionalDetails = product.getProductPromotionalDetails();
		if (promotionalDetails != null) {
			for (PromotionalDetails promotionalDetail : promotionalDetails) {
				promDao.deleteById(promotionalDetail.getId());
			}
		}
	}

	/*
	 * Xóa sản phẩm trong hóa đơn
	 */
	private void deleteOrderDetail(Product product) {
		List<OrderDetail> orderDetails = product.getProductOrderDetail();
		if (orderDetails != null) {
			for (OrderDetail orderDetail : orderDetails) {
				orderDetailDAO.deleteById(orderDetail.getId());
			}
		}
	}

	/*
	 * Xóa sản phẩm trong danh sách yêu thích
	 */
	private void deleteWishList(Product product) {
		List<WishList> wishLists = product.getProductWishList();
		if (wishLists != null) {
			for (WishList wishList : wishLists) {
				wishListDAO.deleteById(wishList.getId());
			}
		}
	}

	/*
	 * Xóa sản phẩm xem gần đây
	 */
	private void deleteRecentlyView(Product product) {
		List<RecentlyViewed> recentlyVieweds = product.getProductRecentlyViewed();
		if (recentlyVieweds != null) {
			for (RecentlyViewed recentlyViewed : recentlyVieweds) {
				recentlyViewedDAO.deleteById(recentlyViewed.getId());
			}
		}
	}

	/*
	 * lấy danh sách sản phẩm tìm kiếm
	 */
	@GetMapping("/search?categoryId=?&manufacturer=?")
	public ResponseEntity<List<ProductDetail>> getProductDetails() {
		List<ProductDetail> productDetails = pdDAO.findAll();
		return ResponseEntity.ok(productDetails);
	}

	/*
	 * Lấy sản phẩm theo mã sản phẩm
	 */
	// @GetMapping("/product/detail/{id}")
	// public ResponseEntity<ProductDTO> getProductDetail(@PathVariable("id") String
	// key) {
	// if (!proDAO.existsById(key)) {
	// return ResponseEntity.notFound().build();
	// }
	// Product product = proDAO.findById(key).get();
	// ProductDTO productDTO = ProductMapper.convertToDto(product);
	//
	// return ResponseEntity.ok(productDTO);
	// }

}
