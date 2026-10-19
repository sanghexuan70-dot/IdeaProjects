package com.learn.hello_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.hello_api.model.Bill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
}