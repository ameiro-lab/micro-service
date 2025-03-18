package com.space.ap.controller;

import static com.space.ap.util.JwtUtil.generateToken;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.space.ap.model.User;
import com.space.ap.util.ApiResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * ユーザー名・パスワードを送ると、JWT を発行する。
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody User loginRequest) {

        try {

            // リクエストデータを取得する。
            String userName = loginRequest.getUserName();
            String password = loginRequest.getPassword();

            // ユーザー名とパスワードを含む認証情報（トークン）を作成する。
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userName, password
                )
            );
            // 認証済み情報を SecurityContext に保存する。
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT を発行
            String token = generateToken(userName);

            // 正常系
            Map<String, Object> data = new HashMap<>();
            data.put("userName", userName);
            data.put("password", password);
            data.put("token", token);
            return ApiResponse.success(data);

        } catch (BadCredentialsException e) {
            // 異常系
            return new ApiResponse(401, "ユーザー名またはパスワードが間違っています");
        }
    }
}
