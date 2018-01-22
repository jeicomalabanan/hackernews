package com.propertyguru.hackernews.ui.main;

import android.support.annotation.NonNull;

import com.propertyguru.hackernews.base.BaseActivityVM;
import com.propertyguru.hackernews.data.model.Story;
import com.propertyguru.hackernews.data.repository.Repository;
import com.propertyguru.hackernews.util.ApiCallback;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivityVM extends BaseActivityVM {
    private final Repository repository;
    private List<Long> storyIdList = Collections.emptyList();
    private int currentIndex = 0;

    @Inject
    public MainActivityVM(Repository repository) {
        this.repository = repository;
    }

    public void getTopStories(@NonNull final ApiCallback<List<Story>> callback) {
        repository.getTopStories()
                .flatMap(new Function<List<Long>, Publisher<List<Long>>>() {
                    @Override
                    public Publisher<List<Long>> apply(List<Long> storyIds) throws Exception {
                        storyIdList = storyIds;
                        currentIndex = 0;
                        return Flowable.just(getBatchId());
                    }
                })
                .flatMapIterable(new Function<List<Long>, Iterable<Long>>() {
                    @Override
                    public Iterable<Long> apply(List<Long> longs) throws Exception {
                        return longs;
                    }
                })
                .flatMap(new Function<Long, Publisher<Story>>() {
                    @Override
                    public Publisher<Story> apply(Long aLong) throws Exception {
                        return repository.getStory(aLong);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onLoading();
                    }

                    @Override
                    public void onSuccess(List<Story> stories) {
                        callback.onSuccess(stories, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("Error");
                    }
                });
    }

    public void getNextTopStories(@NonNull final ApiCallback<List<Story>> callback) {
        Flowable.fromIterable(getBatchId())
                .flatMap(new Function<Long, Publisher<Story>>() {
                    @Override
                    public Publisher<Story> apply(Long aLong) throws Exception {
                        return repository.getStory(aLong);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onLoading();
                    }

                    @Override
                    public void onSuccess(List<Story> stories) {
                        callback.onSuccess(stories, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("Error");
                    }
                });
    }

    private List<Long> getBatchId() {
        List<Long> batchIdList = new ArrayList<>();

        int limit = 9;
        int currentLimit = 0;

        while (currentIndex < storyIdList.size()) {
            batchIdList.add(storyIdList.get(currentIndex));
            currentIndex++;

            if (currentLimit < limit) {
                currentLimit++;
            } else {
                break;
            }
        }

        return batchIdList;
    }
}
