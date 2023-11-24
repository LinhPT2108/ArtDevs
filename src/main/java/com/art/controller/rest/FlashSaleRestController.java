package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.product.ProductDAO;
import com.art.dao.promotion.FlashSaleDAO;
import com.art.dao.promotion.PromotionalDetailsDAO;
import com.art.dto.promotion.FlashSaleDTO;
import com.art.mapper.FlashSaleMapper;
import com.art.models.promotion.FlashSale;
import com.art.utils.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Path.BASE_PATH)
public class FlashSaleRestController {
    @Autowired
    FlashSaleDAO flashSaleDAO;
    @Autowired
    ProductDAO pdDAO;
    @Autowired
    PromotionalDetailsDAO promDao;
    @Autowired
    FlashSaleDAO fDAO;

    @GetMapping("/flash-sale")
    public ResponseEntity<List<FlashSale>> getFlashSales() {
        List<FlashSale> flashSales = flashSaleDAO.findAll();
        return ResponseEntity.ok(flashSales);
    }

     @GetMapping("/flash-sale-active")
    public ResponseEntity<FlashSaleDTO> getFlashSalesActive() { 
        FlashSale flashSales = flashSaleDAO.findByStatus(true);

        FlashSaleDTO flashSaleDTOs = FlashSaleMapper.convertToDto(flashSales,promDao,fDAO, pdDAO);
            
        return ResponseEntity.ok(flashSaleDTOs);
    }

    @GetMapping("/flash-sale/{id}")
    public ResponseEntity<FlashSale> getFlashSale(@PathVariable("id") int id) {
        FlashSale flashSale = flashSaleDAO.findById(id).get();
        return ResponseEntity.ok(flashSale);
    }

    @PostMapping("/flash-sale")
    public ResponseEntity<FlashSale> createFlashSale(@RequestBody FlashSale flashSale) {
        FlashSale fl = flashSaleDAO.findByStatus(true);
        fl.setStatus(false);
        if (flashSaleDAO.existsById(flashSale.getId())) {
            return ResponseEntity.badRequest().build();
        }
        flashSaleDAO.save(fl);
        flashSaleDAO.save(flashSale);
        return ResponseEntity.ok(flashSale);
    }

    @PutMapping("/flash-sale/{id}")
    public ResponseEntity<FlashSale> putFlashSale(@PathVariable("id") int id, @RequestBody FlashSale flashSale) {
        FlashSale fl = flashSaleDAO.findById(id).get();
        boolean isCheck = flashSale.isStatus();
        if (!isCheck) {
            if (flashSaleDAO.existsById(flashSale.getId())) {
                return ResponseEntity.badRequest().build();
            }
            flashSaleDAO.save(flashSale);
        }
        fl.setStatus(false);
        if (flashSaleDAO.existsById(flashSale.getId())) {
            return ResponseEntity.badRequest().build();
        }
        flashSaleDAO.save(flashSale);
        return ResponseEntity.ok(flashSale);
    }

}
