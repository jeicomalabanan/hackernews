package com.propertyguru.hackernews.di.module;

import android.content.Context;

import com.propertyguru.hackernews.BuildConfig;
import com.propertyguru.hackernews.di.qualifier.ApiDateFormat;
import com.propertyguru.hackernews.di.qualifier.ApiEndpoint;
import com.propertyguru.hackernews.di.qualifier.AppContext;
import com.propertyguru.hackernews.di.qualifier.AppId;
import com.propertyguru.hackernews.di.qualifier.IsDebugMode;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;
import okhttp3.HttpUrl;

/**
 * Module that contains app related data
 */
@Module
public class AppModule {
    @AppContext
    @Provides
    static Context context(DaggerApplication daggerApplication) {
        return daggerApplication;
    }

    @AppId
    @Provides
    static String appId() {
        return BuildConfig.APPLICATION_ID;
    }

    @IsDebugMode
    @Provides
    static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    @ApiEndpoint
    @Provides
    static HttpUrl apiEndpoint() {
        return HttpUrl.parse(BuildConfig.BASE_URL);
    }

    @ApiDateFormat
    @Provides
    static String apiDateFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @Named("http-cache")
    @Provides
    static File cacheFile(DaggerApplication daggerApplication) {
        return new File(daggerApplication.getCacheDir(), "http-cache");
    }
}
