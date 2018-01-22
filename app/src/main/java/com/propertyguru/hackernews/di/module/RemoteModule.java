package com.propertyguru.hackernews.di.module;

import com.propertyguru.hackernews.data.remote.api.HackerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RemoteModule {
    @Singleton
    @Provides
    static HackerApi hackerApi(Retrofit retrofit) {
        return retrofit.create(HackerApi.class);
    }
}
