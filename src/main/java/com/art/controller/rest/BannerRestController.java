package com.art.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.art.dao.activity.BannerDAO;
import com.art.models.activity.Banner;
import com.art.utils.Path;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Path.BASE_PATH)
public class BannerRestController {
    @Autowired
    BannerDAO bannerDAO;
    @GetMapping(value="/banner")
    public ResponseEntity<List<Banner>> getMethodName() {
        List<Banner> banners = bannerDAO.findAll();
        return ResponseEntity.ok(banners);
    }
    
}
