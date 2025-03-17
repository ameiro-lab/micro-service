package com.space.ap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Spring Securityのフィルタチェーン構成
     * APIエンドポイントへのアクセス制御とセキュリティ設定を定義
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // REST APIではCSRF保護は通常無効化
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()  // ログインAPIは認証不要
                .anyRequest().authenticated()                                // その他は認証必須
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // CORS設定の適用

        return http.build();
    }

    /**
     * CORS (Cross-Origin Resource Sharing) 設定
     * フロントエンドからのクロスオリジンリクエストを制御
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));  // Vue.jsフロントエンドのオリジン
        configuration.setAllowedMethods(List.of("POST"));                   // 許可するHTTPメソッド
        configuration.setAllowedHeaders(List.of("Content-Type"));           // 許可するHTTPヘッダー

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);             // すべてのパスに適用
        
        return source;
    }

    /**
     * 認証マネージャーの設定
     * ログイン認証処理で使用される
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * パスワードエンコーダーの設定
     * ユーザーパスワードのハッシュ化に使用される
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}