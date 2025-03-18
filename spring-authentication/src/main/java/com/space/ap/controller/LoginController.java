package com.space.ap.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.space.ap.model.User;
import com.space.ap.service.LoginService;
import com.space.ap.util.ApiResponse;
import com.space.ap.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LoginService loginService;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder, JwtUtil jwtUtil, LoginService loginService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.loginService = loginService;  // コンストラクタで注入
    }

    /**
     * ユーザー名・パスワードを送ると、JWT を発行する。
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody User loginRequest, HttpServletResponse response) {

        try {

            // ユーザー情報を取得
            UserDetails userDetails = loginService.loadUserByUsername(loginRequest.getUserName());

            // パスワードの検証
            if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("パスワードが間違っています");

            } else {

                // JWTトークンを生成
                String token = jwtUtil.generateToken(userDetails.getUsername());

                // ResponseCookieを使ってクッキーを作成
                ResponseCookie cookie = ResponseCookie.from("JWT", token)
                        .httpOnly(true)       // JavaScriptからアクセスできないようにする
                        .maxAge(3600)    // 1時間の有効期限
                        .path("/")                // すべてのドメインに適用
                        .build();

                // レスポンスヘッダーにクッキーを追加
                response.addHeader("Set-Cookie", cookie.toString());

                // 正常系
                Map<String, Object> data = new HashMap<>();
                data.put("userDetails", userDetails);
                return ApiResponse.success(data);
            }

        } catch (BadCredentialsException e) {
            // 異常系: パスワードまたはユーザー名が間違っている場合
            return new ApiResponse(401, e.getMessage());
        }
    }
}
