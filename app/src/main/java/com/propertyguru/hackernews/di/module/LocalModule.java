package com.propertyguru.hackernews.di.module;

import android.content.SharedPreferences;

import com.propertyguru.hackernews.data.local.SharedPref;
import com.propertyguru.hackernews.data.local.SharedPrefImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {
    @Singleton
    @Provides
    static SharedPref sharePref(SharedPreferences sharedPreferences) {
        return new SharedPrefImpl(sharedPreferences);
    }
}
