package com.art.controller.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.CartDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.user.AccountDAO;
import com.art.dto.account.CartDTO;
import com.art.dto.product.orderDTO;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDetail;
import com.art.models.user.Account;
import com.art.models.user.InforAddress;
import com.art.utils.Path;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class orderRestController {
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    CartDAO cartDAO;
    @Autowired
    ProductDetailDAO productDetailDAO;
    @Autowired
    ProductDAO productDAO;

//    @PostMapping("/order")
//    public ResponseEntity<?> postMethodName(@RequestBody orderDTO orderDTO) {
//        System.out.println(orderDTO.getUserId() + " - " + orderDTO.getTotalAmount() + " - "
//                + orderDTO.getDeliveryAddress().getPhoneNumber() + " - " + orderDTO.getNote());
//        try {
//            Account account = accountDAO.findById(orderDTO.getUserId()).get();
//            InforAddress infAddress = orderDTO.getDeliveryAddress();
//            Order order = new Order();
//            order.setNote(orderDTO.getNote());
//            order.setStatus(1);
//            order.setTotalAmount(orderDTO.getTotalAmount());
//            order.setOrderDate(new Date());
//            order.setUser(account);
//            String deliveryAddress = "SĐT: " + infAddress.getPhoneNumber() + ";ĐC: " + infAddress.getCity()
//                    + " - " + infAddress.getDistrict() + " - " + infAddress.getWard()
//                    + " - " + infAddress.getSpecific();
//            order.setDeliveryAddress(deliveryAddress);
//            System.out.println(deliveryAddress);
//
//            Order saveOrder = orderDAO.save(order);
//            List<CartDTO> cartDTOs = orderDTO.getCartDTO();
//
//            System.out.println(saveOrder.getId());
//
//            for (CartDTO c : cartDTOs) {
//                OrderDetail orderDetail = new OrderDetail();
//                orderDetail.setQuantity(c.getQuantityInCart());
//                orderDetail.setProduct(productDAO.findById(c.getProductDTO().getProductId()).get());
//                cartDAO.deleteByUserAndProductDetail(account,
//                        productDetailDAO.findById(c.getProductDetailId()).get());
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return ResponseEntity.ok().build();
//    }

}
