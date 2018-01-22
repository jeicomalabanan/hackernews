package com.propertyguru.hackernews.util.databinding;

import android.databinding.BindingAdapter;
import android.view.View;

public class ViewBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleHidden")
    public static void visibleHidden(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
