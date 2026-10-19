package com.learn.bill_api.model;

import java.math.BigDecimal;

public record Bill(Long id, Long userId, String title, BigDecimal amount) {
}
