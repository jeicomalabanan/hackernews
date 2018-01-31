package com.propertyguru.hackernews.common;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.propertyguru.hackernews.BR;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity <B extends ViewDataBinding, V extends BaseActivityVM>
        extends DaggerAppCompatActivity {
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected V viewModel;
    protected B binding;

    protected abstract BindingData<V> getBindingData();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindingData<V> bindingData = getBindingData();

        if (bindingData == null) throw new NullPointerException("Binding data cannot be null");

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(bindingData.getClazz());
        binding = DataBindingUtil.setContentView(this, bindingData.getLayoutResId());
        binding.setVariable(BR.vm, viewModel);

        subscribeToEvents(viewModel);
    }

    protected abstract void subscribeToEvents(V viewModel);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Class that contains details for activity/view model binding
     *
     * @param <V>
     */
    public static class BindingData<V extends BaseActivityVM> {
        private int layoutResId;
        private Class<V> clazz;

        public BindingData(@LayoutRes int layoutResId, Class<V> clazz) {
            this.layoutResId = layoutResId;
            this.clazz = clazz;
        }

        int getLayoutResId() {
            return layoutResId;
        }

        Class<V> getClazz() {
            return clazz;
        }
    }
}
