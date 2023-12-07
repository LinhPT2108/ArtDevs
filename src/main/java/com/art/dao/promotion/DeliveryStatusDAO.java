package com.art.dao.promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.Order;

public interface DeliveryStatusDAO extends JpaRepository<DeliveryStatus, Integer> {
	List<DeliveryStatus> findByOrderStatus(Order orderStatus);
}
