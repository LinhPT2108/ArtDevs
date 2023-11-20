package com.art.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.CategoryDAO;
import com.art.models.product.Category;
import com.art.utils.Path;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class categoryRestController {
    @Autowired
    CategoryDAO caDAO;
 
    @GetMapping(value="/category")
    public ResponseEntity<List<Category>> getMethodName() {
        List<Category> listCategories = caDAO.findByStatus(true);
        return ResponseEntity.ok(listCategories);
    }
    
}
