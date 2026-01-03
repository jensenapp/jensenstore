package com.eazybytes.jensenstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products") // 定義共用路徑
public class ProductController {

    @GetMapping
    public String getProducts() {
        // 目前僅回傳測試用字串，後續才會連接資料庫
        return "Here are your products";
    }
}