package com.khaledamin.pharmacy.base;

import lombok.Builder;

public class BaseResponseWithData<T> extends BaseResponse {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponseWithData(String message, T data) {
        super(true, message);
        this.data = data;
    }

    public BaseResponseWithData() {
    }
}
