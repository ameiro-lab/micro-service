package com.space.ap.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

/*
 * Json Web Token ユーティリティクラス
 */
@Component
public class JwtUtil {

    /**
     * JWT を発行する。
     * @param username
     * @return
     */
    public String generateToken(String username) {

        // シークレットキー（通常は設定ファイルや環境変数から読み込む）
        final String SECRET_KEY = "your-very-secure-and-long-secret-key-32bytes";

        final Long EXPIRATION_TIME = 1000L * 60L * 60L * 1L;

        try {

            //生成のため、日時データを取得する
            Date issuedAt = new Date();
            Date notBefore = new Date(issuedAt.getTime());
            Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

            return JWT.create()
                .withIssuer("わたし")     // 発行するシステムやサービスの識別情報
                .withSubject(username)          // ユーザーやクライアントの識別情報
                .withAudience(username)         // トークンの利用者（メールアドレスを用いてトークンを一意にする）
                .withIssuedAt(issuedAt)         // 発行日時
                .withNotBefore(notBefore)       // 有効期間開始時間
                .withExpiresAt(expiresAt)       // 有効期間終了時間 今回はログアウト、セッションタイムアウトまで保持
                .withClaim("username", username)  // カスタムクレームの追加
                .sign(Algorithm.HMAC256(SECRET_KEY)); //  署名を行う(HMAC256アルゴリズムで署名アルゴリズム)

        } catch(JWTCreationException exception) {
            throw new RuntimeException("トークン生成に失敗しました", exception);
        }    
    }
    
}
