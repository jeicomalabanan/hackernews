package com.propertyguru.hackernews.data.remote.interceptor;

import com.propertyguru.hackernews.data.local.SharedPref;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class ClientInterceptor implements Interceptor {
    private final SharedPref sharedPref;

    @Inject
    public ClientInterceptor(SharedPref sharedPref) {
        this.sharedPref = sharedPref;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        return chain.proceed(original.newBuilder().build());
    }
}
