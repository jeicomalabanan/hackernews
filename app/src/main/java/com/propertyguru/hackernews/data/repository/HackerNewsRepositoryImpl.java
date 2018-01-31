package com.propertyguru.hackernews.data.repository;

import android.support.annotation.NonNull;

import com.propertyguru.hackernews.data.constant.App;
import com.propertyguru.hackernews.data.constant.ItemType;
import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.model.Item;
import com.propertyguru.hackernews.data.model.Story;
import com.propertyguru.hackernews.data.remote.api.HackerNewsApi;

import org.reactivestreams.Publisher;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class HackerNewsRepositoryImpl implements HackerNewsRepository {
    private final HackerNewsApi hackerApi;

    public HackerNewsRepositoryImpl(HackerNewsApi hackerApi) {
        this.hackerApi = hackerApi;
    }

    @Override
    public Flowable<List<Long>> getTopStories() {
        return hackerApi.getTopStories();
    }

    @Override
    public Flowable<Story> getStory(long storyId) {
        return hackerApi.getItem(storyId)
                .filter(new Predicate<Item>() {
                    @Override
                    public boolean test(Item item) throws Exception {
                        return ItemType.STORY.equalsIgnoreCase(item.type);
                    }
                })
                .map(new Function<Item, Story>() {
                    @Override
                    public Story apply(Item item) throws Exception {
                        return itemToStory(item);
                    }
                });
    }

    @Override
    public Flowable<Comment> getComments(long storyId) {
        return hackerApi.getItem(storyId)
                .flatMapIterable(new Function<Item, Iterable<Long>>() {
                    @Override
                    public Iterable<Long> apply(Item item) throws Exception {
                        return item.kids != null ? item.kids : Collections.<Long>emptyList();
                    }
                })
                .flatMap(new Function<Long, Publisher<Item>>() {
                    @Override
                    public Publisher<Item> apply(Long commentId) throws Exception {
                        return hackerApi.getItem(commentId);
                    }
                })
                .filter(new Predicate<Item>() {
                    @Override
                    public boolean test(Item item) throws Exception {
                        return item.text != null
                                && item.text.length() != 0
                                && ItemType.COMMENT.equalsIgnoreCase(item.type);
                    }
                })
                .flatMap(new Function<Item, Publisher<Comment>>() {
                    @Override
                    public Publisher<Comment> apply(Item item) throws Exception {
                        return getRecursiveComment(item, 0);
                    }
                });

    }

    private Publisher<Comment> getRecursiveComment(Item parentItem, final int depth) {
        if (parentItem.kids != null && parentItem.kids.size() > 0 && depth < App.DEPTH_LIMIT) {
            return Flowable.zip(
                    Flowable.just(parentItem),
                    Flowable.fromIterable(parentItem.kids)
                            .flatMap(new Function<Long, Publisher<Item>>() {
                                @Override
                                public Publisher<Item> apply(Long commentId) throws Exception {
                                    return hackerApi.getItem(commentId);
                                }
                            })
                            .filter(new Predicate<Item>() {
                                @Override
                                public boolean test(Item item) throws Exception {
                                    return item.text != null
                                            && item.text.length() != 0
                                            && ItemType.COMMENT.equalsIgnoreCase(item.type);
                                }
                            })
                            .flatMap(new Function<Item, Publisher<Comment>>() {
                                @Override
                                public Publisher<Comment> apply(Item item) throws Exception {
                                    return getRecursiveComment(item, depth + 1);
                                }
                            })
                            .toList()
                            .toFlowable(),
                    new BiFunction<Item, List<Comment>, Comment>() {
                        @Override
                        public Comment apply(Item item, List<Comment> commentList) throws Exception {
                            return itemToComment(
                                    item,
                                    depth,
                                    commentList);
                        }
                    });
        } else {
            return Flowable.just(
                    itemToComment(
                            parentItem,
                            depth,
                            Collections.<Comment>emptyList()));
        }
    }

    private Comment itemToComment(@NonNull Item item, int depth,
                                  @NonNull List<Comment> childList) {
        return Comment.newBuilder()
                .withId(item.id)
                .withBy(item.by)
                .withTime(item.time)
                .withText(item.text)
                .withDepth(depth)
                .withChildList(childList)
                .build();
    }

    private Story itemToStory(@NonNull Item item) {
        return Story.newBuilder()
                .withId(item.id)
                .withTitle(item.title)
                .withType(item.type)
                .withBy(item.by)
                .withUrl(item.url)
                .withDescendants(item.descendants)
                .withScore(item.score)
                .withKids(item.kids)
                .withTime(item.time)
                .build();
    }
}
