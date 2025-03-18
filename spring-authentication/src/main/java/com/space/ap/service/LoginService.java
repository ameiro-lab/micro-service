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

    // @Autowired
    // private UserRepository userRepository; // 実際にDBができたらリポジトリをインジェクトする

    /**
     * ユーザー名でユーザーをロードし、ユーザー情報を返す。
     * @param username ユーザー名
     * @return UserDetails ユーザー詳細情報
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 実際のDBアクセス
        // User user = userRepository.findByUserName(username)
        //     .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした: " + username));

        // デバッグ用:
        String plainPassword = "123"; // 実際のアプリケーションではユーザー入力によるパスワードを使う
        String encodedPassword = passwordEncoder.encode(plainPassword); // プレーンテキストのパスワードをハッシュ化
        // デバッグ用: テストレコード生成
        User user = new User(username, encodedPassword);
            
        // JWTトークンにはパスワードは含まれていないので、ここではパスワードなしでユーザー情報を返す
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUserName())
        .password(user.getPassword())
        .authorities("USER") // ユーザー権限。例えば"USER"や"ADMIN"など
        .build();
    }
}