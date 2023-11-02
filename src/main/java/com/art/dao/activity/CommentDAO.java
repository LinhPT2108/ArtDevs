package com.art.dao.activity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.activity.Comment;
import com.art.models.product.Product;
import com.art.models.promotion.InvoiceDetail;


public interface CommentDAO extends JpaRepository<Comment, Integer> {
	
//	// Thêm hoặc cập nhật một bình luận
//	@SuppressWarnings("unchecked")
//	Comment save(Comment comment);
//	
//	// Xóa một bình luận theo ID
//	void deleteById(int commentId);
//	
	// Tìm bình luận theo ID
	Optional<Comment> findById(int commentId);
	
	Optional<Comment> findByInvoiceDetailAndProduct(InvoiceDetail invoiceDetail, Product product);
//	
//	// Lấy tất cả các bình luận của một người dùng
//	List<Comment> findByUser(UserCustom user);
//	
//	// Lấy tất cả các bình luận của một sản phẩm
//	List<Comment> findByProduct(Product product);
	@Query("SELECT c FROM Comment c WHERE c.product.id = :productId")
	List<Comment> findByProductId(String productId);
	
	
	
}
