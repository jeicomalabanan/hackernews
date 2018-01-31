package com.propertyguru.hackernews.data.remote.api;

import com.propertyguru.hackernews.data.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {
    @GET("topstories.json")
    Flowable<List<Long>> getTopStories();

    @GET("item/{id}.json")
    Flowable<Item> getItem(@Path("id") long id);
}
