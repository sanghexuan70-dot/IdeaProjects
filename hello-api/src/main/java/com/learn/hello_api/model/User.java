package com.learn.hello_api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data                              // Lombok：getter/setter/equals/hashCode/toString 全自动
@TableName("t_user")               // 类名 User ≠ 表名 t_user，必须指明对应关系
public class User {

    @TableId(type = IdType.AUTO)   // 主键策略：用数据库自增（AUTO_INCREMENT）
    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;   // 驼峰 ↔ 下划线自动映射：createdAt ↔ created_at


}