package com.propertyguru.hackernews.di.module.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.propertyguru.hackernews.di.mapkey.ViewModelKey;
import com.propertyguru.hackernews.ui.commentlist.CommentListActivityVM;
import com.propertyguru.hackernews.ui.main.MainActivityVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityViewModelModule {
    // NOTE: customize here
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityVM.class)
    abstract ViewModel mainActivityVM(MainActivityVM mainActivityVM);

    @Binds
    @IntoMap
    @ViewModelKey(CommentListActivityVM.class)
    abstract ViewModel commentListActivityVM(CommentListActivityVM commentListActivityVM);
}
