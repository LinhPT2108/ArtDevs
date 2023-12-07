package com.art.dao.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.art.models.product.Category;
import com.art.models.product.Manufacturer;
import com.art.models.product.Product;

public interface ProductDAO extends JpaRepository<Product, String> {

	// // Thêm Product
	Optional<Product> findById(String product_id);

	//
	////
	//// // Tìm tất cả Product
	// Page<Product> findAll(Pageable pageable);
	//
	////
	//// // Tìm Product theo tên
	// List<Product> findByProductName(String productName);
	//
	// @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword%")
	// List<Product> searchProductByName(String keyword);
	//
	// @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% AND
	// p.categoryProduct = :category")
	// List<Product> searchProductByNameAndCategory(String keyword, Category
	// category);
	//
	@Query("SELECT AVG(c.star) FROM Comment c WHERE c.productDetail.product.id = :productId")
	Double calculateAverageRating(String productId);

	@Query("SELECT count(o.id) FROM OrderDetail o WHERE o.productDetail.product.id = :productId")
	int countProuctSold(String productId);

	//
	@Query("SELECT p FROM Product p WHERE p.categoryProduct.categoryId = :categoryId")
	List<Product> findProductByCategoryId(int categoryId);

	@Query("SELECT k.keywords FROM KeywordSuggestions k WHERE k.user.email = :email")
	List<String> findKeywordByUser(String email);

	@Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('N%', :keyword, '%'))")
	List<Product> findByProductNameAlls(@Param("keyword") String keyword);
	// Tìm Product theo số lượng trong kho
	// List<Product> findByQuantityInStock(int quantityInStock);
	////
	// Tìm Product theo trạng thái is_del
	//
	 @Query("SELECT COUNT(c) FROM Comment c WHERE c.productDetail.product.id = :productId")
	 int countCommentsByProduct(String productId);
	//
	// @Query("SELECT COUNT(i) FROM InvoiceDetail i WHERE i.product.id =
	// :productId")
	// Long countTotalProducts(String productId);

	//
	// // Tìm Product theo giá
	// List<Product> findByPrice(BigDecimal price);
	//
	// // Tìm Product theo ID của UserCustom
	// List<Product> findByUser_UserId(int userId);
	//
	Page<Product> findByCategoryProduct(Category categoryProduct, Pageable pageable);
	// // Tìm Product theo ID của Category
	List<Product> findByCategoryProduct(Category categoryProduct);

	List<Product> findByCategoryProductAndManufacturerProduct(Category category,
			Manufacturer manufacturer);

	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword%")
	List<Product> searchProductByName(String keyword);

	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% AND p.categoryProduct = :category AND p.manufacturerProduct = :manufacturer")
	List<Product> searchProductByNameAndCategoryAndManufacturer(String keyword, Category category,
			Manufacturer manufacturer);

	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% AND p.categoryProduct = :category")
	List<Product> searchProductByNameAndCategory(String keyword, Category category);

	// // Tìm Product theo ID của Manufacturer
	List<Product> findByManufacturerProduct(Manufacturer manufacturerProduct);

	List<Product> findByAvailable(boolean b);
}
