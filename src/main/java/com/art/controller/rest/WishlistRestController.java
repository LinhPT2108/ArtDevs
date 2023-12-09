package com.art.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.WishListDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.user.AccountDAO;
import com.art.dto.product.ProductDTO;
import com.art.mapper.ProductMapper;
import com.art.models.activity.WishList;
import com.art.models.product.Product;
import com.art.models.user.Account;
import com.art.utils.Path;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class WishlistRestController {
    @Autowired
    WishListDAO wishListDAO;

    @Autowired
    ProductDAO pdDAO;
    @Autowired
    AccountDAO aDAO;

    @Autowired
    FlashSaleDAO fDAO;

    @Autowired
    PromotionalDetailsDAO promDao;

    @GetMapping("/wishlist/{accountId}")
    public ResponseEntity<?> getWishlistByAccount(@PathVariable("accountId") String accountId) {
        Account account = aDAO.findById(accountId).get();
        List<WishList> wishLists = wishListDAO.findByUser(account);
        return ResponseEntity.ok(wishLists);
    }

    @GetMapping("/wishlist-site/{accountId}")
    public ResponseEntity<?> getWishlistByAccountSite(@PathVariable("accountId") String accountId) {
        Account account = aDAO.findById(accountId).get();
        List<WishList> wishLists = wishListDAO.findByUser(account);
        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
        for (WishList p : wishLists) {
            productDTOs.add(ProductMapper.convertToDto(p.getProduct(), promDao, fDAO, pdDAO));
        }
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/wishlist/{productId}/{accountId}")
    public ResponseEntity<?> addWishlist(@PathVariable("productId") String productId,
            @PathVariable("accountId") String accountId) {
        System.out.println(productId + " - " + accountId);
        try {
            Product product = pdDAO.findById(productId).get();
            Account account = aDAO.findById(accountId).get();
            WishList wishList = new WishList();
            wishList.setWishlistDate(new Date());
            wishList.setProduct(product);
            wishList.setUser(account);
            wishListDAO.save(wishList);
            return ResponseEntity.ok(wishList);
        } catch (Exception e) {
            return ResponseEntity.ok(500);
        }
    }

    @DeleteMapping("/wishlist/{productId}/{accountId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable("productId") String productId,
            @PathVariable("accountId") String accountId) {
        try {
            Product product = pdDAO.findById(productId).get();
            Account account = aDAO.findById(accountId).get();
            wishListDAO.deleteByUserAndProduct(account, product);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
