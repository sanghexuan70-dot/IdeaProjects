package com.learn.hello_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.hello_api.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 一行不写，单表 CRUD 全白送：insert/selectById/selectList/selectPage/updateById/deleteById
}