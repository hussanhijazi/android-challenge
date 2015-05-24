package br.com.helabs.flickr.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.helabs.flickr.R;
import br.com.helabs.flickr.models.Comment;


public class ListCommentsAdapter extends RecyclerView.Adapter<ListCommentsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Comment> mComments = new ArrayList<>();
    private Context mContext;

    private int mItemLayoutResId = R.layout.list_item_comments;

    public ListCommentsAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(mItemLayoutResId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populate(holder, mComments.get(position));
    }

    void populate(ViewHolder holder, Comment comment) {

        java.util.Date date = new java.util.Date(Long.parseLong(comment.getDateCreate()) * 1000);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String commentDate = df.format(date);
        holder.mDate.setText(commentDate);
        holder.mOwnerName.setText(comment.getAuthorName());
        holder.mComment.setText(comment.getText());
    }

    @Override
    public int getItemViewType(int position) {
        return mItemLayoutResId;
    }

    @Override
    public int getItemCount() {
        return mComments == null ? 0 : mComments.size();
    }

    public void addComments(List<Comment> comments) {
        mComments.addAll(comments);
        notifyDataSetChanged();
    }

    public void clearComments() {
        mComments.clear();
        notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public Comment getComment(int position) {
        return mComments.get(position);
    }

    public void setItemLayout(@LayoutRes int itemLayoutResId) {
        if (mItemLayoutResId != itemLayoutResId) {
            mItemLayoutResId = itemLayoutResId;

            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mDate;
        TextView mOwnerName;
        TextView mComment;

        public ViewHolder(View itemView) {
            super(itemView);

            mDate = (TextView) itemView.findViewById(R.id.txt_date);
            mOwnerName = (TextView) itemView.findViewById(R.id.txt_owner_name);
            mComment = (TextView) itemView.findViewById(R.id.txt_comment);
        }
    }
}