package com.propertyguru.hackernews.di.module.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.propertyguru.hackernews.di.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        ActivityViewModelModule.class,
        FragmentViewModelModule.class
})
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
