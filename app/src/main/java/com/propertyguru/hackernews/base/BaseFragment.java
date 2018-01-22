package com.propertyguru.hackernews.base;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
}
