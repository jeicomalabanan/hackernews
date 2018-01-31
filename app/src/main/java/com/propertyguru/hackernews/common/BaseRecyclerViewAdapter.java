package com.propertyguru.hackernews.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<M> dataList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setList(@NonNull List<M> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }

    public void addToList(@NonNull List<M> newDataList) {
        dataList.addAll(newDataList);
        notifyItemRangeInserted(dataList.size(), newDataList.size());
    }

    public void clearList() {
        dataList.clear();
        notifyDataSetChanged();
    }
}
