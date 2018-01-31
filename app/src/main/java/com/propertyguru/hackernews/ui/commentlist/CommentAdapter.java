package com.propertyguru.hackernews.ui.commentlist;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.propertyguru.hackernews.R;
import com.propertyguru.hackernews.common.BaseRecyclerViewAdapter;
import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.databinding.HolderCommentBinding;

public class CommentAdapter extends BaseRecyclerViewAdapter<Comment, CommentAdapter.CommentHolder> {

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HolderCommentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.holder_comment,
                parent,
                false);
        return new CommentHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.bindData(holder, dataList.get(position));
        holder.binding.executePendingBindings();
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        final HolderCommentBinding binding;

        public CommentHolder(HolderCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(CommentHolder holder, Comment comment) {
            holder.binding.tvComment.setMovementMethod(LinkMovementMethod.getInstance());

            // set data
            holder.binding.setComment(comment);
            // remove replies
            holder.binding.llReplies.removeAllViews();

            int space = (int) (TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16,
                    holder.itemView.getContext().getResources().getDisplayMetrics()));

            if (comment.getDepth() == 0) {
                holder.binding.llContent.setPadding(space, space, space, space);
            } else {
                holder.binding.llContent.setPadding(space * comment.getDepth(), 0, 0 , 0);
            }

            for (Comment childComment : comment.getChildList()) {
                HolderCommentBinding binding = DataBindingUtil.inflate(
                        LayoutInflater.from(holder.binding.getRoot().getContext()),
                        R.layout.holder_comment,
                        holder.binding.llReplies,
                        false);
                holder.binding.llReplies.addView(binding.getRoot());

                CommentHolder childHolder = new CommentHolder(binding);
                bindData(childHolder, childComment);
            }
        }
    }
}
