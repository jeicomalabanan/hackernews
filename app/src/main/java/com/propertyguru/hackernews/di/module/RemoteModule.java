package com.propertyguru.hackernews.di.module;

import com.propertyguru.hackernews.data.remote.api.HackerNewsApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module that contains API
 */
@Module
public class RemoteModule {
    @Singleton
    @Provides
    static HackerNewsApi hackerNewsApi(Retrofit retrofit) {
        return retrofit.create(HackerNewsApi.class);
    }
}
