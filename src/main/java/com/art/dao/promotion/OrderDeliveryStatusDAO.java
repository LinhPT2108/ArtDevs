package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import com.art.models.promotion.OrderDelieveryStatus;

public interface OrderDeliveryStatusDAO extends JpaRepository<OrderDelieveryStatus, Integer> {

}
