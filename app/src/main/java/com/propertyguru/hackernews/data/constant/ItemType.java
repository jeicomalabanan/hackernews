package com.propertyguru.hackernews.data.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ItemType {
    public static final String JOB = "job";
    public static final String STORY = "story";
    public static final String COMMENT = "comment";
    public static final String POLL = "poll";
    public static final String POLL_OPT = "pollopt";

    @StringDef({
            JOB,
            STORY,
            COMMENT,
            POLL,
            POLL_OPT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Constant {}
}
