package com.art.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.CategoryDAO;
import com.art.dao.product.ProductDAO;
import com.art.models.product.Category;

import java.util.List;

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

    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getMethodName() {
        List<Category> listCategories = caDAO.findByStatus(true);
        return ResponseEntity.ok(listCategories);
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<List<Product>> getMethodName(@PathVariable("id") int id) {
        List<Product> products = pdDAO.findProductByCategoryId(id);
        System.out.println(id);
        return ResponseEntity.ok(products);
    }

}
