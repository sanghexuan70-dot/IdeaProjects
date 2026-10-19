package com.learn.hello_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.hello_api.exception.BizException;
import com.learn.hello_api.mapper.UserMapper;
import com.learn.hello_api.mapper.BillMapper;
import com.learn.hello_api.model.Bill;
import com.learn.hello_api.model.User;
import com.learn.hello_api.service.UserService;
import com.learn.hello_api.web.PageResult;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@Service
public class DbUserService implements UserService {

    private final UserMapper userMapper;
    private final BillMapper billMapper;

    public DbUserService(UserMapper userMapper, BillMapper billMapper) {   // 构造器注入（C09 讲过的推荐姿势）
        this.userMapper = userMapper;
        this.billMapper = billMapper;
    }

    @Override
    public User create(String name, String email) {
        User user = new User();          // 不设 id——自增主键轮不到客户端说话（C07 的老原则）
        user.setName(name);
        user.setEmail(email);
        userMapper.insert(user);         // 插入后 id 自动回填进 user 对象
        return user;                     // 返回带 id、带 createdAt 的完整实体
    }

    @Override
    public PageResult<User> query(String keyword, LocalDate createdFrom, LocalDate createdTo,
                                  int page, int size) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                // 关键字匹配 name 或 email——and(嵌套) 里放 or，括号语义
                .and(StringUtils.hasText(keyword), q -> q
                        .like(User::getName, keyword)
                        .or()
                        .like(User::getEmail, keyword));
        if (createdFrom != null) {
            wrapper.ge(User::getCreatedAt, createdFrom.atStartOfDay());
        }

        if (createdTo != null) {
            wrapper.lt(
                    User::getCreatedAt,
                    createdTo.plusDays(1).atStartOfDay()
            );
        }
        Page<User> result = userMapper.selectPage(new Page<>(page, size), wrapper);
        return new PageResult<>(result.getTotal(), page, size, result.getRecords());
    }

    @Override
    public User findById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BizException(404, "用户不存在: " + id);
        }
        return user;
    }

    @Override
    public User update(Long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        userMapper.updateById(user);     // null 字段默认不更新（createdAt 不会被冲掉）
        return findById(id);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Transactional   // ← 本卡主角：方法内所有写库绑成一体
    @Override
    public User registerWithInitBill(String name, String email) {
        // 写库 ①：建用户
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userMapper.insert(user);          // id 已回填（C11 知识复用）

        // 写库 ②：送注册礼金账单
        Bill bill = new Bill();
        bill.setUserId(user.getId());
        bill.setTitle("注册礼金");
        bill.setAmount(BigDecimal.TEN);
        bill.setType("INCOME");
        bill.setCategory("奖金");
        bill.setBillDate(LocalDate.now());
        billMapper.insert(bill);


        return user;
    }

}