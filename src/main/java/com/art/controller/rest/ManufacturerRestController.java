package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ManufacturerDAO;
import com.art.models.product.Manufacturer;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class manufacturerRestController {
    @Autowired
    ManufacturerDAO maDAO;
 
    @GetMapping(value="/manufacturer")
    public ResponseEntity<List<Manufacturer>> getMethodName() {
        List<Manufacturer> manufacturers = maDAO.findByDel(false);
        return ResponseEntity.ok(manufacturers);
    }
    
}
