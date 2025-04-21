package com.space.auth.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User implements UserDetails { // UserDetails を継承

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("userName")  // リクエストの"userName"をuserNameフィールドにマッピング
    @Column(name = "\"user_name\"")
    private String username;

    @Column(name = "\"password\"")
    private String password;

    // 引数付きコンストラクタ
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // JPA用のデフォルトコンストラクタ
    public User() {
    }

    // ===== UserDetails 実装 =====

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER")); // 固定でUSERロール
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントの有効期限チェック（常に有効）
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ロック状態チェック（常に未ロック）
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 資格情報の期限（常に有効）
    }

    @Override
    public boolean isEnabled() {
        return true; // アカウントの有効化状態（常に有効）
    }
}