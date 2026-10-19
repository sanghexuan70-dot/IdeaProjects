package com.learn.hello_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "bill-api")
public interface BillClient {

    @GetMapping("/api/bills")
        // ← bill-api 的真实路径
    List<Bill> bills(@RequestParam("userId") Long userId);

    record Bill(Long id, Long userId, String title, BigDecimal amount) {
    }
}