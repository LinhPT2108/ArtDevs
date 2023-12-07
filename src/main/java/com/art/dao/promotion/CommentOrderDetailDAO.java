package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.activity.Comment;
import java.util.List;
import com.art.models.promotion.OrderDetail;


public interface CommentOrderDetailDAO extends JpaRepository<Comment, Integer> {
	List<Comment> findByOrderDetail(OrderDetail orderDetail);
}
