package com.space.ap.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    // Spring Security のフィルタチェーンを定義する
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF無効化
            .authorizeHttpRequests(authz -> authz
            // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONSメソッドに対しては認証なしで許可 ⇒ 無くてもエラー出ない。プリフライトは？
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll() // /api/loginは認証不要
                // .anyRequest().authenticated())           // その他のリクエストは認証が必要
            // .formLogin(form -> form.disable())           // フォームログイン無効化
            // .httpBasic(httpBasic -> httpBasic.disable()  // HTTP Basic認証無効化
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // CROS設定

        return http.build();
    }

    // CROSの設定
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // クロスオリジンリクエストにおいて、許可するリクエストを設定する。
        CorsConfiguration configuration = new CorsConfiguration();
        // オリジンを設定
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // HTTPメソッドを設定
        configuration.setAllowedMethods(List.of("POST"));   // 段階的に増やしていく    "GET", "PUT", "DELETE", "OPTIONS"
        // クッキーやAuthorizationヘッダーをリクエストに含むことを許可するか
        // configuration.setAllowCredentials(true);    // クライアントとサーバー間で、セッション情報や認証トークンを送信できるようにする。
        // HTTPヘッダーを設定
        configuration.setAllowedHeaders(List.of("Content-Type"));   // 段階的に増やしていく    "Authorization",  "Accept"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // この CORS 設定を全てのエンドポイントに適用

        return source;
    }

}
