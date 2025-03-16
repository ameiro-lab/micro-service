package com.space.ap.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.space.ap.model.User;
import com.space.ap.util.ApiResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User loginRequest) {

        // リクエストデータを取得する。
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        Map<String, Object> data = new HashMap<>();
        data.put("userName", userName);
        data.put("password", password);

        // レスポンス200を返却する。
        System.out.println("☆☆☆ username: " + userName + ", password: " + password);
        return ApiResponse.success(data);
    }
}
