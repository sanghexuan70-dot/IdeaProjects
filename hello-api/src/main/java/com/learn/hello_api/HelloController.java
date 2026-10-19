package com.learn.hello_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Result<String> hello(@RequestParam(defaultValue = "world") String name) {
        return Result.success("hello, " + name);
    }
}