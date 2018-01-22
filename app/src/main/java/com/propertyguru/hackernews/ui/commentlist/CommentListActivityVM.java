package com.propertyguru.hackernews.ui.commentlist;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.repository.Repository;
import com.propertyguru.hackernews.util.ApiCallback;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CommentListActivityVM extends ViewModel {
    private final Repository repository;

    @Inject
    public CommentListActivityVM(Repository repository) {
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
