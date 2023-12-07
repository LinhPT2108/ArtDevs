package com.art.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ProductDetailDAO;
import com.art.dto.product.ProductDetailDTO;
import com.art.mapper.ProductDetailsMapper;
import com.art.models.product.ProductDetail;
import com.art.utils.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class ProductDetailRestController {
    @Autowired
    ProductDetailDAO pdtDAO;

    @GetMapping(value = "/product-detail/{id}")
    public ResponseEntity<ProductDetailDTO> getMethodName(@PathVariable("id") int id) {
        if (!pdtDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ProductDetail productDetail = pdtDAO.findById(id).get();
        ProductDetailDTO productDetailDTO = ProductDetailsMapper.convertToDto(productDetail);

        return ResponseEntity.ok(productDetailDTO);
    }
}
