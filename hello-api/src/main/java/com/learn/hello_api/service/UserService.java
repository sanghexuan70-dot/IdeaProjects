package com.learn.hello_api.service;

import com.learn.hello_api.model.User;
import com.learn.hello_api.web.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User create(String name, String email);
    PageResult<User> query(String keyword, LocalDate createdFrom, LocalDate createdTo, int page, int size);
    User findById(Long id);          // 找不到返回 null（真实项目用 Optional，AI 补位层）
    User update(Long id, String name, String email);
    void delete(Long id);
    User registerWithInitBill(String name, String email);
}
