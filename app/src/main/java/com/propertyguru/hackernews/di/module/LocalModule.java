package com.propertyguru.hackernews.di.module;

import android.content.SharedPreferences;

import com.propertyguru.hackernews.data.local.SharedPref;
import com.propertyguru.hackernews.data.local.SharedPrefImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that contains data access objects for local sources
 * i.e. shared pref helper, database dao
 */
@Module
public class LocalModule {
    @Singleton
    @Provides
    static SharedPref sharePref(SharedPreferences sharedPreferences) {
        return new SharedPrefImpl(sharedPreferences);
    }
}
