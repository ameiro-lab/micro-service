package com.space.ap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.space.ap.model.User;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ユーザー名でレコード検索
        // User user = userRepository.findByUserName(username)
        //     .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした: " + username));

        // デバッグ用：プレーンテキストパスワードをその場でエンコード
        String plainPassword = "123";
        String encodedPassword = passwordEncoder.encode(plainPassword);
        // DB未作成のためデバック用データ
        User user = new User(username, encodedPassword);
            
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserName())
            .password(user.getPassword()) // パスワードは暗号化されている必要があります
            .authorities("USER") // ユーザーの権限
            .build();
    }
}
