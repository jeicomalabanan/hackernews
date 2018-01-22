package com.propertyguru.hackernews.di.module.androidcomponent;

import com.propertyguru.hackernews.ui.commentlist.CommentListActivity;
import com.propertyguru.hackernews.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    // NOTE: customize here
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract CommentListActivity commentListActivity();
}
