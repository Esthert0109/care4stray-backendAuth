package com.helloIftekhar.springJwt.Utils.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse<T> {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;

    @JsonProperty("data")
    private T data;

    public LoginResponse(String message, String token, T data) {
        this.message = message;
        this.token = token;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public T getData() {
        return data;
    }
}
