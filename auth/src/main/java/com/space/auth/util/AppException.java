package com.space.auth.util;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 共通カスタム例外クラス
 */
@Getter
public class AppException extends RuntimeException {
    
    // Error管理用の列挙型
    private final ErrorCode errorCode;
    private final Map<String, Object> vars;

    /** コンストラクタ(varsが渡されない場合) */
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.vars = null;  // varsが渡されない場合はnull
    }

    /** コンストラクタ(varsが渡された場合) */
    public AppException(ErrorCode errorCode, Map<String, Object> vars) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.vars = vars != null ? vars : new HashMap<>();
    }

}
