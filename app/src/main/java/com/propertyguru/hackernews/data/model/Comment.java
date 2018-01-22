package com.propertyguru.hackernews.data.model;

import java.util.Date;
import java.util.List;

public class Comment {
    private long id;
    private String by;
    private Date time;
    private String text;
    private int depth;
    private List<Comment> childList;

    public Comment() {
    }

    private Comment(Builder builder) {
        id = builder.id;
        by = builder.by;
        time = builder.time;
        text = builder.text;
        depth = builder.depth;
        childList = builder.childList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Comment> getChildList() {
        return childList;
    }

    public void setChildList(List<Comment> childList) {
        this.childList = childList;
    }

    public static final class Builder {
        private long id;
        private String by;
        private Date time;
        private String text;
        private int depth;
        private List<Comment> childList;

        private Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withBy(String val) {
            by = val;
            return this;
        }

        public Builder withTime(Date val) {
            time = val;
            return this;
        }

        public Builder withText(String val) {
            text = val;
            return this;
        }

        public Builder withDepth(int val) {
            depth = val;
            return this;
        }

        public Builder withChildList(List<Comment> val) {
            childList = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
