package com.space.ap.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    /** constructor */
    public ApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int status, String message) {
        this(status, message, null);
    }

    /** success */
    public static ApiResponse success(Object data) {
        return new ApiResponse(200, "OK", data);
    }
    public static ApiResponse success(Object data, String message) {
        return new ApiResponse(200, message, data);
    }

    /** error */
    public static ApiResponse error(int status, String message) {
        return new ApiResponse(status, message, null);
    }

    /** getter */
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
