package com.art.controller.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.CartDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.DeliveryStatusDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.OrderDAO;
import com.art.dao.promotion.OrderDeliveryStatusDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dao.promotion.paymentDAO;
import com.art.dao.user.AccountDAO;
import com.art.dto.account.CartDTO;
import com.art.dto.product.orderDTO;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.FlashSale;
import com.art.models.promotion.Order;
import com.art.models.promotion.OrderDelieveryStatus;
import com.art.models.promotion.OrderDetail;
import com.art.models.promotion.PromotionalDetails;
import com.art.models.user.Account;
import com.art.models.user.InforAddress;
import com.art.utils.Path;
import com.art.utils.validUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Autowired
    DeliveryStatusDAO deliveryStatusDAO;

    @Autowired
    OrderDeliveryStatusDAO orderDeliStatusDAO;

    @Autowired
    paymentDAO paymentDAO;

    @Autowired
    FlashSaleDAO fDAO;

    @Autowired
    PromotionalDetailsDAO promDao;

    @PostMapping("/order")
    public ResponseEntity<?> postMethodName(@RequestBody orderDTO orderDTO) {
        System.out.println(orderDTO.getUserId() + " - " + orderDTO.getTotalAmount() + " - "
                + orderDTO.getDeliveryAddress().getPhoneNumber() + " - " + orderDTO.getNote()
                + " - " + orderDTO.getExpectedDeliveryTime() + " - " + orderDTO.getPaymentMethod());
        try {
            Account account = accountDAO.findById(orderDTO.getUserId()).get();
            InforAddress infAddress = orderDTO.getDeliveryAddress();
            Order order = new Order();
            order.setNote(orderDTO.getNote());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setDeliveryFee(orderDTO.getDeliveryFee());
            order.setDiscount(orderDTO.getDiscount());
            order.setOrderDate(new Date());
            order.setExpected_delivery_time(orderDTO.getExpectedDeliveryTime());
            order.setUser(account);
            String deliveryAddress = "SĐT: " + infAddress.getPhoneNumber() + "; ĐC: " + infAddress.getCity()
                    + " - " + infAddress.getDistrict() + " - " + infAddress.getWard()
                    + " - " + infAddress.getSpecific();
            order.setNickname(infAddress.getNickname());
            order.setDeliveryAddress(deliveryAddress);
            order.setPaymentMethod(paymentDAO.findById(orderDTO.getPaymentMethod()).get());
            System.out.println(deliveryAddress);
            Order saveOrder = orderDAO.save(order);

            OrderDelieveryStatus orderDeliStatus = new OrderDelieveryStatus();
            orderDeliStatus.setDate(new Date());
            orderDeliStatus.setStatus(true);
            orderDeliStatus.setOrderStatus(saveOrder);
            orderDeliStatus.setDeliveryStatus(deliveryStatusDAO.findById(1).get());
            orderDeliStatusDAO.save(orderDeliStatus);

            List<CartDTO> cartDTOs = orderDTO.getCartDTO();

            for (CartDTO c : cartDTOs) {
                OrderDetail orderDetail = new OrderDetail();
                ProductDetail pdt = productDetailDAO.findById(c.getProductDetailId()).get();
                orderDetail.setQuantity(c.getQuantityInCart());
                orderDetail.setPrice(validUtil.getPriceProduct(c.getProductDTO(), c.getProductDetailId(), fDAO, promDao,
                        productDetailDAO));
                orderDetail.setProductDetail(pdt);
                orderDetail.setOrder(saveOrder);
                orderDetailDAO.save(orderDetail);

                pdt.setQuantityInStock(pdt.getQuantityInStock() - c.getQuantityInCart());
                productDetailDAO.save(pdt);
                if (c.getProductDTO().isSale()) {
                    FlashSale flActive = fDAO.findByStatus(true);
                    List<PromotionalDetails> promotionalDetails = flActive.getPromotionalDetailsList();
                    for (PromotionalDetails pm : promotionalDetails) {
                        if (pm.getProduct().getProductId().equals(c.getProductDTO().getProductId())) {
                            pm.setQuantitySold(pm.getQuantitySold() + c.getQuantityInCart());
                            promDao.save(pm);
                        }
                    }

                }

                cartDAO.deleteByUserAndProductDetail(account, pdt);
            }
            return ResponseEntity.ok(200);

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(400);
        }
    }

    @PutMapping("/order/cancel-order/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable("id") int id) {
        try {
            Order order = orderDAO.findById(id);
            List<OrderDelieveryStatus> ods = orderDeliStatusDAO.findByOrderStatus(order);
            for (OrderDelieveryStatus e : ods) {
                e.setStatus(false);
                orderDeliStatusDAO.save(e);
            }
            OrderDelieveryStatus newStatus = new OrderDelieveryStatus();
            newStatus.setDeliveryStatus(deliveryStatusDAO.findById(4).get());
            newStatus.setDate(new Date());
            newStatus.setOrderStatus(order);
            newStatus.setStatus(true);
            orderDeliStatusDAO.save(newStatus);

            return ResponseEntity.ok(200);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(400);
        }
    }
}
