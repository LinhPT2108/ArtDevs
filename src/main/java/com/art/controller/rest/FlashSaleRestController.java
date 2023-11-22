package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.promotion.FlashSaleDAO;
import com.art.models.promotion.FlashSale;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class FlashSaleRestController {
    @Autowired
    FlashSaleDAO flashSaleDAO;
 
    @GetMapping("/flash-sale")
    public ResponseEntity<List<FlashSale>> getFlashSales() {
        List<FlashSale> flashSales = flashSaleDAO.findAll();
        return ResponseEntity.ok(flashSales);
    }
    
    @GetMapping("/flash-sale/{id}")
    public ResponseEntity<FlashSale> getFlashSale(@PathVariable("id") int id) {
        FlashSale flashSale = flashSaleDAO.findById(id).get();
        return ResponseEntity.ok(flashSale);
    }
    
    @PostMapping("/flash-sale")
    public ResponseEntity<FlashSale> createFlashSale(@RequestBody FlashSale flashSale){
    	if(flashSaleDAO.existsById(flashSale.getId())) {
    		return ResponseEntity.badRequest().build();
    	}
    	flashSaleDAO.save(flashSale);
    	return ResponseEntity.ok(flashSale);
    }
    
}
