package com.propertyguru.hackernews.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaginatedList<T> {
    @SerializedName("total")
    private long total;
    @SerializedName("per_page")
    private long perPage;
    @SerializedName("current_page")
    private long currentPage;
    @SerializedName("last_page")
    private long lastPage;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    @SerializedName("from")
    private long from;
    @SerializedName("to")
    private long to;
    @SerializedName("data")
    private List<T> dataList = new ArrayList<>();

    public long getTotal() {
        return total;
    }

    public long getPerPage() {
        return perPage;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public long getLastPage() {
        return lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public List<T> getDataList() {
        return dataList;
    }
}
