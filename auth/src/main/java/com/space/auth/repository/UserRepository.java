package com.space.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ユーザー名でユーザーを検索するメソッド
     * @param username ユーザー名
     * @return ユーザー情報
     */
    User findByUsername(String username);
}
