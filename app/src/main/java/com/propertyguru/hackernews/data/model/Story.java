package com.propertyguru.hackernews.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Story {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("by")
    private String by;
    @SerializedName("url")
    private String url;
    @SerializedName("descendants")
    private int descendants;
    @SerializedName("score")
    private int score;
    @SerializedName("kids")
    private List<Long> kids;
    @SerializedName("time")
    private Date time;

    private Story(Builder builder) {
        id = builder.id;
        title = builder.title;
        type = builder.type;
        by = builder.by;
        url = builder.url;
        descendants = builder.descendants;
        score = builder.score;
        kids = builder.kids;
        time = builder.time;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Long> getKids() {
        return kids;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static final class Builder {
        private long id;
        private String title;
        private String type;
        private String by;
        private String url;
        private int descendants;
        private int score;
        private List<Long> kids;
        private Date time;

        private Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withBy(String val) {
            by = val;
            return this;
        }

        public Builder withUrl(String val) {
            url = val;
            return this;
        }

        public Builder withDescendants(int val) {
            descendants = val;
            return this;
        }

        public Builder withScore(int val) {
            score = val;
            return this;
        }

        public Builder withKids(List<Long> val) {
            kids = val;
            return this;
        }

        public Builder withTime(Date val) {
            time = val;
            return this;
        }

        public Story build() {
            return new Story(this);
        }
    }
}
