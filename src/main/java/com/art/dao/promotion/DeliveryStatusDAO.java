package com.art.dao.promotion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.art.models.promotion.DeliveryStatus;

public interface DeliveryStatusDAO extends JpaRepository<DeliveryStatus, Integer> {

}
