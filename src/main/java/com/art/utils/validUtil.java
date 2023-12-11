package com.art.utils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.product.ProductDTO;
import com.art.dto.product.ProductDetailDTO;
import com.art.models.product.Price;
import com.art.models.product.Product;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.PromotionalDetails;

public class validUtil {

    public static boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }

    public static boolean containsSpecialCharacters(String str) {
        return str.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    public static Long getPriceProduct(ProductDTO productDTO, int productDetailId, FlashSaleDAO flDAO,
            PromotionalDetailsDAO proDAOm,
            ProductDetailDAO pdtDAO) {
        FlashSale fl = flDAO.findByStatus(true);
        List<Price> prices = pdtDAO.findById(productDetailId).get().getProductPrice();
        prices.sort((o1, o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()));
        if (fl != null) {
            Double latestPrice = (double) prices.get(0).getPrice();
            if (fl.getEndDay().after(new Date())) {
                if (productDTO.isSale()) {
                    Double priceCurrent = latestPrice * (1 - productDTO.getDiscountPrice());
                    return priceCurrent.longValue();
                } else {
                    return latestPrice.longValue();
                }
            } else {
                return latestPrice.longValue();
            }
        } else {
            Double latestPrice = (double) prices.get(0).getPrice();
            return latestPrice.longValue();
        }
    }
}
