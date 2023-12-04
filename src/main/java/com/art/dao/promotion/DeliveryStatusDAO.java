package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.DeliveryStatus;
import com.art.models.promotion.FlashSale;

public interface DeliveryStatusDAO extends JpaRepository<DeliveryStatus, Integer> {

}
