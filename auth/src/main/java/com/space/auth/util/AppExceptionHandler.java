package com.space.auth.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * アプリケーション全体の例外処理を一元管理するクラス
 */
@RestControllerAdvice
public class AppExceptionHandler {

    // ログをクラスに定義
    private static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    /**
     * AppExceptionを処理するメソッド
     * @param ex AppException情報
     * @return Api通信共通レスポンスクラス
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException ex) {

        // エラー発生時の情報をログに出力する。
        logAppException(ex, ex.getVars());

        // クライアントにエラーレスポンスを返す。
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(ApiResponse.error(ex.getErrorCode().getHttpStatus(), ex.getMessage()));
    }

    /**
     * エラーログを出力するメソッド
     * @param ex AppException情報
     * @param vars 関係変数
     */
    public static void logAppException(AppException ex, Map<String, Object> vars) {
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";

        StackTraceElement location = ex.getStackTrace()[0];
        String errorLocation = location.getClassName() + "." + location.getMethodName()
            + "(" + location.getFileName() + ":" + location.getLineNumber() + ")";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(RED)  // 赤色開始
            .append("========== [アプリケーションエラー] ==========\n")
            .append("[名称]         : ").append(ex.getErrorCode()).append("\n")
            .append("[ID]           : ").append(ex.getErrorCode().getId()).append("\n")
            .append("[時刻]         : ").append(timestamp).append("\n")
            .append("[発生場所]     : ").append(errorLocation).append("\n")
            .append("[メッセージ]   : ").append(ex.getMessage()).append("\n");
    
        if (vars != null && !vars.isEmpty()) {
            sb.append("[関連変数]\n");
            vars.forEach((k, v) -> sb.append(" - ").append(k).append(": ").append(v).append("\n"));
        }
    
        sb.append("===========================================\n")
            .append(RESET)  // 色リセット
            .append(getStackTraceAsString(ex));
    
        // ログにエラーメッセージを出力
        log.error(sb.toString());
    }
    
    /**
     * スタックトレースを文字列に変換するメソッド
     * @param ex AppException情報
     * @return String型のスタックトレース
     */
    private static String getStackTraceAsString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}