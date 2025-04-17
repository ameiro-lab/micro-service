package com.space.auth.util;

/** Error管理用の列挙型 */
public enum ErrorCode {

    /** enum の定数定義 */
    USER_NOT_FOUND(404, "E-AUTH-001", "ユーザーが見つかりません。"),
    JWT_GENERATION_FAILED(500, "E-JWT-001", "JWTの生成に失敗しました。");

    /** フィールド */
    private final int httpStatus;
    private final String id;
    private final String message;

    /** コンストラクタ */
    ErrorCode(int httpStatus, String id, String message) {
        this.httpStatus = httpStatus;
        this.id = id;
        this.message = message;
    }

    /** getter 
     * ※enum は不変であるべき（イミュータブル）なクラスのため、Setterは作らない */
    public int getHttpStatus() {
        return httpStatus;
    }
    public String getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
}
