package com.propertyguru.hackernews.di.module;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.propertyguru.hackernews.di.qualifier.ApiDateFormat;
import com.propertyguru.hackernews.di.qualifier.ApiEndpoint;
import com.propertyguru.hackernews.di.qualifier.IsDebugMode;
import com.propertyguru.hackernews.util.DateDeserializer;

import java.io.File;
import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Module that contain network configurations
 */
@Module
public class NetworkModule {
    @Singleton
    @Provides
    static Cache cache(@Named("http-cache") File file) {
        Cache cache = null;

        try {
            int cacheSize = 10 * 1024 * 1024; // 10mb cache
            cache = new Cache(file, cacheSize);
        } catch (Exception e) {
            Timber.e(e, "Could not create Cache!");
        }

        return cache;
    }

    @Singleton
    @Provides
    static OkHttpClient okHttpClient(Cache cache, @IsDebugMode boolean isDebugMode) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        Timber.d(message);
                    }
                }).setLevel(isDebugMode ? BODY : NONE))
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    static Gson gson(@ApiDateFormat String apiDateFormat) {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }

    @Singleton
    @Provides
    static Converter.Factory converterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    static CallAdapter.Factory callAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Singleton
    @Provides
    static Retrofit retrofit(@ApiEndpoint HttpUrl apiEndpoint, OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }
}
