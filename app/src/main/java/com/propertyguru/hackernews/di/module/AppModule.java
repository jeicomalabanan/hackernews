package com.propertyguru.hackernews.di.module;

import android.content.Context;

import com.propertyguru.hackernews.BuildConfig;
import com.propertyguru.hackernews.di.qualifier.ApiDateFormat;
import com.propertyguru.hackernews.di.qualifier.ApiEndpoint;
import com.propertyguru.hackernews.di.qualifier.AppContext;
import com.propertyguru.hackernews.di.qualifier.AppId;
import com.propertyguru.hackernews.di.qualifier.IsDebugMode;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;
import okhttp3.HttpUrl;

@Module
public class AppModule {
    @AppContext
    @Provides
    static Context context(DaggerApplication daggerApplication) {
        return daggerApplication;
    }

    @ApiEndpoint
    @Singleton
    @Provides
    static HttpUrl apiEndpoint() {
        return HttpUrl.parse(BuildConfig.BASE_URL);
    }

    @ApiDateFormat
    @Singleton
    @Provides
    static String apiDateFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @IsDebugMode
    @Singleton
    @Provides
    static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    @AppId
    @Singleton
    @Provides
    static String appId() {
        return BuildConfig.APPLICATION_ID;
    }
}
