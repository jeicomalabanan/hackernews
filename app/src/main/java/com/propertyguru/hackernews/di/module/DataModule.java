package com.propertyguru.hackernews.di.module;

import com.propertyguru.hackernews.data.remote.api.HackerApi;
import com.propertyguru.hackernews.data.repository.Repository;
import com.propertyguru.hackernews.data.repository.RepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Singleton
    @Provides
    static Repository repository(HackerApi hackerApi) {
        return new RepositoryImpl(hackerApi);
    }
}
