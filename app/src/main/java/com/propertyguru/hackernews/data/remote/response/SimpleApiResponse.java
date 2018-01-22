package com.propertyguru.hackernews.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class SimpleApiResponse {
    @SerializedName("success")
    protected boolean success;
    @SerializedName("message")
    protected String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiSimpleResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
