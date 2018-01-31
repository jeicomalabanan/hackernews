package com.propertyguru.hackernews.di.component;

import com.propertyguru.hackernews.data.api.HackerNewsApiTest;
import com.propertyguru.hackernews.data.repository.HackNewsRepositoryTest;
import com.propertyguru.hackernews.di.module.DataModule;
import com.propertyguru.hackernews.di.module.NetworkModule;
import com.propertyguru.hackernews.di.module.RemoteModule;
import com.propertyguru.hackernews.di.module.TestAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        NetworkModule.class,
        RemoteModule.class,
        TestAppModule.class,
})
public interface TestAppComponent {
    void inject(HackerNewsApiTest hackerNewsApiTest);
    void inject(HackNewsRepositoryTest hackNewsRepositoryTest);
}
