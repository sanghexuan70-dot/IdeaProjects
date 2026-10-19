package com.learn.hello_api.web;

import com.learn.hello_api.Result;
import com.learn.hello_api.config.AppProperties;
import com.learn.hello_api.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {

    @Value("${spring.profiles.active}")    // 环境名本身也是配置：dev / prod
    private String activeProfile;


    private final AppProperties appProperties;   // 方式二：整组绑定

    public EnvController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/api/env")
    public Result<String> env() {
        return Result.success(appProperties.name() + " v" + appProperties.version()
                + "，运行在" + activeProfile);
    }

    @GetMapping("/api/config")
    public Result<AppProperties> config() {
        if (!"dev".equals(activeProfile)) {
            throw new BizException(403, "配置详情仅开发环境可看");   // 复用 C08 的异常
        }
        return Result.success(appProperties);
    }
}