package com.propertyguru.hackernews.base;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
}
