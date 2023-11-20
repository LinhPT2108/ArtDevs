package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ManufacturerDAO;
import com.art.models.product.Manufacturer;

import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/rest")
public class manufacturerRestController {
    @Autowired
    ManufacturerDAO mnDAO;
    @GetMapping(value="/manufacturer")
    public ResponseEntity<List<Manufacturer>> getMethodName() {
        List<Manufacturer> listManufacturers = mnDAO.findByDel(false);
        return ResponseEntity.ok(listManufacturers);
    }
    
}
