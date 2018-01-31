package com.propertyguru.hackernews.ui.commentlist;

import android.support.annotation.NonNull;

import com.propertyguru.hackernews.common.BaseActivityVM;
import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.repository.HackerNewsRepository;
import com.propertyguru.hackernews.util.ApiCallback;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommentListActivityVM extends BaseActivityVM {
    private final HackerNewsRepository repository;

    @Inject
    public CommentListActivityVM(HackerNewsRepository repository) {
        this.repository = repository;
    }


    public void getComments(long storyId, @NonNull final ApiCallback<Comment> apiCallback) {
        repository.getComments(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Comment>() {
                    @Override
                    public void accept(Comment comment) throws Exception {
                        apiCallback.onSuccess(comment, null);
                    }
                });
    }

}
