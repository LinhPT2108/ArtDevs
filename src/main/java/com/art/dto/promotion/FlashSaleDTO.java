package com.art.dto.promotion;

import java.util.Date;
import java.util.List;

import com.art.models.promotion.PromotionalDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleDTO {
    private int id;
    private Date startDay;
    private Date endDay;
    private Boolean status;
    private List<PromotionalDetailsListDTO> promotionalDetailsList;
}
