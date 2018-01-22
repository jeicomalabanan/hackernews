package com.propertyguru.hackernews.ui.commentlist;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.propertyguru.hackernews.R;
import com.propertyguru.hackernews.base.BaseActivity;
import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.constant.App;
import com.propertyguru.hackernews.data.constant.BundleKey;
import com.propertyguru.hackernews.databinding.ActivityCommentListBinding;
import com.propertyguru.hackernews.util.ApiCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentListActivity extends BaseActivity {
    public static Intent newIntent(Activity activity, long storyId) {
        Intent intent = new Intent(activity, CommentListActivity.class);
        intent.putExtra(BundleKey.STORY_ID, storyId);
        return intent;
    }

    private CommentListActivityVM viewModel;
    private ActivityCommentListBinding binding;
    private final CommentAdapter commentAdapter = new CommentAdapter();
    private long storyId = App.INVALID_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommentListActivityVM.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_list);
        binding.setVm(viewModel);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        RecyclerView recyclerView = binding.rvCommentList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        if (getIntent() != null) {
            storyId = getIntent().getLongExtra(BundleKey.STORY_ID, App.INVALID_ID);
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getComments();
            }
        });

        getComments();
    }

    private void getComments() {
        viewModel.getComments(
                storyId,
                new ApiCallback<Comment>() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onSuccess(Comment comment, String successMsg) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        List<Comment> commentList = new ArrayList<>();
                        commentList.add(comment);
                        commentAdapter.addToList(commentList);
                    }

                    @Override
                    public void onError(String errMsg) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
}
