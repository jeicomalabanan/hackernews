package com.propertyguru.hackernews.common;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;

public class BaseActivityVM extends ViewModel {
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
