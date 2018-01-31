package com.propertyguru.hackernews.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.propertyguru.hackernews.di.qualifier.AppId;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;

/**
 * Module that contain data sources
 * i.e. shared preference, database
 */
@Module
public class SourceModule {
    @Singleton
    @Provides
    static SharedPreferences sharedPreferences(DaggerApplication daggerApplication, @AppId String appId) {
        return daggerApplication.getSharedPreferences(
                appId,
                Context.MODE_PRIVATE);
    }
}
