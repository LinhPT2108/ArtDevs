package com.art.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.art.config.vnpay.VNPayService;
import com.art.dto.vnpay.vnpayDTO;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*")
public class vnpayCotroller {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public ResponseEntity<?> submidOrder(@RequestBody vnpayDTO vnpaydto,
            HttpServletRequest request) {
        System.out.println(vnpaydto.getAmount());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(vnpaydto.getAmount(),
                vnpaydto.getOrderInfo(), baseUrl);
        Map<String, String> url = new HashMap<>();
        url.put("urlVnpay", vnpayUrl);
        return ResponseEntity.ok(url);
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
        System.out.println(orderInfo);
        return paymentStatus == 1 ? "redirect:/payment/true/" + orderInfo : "redirect:/payment/false/" + orderInfo;
    }

    @GetMapping("/payment/{status}/{orderId}")
    public String getPayment(@PathVariable("status") String status,
            @PathVariable("orderId") String orderId) {
        System.out.println(status + " - " + orderId);
        return "index";
    }

}
