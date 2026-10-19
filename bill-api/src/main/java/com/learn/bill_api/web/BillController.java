package com.learn.bill_api.web;

import com.learn.bill_api.model.Bill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class BillController {

    @GetMapping("/api/bills")
    public List<Bill> bills(@RequestParam Long userId) {
        // 写死的数据：演示"服务间调用"足够了
        return List.of(
                new Bill(1L, userId, "注册礼金", new BigDecimal("10.00")),
                new Bill(2L, userId, "午饭", new BigDecimal("25.00"))
        );
    }
}
