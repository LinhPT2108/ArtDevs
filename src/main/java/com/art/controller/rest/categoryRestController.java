package com.art.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.CategoryDAO;
import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.product.ProductDTO;
import com.art.mapper.ProductMapper;
import com.art.models.product.Category;
import com.art.models.product.Product;
import com.art.utils.Path;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class categoryRestController {
    @Autowired
    CategoryDAO caDAO;
    @Autowired
    ProductDAO pdDAO;
    @Autowired
    PromotionalDetailsDAO promDao;
    @Autowired
    FlashSaleDAO fDAO;

    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getMethodName() {
        List<Category> listCategories = caDAO.findByStatus(true);
        return ResponseEntity.ok(listCategories);
    }

    @GetMapping(value = "/product-by-category/{id}") 
    public ResponseEntity<List<ProductDTO>> getMethodName(@PathVariable("id") int id) {
        System.out.println(id);
        List<Product> products = pdDAO.findProductByCategoryId(id);

        Collections.shuffle(products);

        List<Product> randomProducts = products.stream().limit(8).collect(Collectors.toList());

        List<ProductDTO> productDTOs = randomProducts.stream().limit(8)
                .map(product -> ProductMapper.convertToDto(product, promDao, fDAO))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

}
