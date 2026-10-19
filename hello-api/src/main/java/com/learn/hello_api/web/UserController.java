package com.learn.hello_api.web;


import com.learn.hello_api.Result;
import com.learn.hello_api.client.BillClient;
import com.learn.hello_api.model.User;
import com.learn.hello_api.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "用户管理", description = "用户资源的增删改查")   // import io.swagger.v3.oas.annotations.tags.Tag
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired     // ← 关键：容器，给我一个 UserService
    private UserService userService;
    private final BillClient billClient;

    // 构造器注入
    public UserController(BillClient billClient) {
        this.billClient = billClient;
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public Result<User> create(@Valid @RequestBody CreateUserRequest req) {
        return Result.success(userService.create(req.name(), req.email()));
    }

    @Operation(summary = "查询用户列表（关键字+创建时间范围+分页）")
    @GetMapping
    public Result<PageResult<User>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDate createdFrom,   // 格式 2026-10-01
            @RequestParam(required = false) LocalDate createdTo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(userService.query(keyword, createdFrom, createdTo, page, size));
    }

    @Operation(summary = "查询单个用户")
    @GetMapping("/{id}")
    public Result<User> detail(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @Operation(summary = "编辑用户")
    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id,@Valid @RequestBody CreateUserRequest req) {
        return Result.success(userService.update(id, req.name(), req.email()));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success(null);
    }

    public record CreateUserRequest(
            @NotBlank(message = "姓名不能为空")
            String name,

            @NotBlank(message = "邮箱不能为空")
            @Email(message = "邮箱格式不正确")
            String email) {
    }

    @Operation(summary = "注册并送注册礼金")
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody CreateUserRequest req) {
        return Result.success(userService.registerWithInitBill(req.name(), req.email()));
    }

    // 聚合接口：查询指定用户的账单
    @Operation(summary = "查某用户的账单（跨服务调用）")
    @GetMapping("/{id}/bills")
    public Result<List<BillClient.Bill>> getUserBills(@PathVariable Long id) {
        userService.findById(id);                        // 用户不存在则 404（复用校验）
        return Result.success(billClient.bills(id));     // 跨进程拿数据
    }
}