package com.propertyguru.hackernews.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.propertyguru.hackernews.R;
import com.propertyguru.hackernews.common.BaseActivity;
import com.propertyguru.hackernews.data.model.Story;
import com.propertyguru.hackernews.databinding.ActivityMainBinding;
import com.propertyguru.hackernews.ui.commentlist.CommentListActivity;
import com.propertyguru.hackernews.util.ApiCallback;

import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityVM> {
    @Override
    protected BindingData<MainActivityVM> getBindingData() {
        return new BindingData<>(R.layout.activity_main, MainActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(binding.toolbar);

        RecyclerView recyclerView = binding.rvStoryList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(storyAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTopStories();
            }
        });

        binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoreClicked();
            }
        });

        if (storyAdapter.getItemCount() == 0) {
            requestTopStories();
        }
    }

    @Override
    protected void subscribeToEvents(MainActivityVM viewModel) {

    }

    private void requestTopStories() {
        viewModel.getTopStories(new ApiCallback<List<Story>>() {
            @Override
            public void onLoading() {
                binding.pbMore.setVisibility(View.GONE);
                binding.btnMore.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(List<Story> stories, String successMsg) {
                binding.swipeRefreshLayout.setRefreshing(false);
                storyAdapter.setList(stories);

                if (stories.size() != 0)
                    binding.btnMore.setVisibility(View.VISIBLE);
                else
                    binding.btnMore.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errMsg) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.btnMore.setVisibility(View.GONE);
            }
        });
    }

    private void onMoreClicked() {
        viewModel.getNextTopStories(new ApiCallback<List<Story>>() {
            @Override
            public void onLoading() {
                binding.pbMore.setVisibility(View.VISIBLE);
                binding.btnMore.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(List<Story> stories, String successMsg) {
                storyAdapter.addToList(stories);
                binding.pbMore.setVisibility(View.GONE);
                binding.btnMore.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String errMsg) {
                binding.pbMore.setVisibility(View.GONE);
                binding.btnMore.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showCommentListUi(@NonNull Story story) {
        Intent intent = CommentListActivity.newIntent(this, story.getId());
        startActivity(intent);
    }

    private final StoryAdapter storyAdapter = new StoryAdapter(new StoryAdapter.Callback() {
        @Override
        public void onViewComments(Story story) {
            showCommentListUi(story);
        }
    });
}
