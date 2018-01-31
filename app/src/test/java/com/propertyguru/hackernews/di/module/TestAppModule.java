package com.propertyguru.hackernews.di.module;

import com.propertyguru.hackernews.BuildConfig;
import com.propertyguru.hackernews.di.qualifier.ApiDateFormat;
import com.propertyguru.hackernews.di.qualifier.ApiEndpoint;
import com.propertyguru.hackernews.di.qualifier.AppId;
import com.propertyguru.hackernews.di.qualifier.IsDebugMode;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

@Module
public class TestAppModule {
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
    static HttpUrl apiEndpoint(MockWebServer mockWebServer) {
        return mockWebServer.url("/");
    }

    @ApiDateFormat
    @Provides
    static String apiDateFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @Singleton
    @Provides
    static MockWebServer mockWebServer() {
        return new MockWebServer();
    }

    @Named("http-cache")
    @Provides
    static File cacheFile() {
        return new File("build/http-cache");
    }
}
