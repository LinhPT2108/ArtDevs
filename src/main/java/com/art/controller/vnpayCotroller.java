package com.art.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.art.config.vnpay.VNPayService;
import com.art.dto.vnpay.vnpayDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*")
public class vnpayCotroller {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestBody vnpayDTO vnpaydto,
            HttpServletRequest request) {
                System.out.println(vnpaydto.getAmount());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
        + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(vnpaydto.getAmount(),
        vnpaydto.getOrderInfo(), baseUrl);
        return "redirect:" + vnpayUrl;  
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

}
