package com.art.config;

import java.util.TimeZone;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class timezoneConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Đặt múi giờ mặc định
        System.out.println(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
}
