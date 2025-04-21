package com.space.auth.controller;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.space.auth.service.ImportService;
import com.space.auth.util.ApiResponse;
import com.space.auth.util.AppException;
import com.space.auth.util.ErrorCode;

@RestController
@RequestMapping("/api")
public class ImportController {
    
    @Autowired
    private ImportService userImportService;

    /**
     * ユーザーインポートAPI
     * CSVファイルからユーザーをインポートする
     */
    @PostMapping("/import/users")
    public ApiResponse importUsers() {
        try {
            userImportService.importUserCsv("users.csv"); // TO DO：パスは必要に応じて調整
            return ApiResponse.success("インポート成功！");

        } catch (IOException  e) {
            throw new AppException(ErrorCode.USER_IMPORT_FAILED, Collections.emptyMap());
        }
    }
}
