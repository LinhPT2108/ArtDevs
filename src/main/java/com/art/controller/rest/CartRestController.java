package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.CartDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.user.AccountDAO;
import com.art.dto.account.CartDTO;
import com.art.mapper.CartMapper;
import com.art.models.activity.Cart;
import com.art.utils.Path;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class CartRestController {
    @Autowired
    CartDAO cartDAO;
    @Autowired
    ProductDetailDAO pdtDAO;
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    FlashSaleDAO flashSaleDAO;
    @Autowired
    PromotionalDetailsDAO proDAO;

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> delCart(@PathVariable("id") int id) {
        if (!cartDAO.existsById(id)) {
            return ResponseEntity.ok(404);
        } else {
            cartDAO.delete(cartDAO.findById(id).get());
            return ResponseEntity.ok(200);
        }
    }

    @PostMapping("/cart/{userId}")
    public ResponseEntity<?> postMethodName(@RequestBody CartDTO cartDTO,
            @PathVariable("userId") String userId) {
        try {
            System.out.println(cartDTO.getProductDetailId());
            List<Cart> cartUser = cartDAO.findByUserAndProductDetail(
                    accountDAO.findById(userId).get(),
                    pdtDAO.findById(cartDTO.getProductDetailId()).get());
            if (cartUser.size() == 1) {
                Cart cart = cartUser.get(0);
                cart.setQuantity(cart.getQuantity() + cartDTO.getQuantityInCart());
                Cart saveCart = cartDAO.save(cart);
                return ResponseEntity.ok(CartMapper.convertToCartDTO(saveCart, proDAO, flashSaleDAO, productDAO));
            } else {
                Cart cart = CartMapper.convertToCart(cartDTO);
                cart.setProductDetail(pdtDAO.findById(cartDTO.getProductDetailId()).get());
                cart.setUser(accountDAO.findById(userId).get());
                Cart saveCart = cartDAO.save(cart);
                return ResponseEntity.ok(CartMapper.convertToCartDTO(saveCart, proDAO, flashSaleDAO, productDAO));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(400);
        }
    }

    @PutMapping("/update-cart/{userId}")
    public ResponseEntity<?> putCartUser(@RequestBody CartDTO cartDTO,
            @PathVariable("userId") String userId) {
        try {
            List<Cart> saveCart = cartDAO.findByUserAndProductDetail(accountDAO.findById(userId).get(),
                    pdtDAO.findById(cartDTO.getProductDetailId()).get());
            Cart svCart = saveCart.get(0);
            svCart.setQuantity(cartDTO.getQuantityInCart());
            cartDAO.save(svCart);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().build();
    }

}
