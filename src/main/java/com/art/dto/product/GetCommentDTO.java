package com.art.dto.product;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetCommentDTO {
    private int star;
    private String content;
    private Date date = new Date();
    private String orderDetailId;
    private String productId;
    private String userComment;
    // private List<MultipartFile> commentImages;

}
