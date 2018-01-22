package com.propertyguru.hackernews.util;

public interface ApiCallback<T> {
    void onLoading();
    void onSuccess(T t, String successMsg);
    void onError(String errMsg);
}
