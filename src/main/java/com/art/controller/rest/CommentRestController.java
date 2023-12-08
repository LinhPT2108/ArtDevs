package com.art.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.CommentDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.models.activity.Comment;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.OrderDetail;
import com.art.utils.Path;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class CommentRestController {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    ProductDetailDAO pdtDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @GetMapping("/comment/{productId}/{orderDetailId}")
    public ResponseEntity<Comment> getMethodName(@PathVariable("productId") int productId,
            @PathVariable("orderDetailId") int orderDetailId) {
        System.out.println(productId + " - " + orderDetailId);
        ProductDetail pdt = pdtDAO.findById(productId).get();
        OrderDetail orderDetail = orderDetailDAO.findById(orderDetailId).get();
        Optional<Comment> comment = commentDAO.findByOrderDetailAndProductDetail(orderDetail, pdt);

        return ResponseEntity.ok(comment.isEmpty()?null:comment.get());
    }

    @PostMapping("/add-comment")
    public ResponseEntity<?> postMethodName(@RequestBody Comment comment) {
         
        return ResponseEntity.ok(comment);
    }
    

}
