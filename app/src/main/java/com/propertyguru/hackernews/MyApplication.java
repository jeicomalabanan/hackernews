package com.propertyguru.hackernews;

import com.propertyguru.hackernews.di.component.DaggerAppComponent;
import com.propertyguru.hackernews.di.qualifier.IsDebugMode;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class MyApplication extends DaggerApplication {

    @IsDebugMode
    @Inject
    boolean isDebugMode;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
