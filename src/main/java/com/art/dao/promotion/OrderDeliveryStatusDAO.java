package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.art.models.product.Price;
import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;

public interface OrderDeliveryStatusDAO extends JpaRepository<OrderDelieveryStatus, Integer> {
    List<OrderDelieveryStatus> findByOrderStatus(Order orderStatus);
    
    @Query("SELECT p FROM OrderDelieveryStatus p WHERE p.orderStatus.id =:r and p.status = true ORDER BY (p.date) DESC")
	List<OrderDelieveryStatus> sortByDate(int r);
}
