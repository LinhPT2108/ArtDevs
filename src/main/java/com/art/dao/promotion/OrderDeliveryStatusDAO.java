package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;

public interface OrderDeliveryStatusDAO extends JpaRepository<OrderDelieveryStatus, Integer> {
    List<OrderDelieveryStatus> findByOrderStatus(Order orderStatus);
}
