package com.learn.hello_api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_bill")
public class Bill {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private BigDecimal amount;      // 金额 BigDecimal，两边（Java/DB）都不用浮点——C10 的约定闭环
    private String type;
    private String category;
    private LocalDate billDate;
    private LocalDateTime createdAt;
}