package com.space.ap.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class User {

    /** ユーザー名 */
    private String userName;
    /** パスワード */
    private String password;
}
