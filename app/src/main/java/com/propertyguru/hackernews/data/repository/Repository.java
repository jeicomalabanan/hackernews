package com.propertyguru.hackernews.data.repository;

import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.model.Story;

import java.util.List;

import io.reactivex.Flowable;

public interface Repository {
    Flowable<List<Long>> getTopStories();
    Flowable<Story> getStory(long storyId);
    Flowable<Comment> getComments(long storyId);
}
