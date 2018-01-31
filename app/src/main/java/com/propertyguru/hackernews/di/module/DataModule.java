package com.propertyguru.hackernews.di.module;

import com.propertyguru.hackernews.data.remote.api.HackerNewsApi;
import com.propertyguru.hackernews.data.repository.HackerNewsRepository;
import com.propertyguru.hackernews.data.repository.HackerNewsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that contain repositories
 */
@Module
public class DataModule {
    @Singleton
    @Provides
    static HackerNewsRepository hackerNewsRepository(HackerNewsApi hackerNewsApi) {
        return new HackerNewsRepositoryImpl(hackerNewsApi);
    }
}
