package com.space.auth.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.space.auth.model.User;
import com.space.auth.repository.UserRepository;

@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    // コンストラクタインジェクション　※現在、推奨されている方法
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    /**
     * ユーザー名でユーザーをロードし、ユーザー情報を返す。
     * @param username ユーザー名
     * @return UserDetails ユーザー詳細情報
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 実際のDBアクセス
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }
            
        // JWTトークンにはパスワードは含まれていないので、ここではパスワードなしでユーザー情報を返す
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities("USER") // ユーザー権限を付与
        .build();
    }

    /**
     * JWT を発行する
     * @param user
     * @return
     */
    public String generateToken(UserDetails user) {
        try {
            // RSA鍵ペアの生成（TO DO：通常はここではなく外部ファイルやキーストアから読み込む）
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();

            // 鍵のキャスト：Algorithm.RSA256 に必要な型に変換
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            // RSA256 アルゴリズムの署名器を生成（秘密鍵を使って署名）
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

            // JWTを構築して署名
            return JWT.create()
                    .withSubject(user.getUsername())    // ユーザー名をサブジェクトに設定
                    .withIssuedAt(new Date())           // 発行日時
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(3600))) // 有効期限：1時間
                    .sign(algorithm);   // RSA256で署名

        } catch (Exception e) {
            // 例外処理：RSA鍵ペアの生成やJWTの署名に失敗した場合。ひとまずRuntimeExceptionを人工生成する。
            throw new RuntimeException(e);
        }
    }
}