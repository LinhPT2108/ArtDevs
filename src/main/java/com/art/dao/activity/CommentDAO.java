package com.art.dao.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.activity.Comment;


public interface CommentDAO extends JpaRepository<Comment, Integer> {
	
//	// Thêm hoặc cập nhật một bình luận
//	@SuppressWarnings("unchecked")
//	Comment save(Comment comment);
//	
//	// Xóa một bình luận theo ID
//	void deleteById(int commentId);
//	
	// Tìm bình luận theo ID
//	Optional<Comment> findById(int commentId);
//	
//	Optional<Comment> findByInvoiceDetailAndProduct(OrderDetail invoiceDetail, Product product);
//	
//	// Lấy tất cả các bình luận của một người dùng
//	List<Comment> findByUser(UserCustom user);
//	
//	// Lấy tất cả các bình luận của một sản phẩm
//	List<Comment> findByProduct(Product product);
//	@Query("SELECT c FROM Comment c WHERE c.product.id = :productId")
//	List<Comment> findByProductId(String productId);
	
	
	
}
