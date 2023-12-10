package com.art.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.art.dao.activity.CommentDAO;
import com.art.dao.activity.ImageCommentDAO;
import com.art.dao.product.ProductDetailDAO;
import com.art.dao.promotion.OrderDetailDAO;
import com.art.dao.user.AccountDAO;
import com.art.dto.product.GetCommentDTO;
import com.art.models.activity.Comment;
import com.art.models.activity.ImageComment;
import com.art.models.product.ProductDetail;
import com.art.models.promotion.OrderDetail;
import com.art.models.user.Account;
import com.art.service.ParamService;
import com.art.utils.Path;

import jakarta.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@MultipartConfig(maxFileSize = 9999999, maxRequestSize = 99999999)
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

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ProductDetailDAO productDetailDAO;

    @Autowired
    ParamService paramService;

    @Autowired
    ImageCommentDAO imgCmtDAO;

    @GetMapping("/comment/{productId}/{orderDetailId}")
    public ResponseEntity<Comment> getMethodName(@PathVariable("productId") int productId,
            @PathVariable("orderDetailId") int orderDetailId) {
        System.out.println(productId + " - " + orderDetailId);
        ProductDetail pdt = pdtDAO.findById(productId).get();
        OrderDetail orderDetail = orderDetailDAO.findById(orderDetailId).get();
        Optional<Comment> comment = commentDAO.findByOrderDetailAndProductDetail(orderDetail, pdt);

        return ResponseEntity.ok(comment.isEmpty() ? null : comment.get());
    }

    @PostMapping(value = "/add-comment")
    public ResponseEntity<?> postMethodName(@RequestBody GetCommentDTO cmtDTO) {
        System.out.println(cmtDTO.toString());
        try {
            OrderDetail orderDetail = orderDetailDAO.findById(Integer.parseInt(cmtDTO.getOrderDetailId())).get();
            Account account = accountDAO.findById(cmtDTO.getUserComment()).get();
            List<Comment> existCmt = commentDAO.findByUserAndOrderDetail(account, orderDetail);
            if (existCmt.isEmpty()) {
                System.out.println("insert");
                ProductDetail productDetail = productDetailDAO.findById(Integer.parseInt(cmtDTO.getProductId())).get();
                Comment comment = new Comment();
                comment.setContent(cmtDTO.getContent());
                comment.setDate(new Date());
                comment.setOrderDetail(orderDetail);
                comment.setUser(account);
                comment.setStar(cmtDTO.getStar());
                comment.setProductDetail(productDetail);
                commentDAO.save(comment);
                return ResponseEntity.ok(comment);
            } else {
                System.out.println("update");
                Comment comment = existCmt.get(0);
                comment.setContent(cmtDTO.getContent());
                comment.setDate(new Date());
                comment.setOrderDetail(orderDetail);
                comment.setUser(account);
                comment.setStar(cmtDTO.getStar());
                commentDAO.save(comment);
                return ResponseEntity.ok(comment);
            }
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/get-image-comment/{accountId}/{orderDetailId}")
    public ResponseEntity<?> GetImgCmt(@RequestBody List<MultipartFile> imgsComment,
            @PathVariable("accountId") String accountId, @PathVariable("orderDetailId") String orderDetailId) {
        if (!imgsComment.isEmpty()) {
            System.out.println(accountId + " - " + orderDetailId);
            try {
                Account account = accountDAO.findById(accountId).get();
                OrderDetail orderDetail = orderDetailDAO.findById(Integer.parseInt(orderDetailId)).get();
                List<Comment> existCmt = commentDAO.findByUserAndOrderDetail(account, orderDetail);
                if (existCmt.isEmpty()) {
                    System.out.println("insert img");
                    List<Comment> comment = commentDAO.findByUserAndOrderDetail(account, orderDetail);
                    for (MultipartFile file : imgsComment) {
                        ImageComment imgCmt = new ImageComment();
                        imgCmt.setComment(comment.get(0));
                        imgCmt.setImage(paramService.saveFile(file, "/comments").getName());
                        imgCmtDAO.save(imgCmt);
                    }
                    return ResponseEntity.ok(comment);
                } else {
                    List<Comment> comment = commentDAO.findByUserAndOrderDetail(account, orderDetail);
                    imgCmtDAO.deleteByComment(comment.get(0));
                    for (MultipartFile file : imgsComment) {
                        ImageComment imgCmt = new ImageComment();
                        imgCmt.setComment(comment.get(0));
                        imgCmt.setImage(paramService.saveFile(file, "/comments").getName());
                        imgCmtDAO.save(imgCmt);
                    }
                    System.out.println("update img");
                    return ResponseEntity.ok(comment);

                }

            } catch (Exception e) {
                return ResponseEntity.ok(null);
            }
        } else {
            return ResponseEntity.ok(null);

        }

    }

}
