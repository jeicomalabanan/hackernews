package com.propertyguru.hackernews.common;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
}
