package com.space.auth.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.space.auth.model.User;
import com.space.auth.repository.UserRepository;

@Service
public class ImportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * CSVファイルからユーザーを読み取り、DBに保存するメソッド
     * @param filePath csvファイル名
     * @throws Exception
     */
    public void importUserCsv(String filePath) throws IOException  {

        // classpathから読み込む（resources配下にcsvが置いてある前提）
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.lines().skip(1).forEach(line -> {     // 1行目はヘッダーなのでスキップ
                String[] data = line.split(",");
                String username = data[0].trim();       // ユーザー名
                String rawPassword = data[1].trim();    // 平文パスワード

                // パスワードをBCryptで暗号化
                String encodedPassword = passwordEncoder.encode(rawPassword);

                // ユーザーを生成してDBに保存
                User user = new User(username, encodedPassword);
                userRepository.save(user);
            });
        }
    }
}
