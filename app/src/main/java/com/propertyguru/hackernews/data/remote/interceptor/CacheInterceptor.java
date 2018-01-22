package com.propertyguru.hackernews.data.remote.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

@Singleton
public class CacheInterceptor implements Interceptor {
    private static final String CACHE_CONTROL = "Cache-Control";

    @Inject
    public CacheInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        // re-write response header to force use of cache
        CacheControl cacheControl =  new CacheControl.Builder()
                .maxAge(2, TimeUnit.MINUTES)
                .build();

        return response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build();
    }
}
