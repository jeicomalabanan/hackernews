package com.propertyguru.hackernews.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Item {
    @SerializedName("id")
    public long id;
    @SerializedName("deleted")
    public boolean isDeleted;
    @SerializedName("type")
    public String type;
    @SerializedName("by")
    public String by;
    @SerializedName("time")
    public Date time;
    @SerializedName("text")
    public String text;
    @SerializedName("dead")
    public boolean isDead;
    @SerializedName("parent")
    public long parent;
    @SerializedName("poll")
    public long poll;
    @SerializedName("kids")
    public List<Long> kids;
    @SerializedName("url")
    public String url;
    @SerializedName("score")
    public int score;
    @SerializedName("title")
    public String title;
    @SerializedName("parts")
    public List<Long> parts;
    @SerializedName("descendants")
    public int descendants;
}
