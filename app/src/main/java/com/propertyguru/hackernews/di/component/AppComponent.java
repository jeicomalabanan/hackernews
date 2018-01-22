package com.propertyguru.hackernews.di.component;

import com.propertyguru.hackernews.di.module.AppModule;
import com.propertyguru.hackernews.di.module.DataModule;
import com.propertyguru.hackernews.di.module.LocalModule;
import com.propertyguru.hackernews.di.module.NetworkModule;
import com.propertyguru.hackernews.di.module.RemoteModule;
import com.propertyguru.hackernews.di.module.SourceModule;
import com.propertyguru.hackernews.di.module.androidcomponent.AndroidComponentsModule;
import com.propertyguru.hackernews.di.module.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        AndroidComponentsModule.class,
        DataModule.class,
        LocalModule.class,
        NetworkModule.class,
        RemoteModule.class,
        SourceModule.class,
        ViewModelModule.class
})
interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerApplication> {}
}