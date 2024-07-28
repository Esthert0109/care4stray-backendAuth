package com.helloIftekhar.springJwt.Utils.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.crypto.Data;

public class Response<T> {
    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
