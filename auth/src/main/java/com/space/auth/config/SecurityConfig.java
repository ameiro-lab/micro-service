package com.space.auth.config;

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
                .requestMatchers(HttpMethod.POST, "/api/import/users").permitAll()  // ユーザーインポートAPIも認証不要
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
     * パスワードをハッシュ化・照合するための PasswordEncoder を Spring コンテナに登録する。
     * 
     * Spring Security はパスワードの比較時に PasswordEncoder を必要とするが、
     * デフォルトでは登録されていないため、アプリケーションで明示的に定義する必要がある。
     *
     * この Bean は以下のような場面で使用される：
     * - ユーザー登録時に平文パスワードをハッシュ化する
     * - ログイン認証時に入力されたパスワードとDB上のハッシュを比較する
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 認証処理の中核となる AuthenticationManager を Spring コンテナに登録する。
     * Spring Security では AuthenticationManager は自動で Bean 化されないため、
     * 明示的に定義して Controller などで注入できるようにする必要がある。
     *
     * @param config Spring が内部で用意する AuthenticationConfiguration
     * @return 認証に使用する AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}