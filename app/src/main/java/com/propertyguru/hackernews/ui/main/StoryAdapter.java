package com.propertyguru.hackernews.ui.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.propertyguru.hackernews.base.BaseRecyclerViewAdapter;
import com.propertyguru.hackernews.R;
import com.propertyguru.hackernews.data.model.Story;
import com.propertyguru.hackernews.databinding.HolderStoryBinding;

public class StoryAdapter extends BaseRecyclerViewAdapter<Story, StoryAdapter.StoryHolder> {
    private final Callback callback;

    public interface Callback {
        void onViewComments(Story story);
    }

    public StoryAdapter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HolderStoryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.holder_story,
                parent,
                false);
        binding.setCallback(callback);
        return new StoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        holder.binding.setPosition(position + 1);
        holder.binding.setStory(dataList.get(position));
        holder.binding.executePendingBindings();
    }

    static class StoryHolder extends RecyclerView.ViewHolder {
        final HolderStoryBinding binding;

        public StoryHolder(HolderStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
