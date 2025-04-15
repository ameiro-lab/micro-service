package com.space.ap.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.space.ap.model.User;
import com.space.ap.service.LoginService;
import com.space.ap.util.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    /** new */
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationManager authenticationManager) {
            this.loginService = loginService;
            this.authenticationManager = authenticationManager; // コンストラクタで注入
    }

    /**
     * ユーザー名・パスワードを送ると、JWT を発行する。
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody User loginRequest, HttpServletResponse response) {

        try {
            
            // Spring Securityでユーザー名とパスワードが本物かどうか検証
            Authentication authentication = authenticationManager.authenticate( // authenticateメソッド
                // ユーザー名とパスワードを保持した 未認証トークンを生成
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            /**
             * authenticateメソッドの内部処理
             * 1：適切な AuthenticationProvider を選ぶ
             * 2：UserDetailsService#loadUserByUsernameが呼ばれ、ユーザー名に該当するユーザーをDBから取得する
             * 3：入力された平文パスワードと、DBから取得したハッシュ済みパスワードを照合
             * 4：成功したら「認証済みのトークン」を返す
             */
            );

            // 認証済みのユーザー情報を取得
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // JWTトークンを生成
            String token = loginService.generateToken(userDetails);

            // ResponseCookieを使ってクッキーを作成
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                    // .httpOnly(true)       // JavaScriptからアクセスできないようにする
                    // .secure(true)           // 本番環境では true（HTTPS）
                    // .sameSite("Strict")   // CSRF対策（"Lax"でも可）
                    .maxAge(3600)    // 1時間の有効期限
                    .path("/")                // すべてのドメインに適用
                    .build();

            // レスポンスヘッダーにクッキーを追加
            response.addHeader("Set-Cookie", cookie.toString());

            // SecurityContextに認証情報を保存（省略可だけどおすすめ）
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 正常系
            Map<String, Object> data = Map.of(
                "username", userDetails.getUsername(),
                "roles", userDetails.getAuthorities()
            );
            return ApiResponse.success(data);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // 認証失敗: パスワードまたはユーザー名が間違っている場合
            return new ApiResponse(401, e.getMessage());
        }
    }
}
