package com.art.mapper;

import org.modelmapper.ModelMapper;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.account.CartDTO;
import com.art.models.activity.Cart;

public class CartMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CartDTO convertToCartDTO(Cart cart, PromotionalDetailsDAO proDAO, FlashSaleDAO fDAO,
			ProductDAO productDAO) { 
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProductDTO(ProductMapper.convertToDto(cart.getProductDetail().getProduct(),proDAO,fDAO,productDAO));
        cartDTO.setQuantityInCart(cart.getQuantity());
        return cartDTO;
    }

    public static Cart convertToCart(CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        return cart;
    }
}
