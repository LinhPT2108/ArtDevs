package com.art.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.activity.CartDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.models.activity.Cart;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.PromotionalDetails;
import com.art.models.user.Account;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
	@Autowired
	HttpSession session;
	@Autowired
	FlashSaleDAO fsDAO;
	@Autowired
	PromotionalDetailsDAO pmtDAO;
	@Autowired
	CartDAO cartDAO;

	/**
	 * Đọc giá trị của attribute trong session
	 * 
	 * @param name tên attribute
	 * @return giá trị đọc được hoặc null nếu không tồn tại
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) session.getAttribute(name);
	}

	/**
	 * Thay đổi hoặc tạo mới attribute trong session
	 * 
	 * @param name  tên attribute
	 * @param value giá trị attribute
	 */
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}

	/**
	 * Xóa attribute trong session
	 * 
	 * @param name tên attribute cần xóa
	 */
	public void remove(String name) {
		session.removeAttribute(name);
	}

	public void setCart(List<Cart> cart) {

		FlashSale isFlashSale = fsDAO.findByStatus(false);

		if (isFlashSale != null) {
			List<PromotionalDetails> pmt = pmtDAO.findByFlashSale_Id(isFlashSale.getId());
			session.setAttribute("ssPdFlashSale", pmt);
			session.setAttribute("isFlashSale", true);
		} else {
			session.setAttribute("isFlashSale", false);
		}
		session.setAttribute("carts", cart);

		Double totalPrice = totalPriceCartByUserId(get("userLogin"));
		session.setAttribute("totalPriceInCart", totalPrice);
	}
	// cần update lại dòng 91, 96, 82
	public double totalPriceCartByUserId(Account userId) {
		
		FlashSale isFlashSale = fsDAO.findByStatus(false);

		List<Cart> cartList = cartDAO.findByUser(userId);

		double totalPrice = 0;
		if (isFlashSale != null) {
			List<PromotionalDetails> pmt = pmtDAO.findByFlashSale_Id(isFlashSale.getId());

			for (Cart cart : cartList) {
				Boolean isSale = false;
				for (PromotionalDetails p : pmt) {
					if (cart.getProductDetail().get(0).getProduct().getProductId() == p.getProduct().getProductId()) {
						isSale = true;
					}
				}
				if (isSale) {
					totalPrice += cart.getProductDetail().get(0).getProduct().getProductPromotionalDetails().get(0).getDiscountedPrice()
							* cart.getQuantity();
				} else { 
					//lấy lại theo giá đang bán
					BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(cart.getPrice())); 
					int intValue = cart.getQuantity(); 

					BigDecimal result = bigDecimalValue.multiply(new BigDecimal(intValue));
					totalPrice += Double.parseDouble(String.valueOf(result));
				}
			}
		} else {
			for (Cart cart : cartList) {
				//lấy lại theo giá đang bán
				BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(cart.getPrice())); 
				int intValue = cart.getQuantity(); 

				BigDecimal result = bigDecimalValue.multiply(new BigDecimal(intValue));
				totalPrice += Double.parseDouble(String.valueOf(result));
			}
		}
		return totalPrice;
	}
}
