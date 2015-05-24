package br.com.helabs.flickr.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.helabs.flickr.R;
import br.com.helabs.flickr.listeners.OnItemClickListener;
import br.com.helabs.flickr.models.RecentPhoto;

public class ListPhotosAdapter extends RecyclerView.Adapter<ListPhotosAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<RecentPhoto> mPhotos = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    private int mItemLayoutResId = R.layout.list_item;

    public ListPhotosAdapter(Context context, List<RecentPhoto> photos) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPhotos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(mItemLayoutResId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populate(holder, mPhotos.get(position));
    }

    void populate(final ViewHolder holder, RecentPhoto currentPhoto) {
        if (currentPhoto.getTitle() != null && !currentPhoto.getTitle().isEmpty()) {
            holder.mTitle.setText(currentPhoto.getTitle());
            holder.mTitle.setVisibility(View.VISIBLE);
        } else {
            holder.mTitle.setVisibility(View.GONE);
        }

        if (currentPhoto.getTags() != null && !currentPhoto.getTags().isEmpty()) {
            holder.mTags.setText(currentPhoto.getTagsTagged());
            holder.mTags.setVisibility(View.VISIBLE);
        } else {
            holder.mTags.setVisibility(View.GONE);
        }

        if (currentPhoto.getOwnerName() != null && !currentPhoto.getOwnerName().isEmpty()) {
            holder.mOwnerName.setText(currentPhoto.getOwnerName());
            holder.mOwnerName.setVisibility(View.VISIBLE);
        } else {
            holder.mOwnerName.setVisibility(View.GONE);
        }

        if (currentPhoto.getThumbURL() != null && !currentPhoto.getThumbURL().isEmpty()) {
            Picasso.with(mContext)
                    .load(currentPhoto.getThumbURL())
                    .fit()
                    .into(holder.mImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Animation in = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
                            holder.mImage.startAnimation(in);
                        }

                        @Override
                        public void onError() {

                        }
                    });


        } else {
            Picasso.with(mContext)
                    .cancelRequest(holder.mImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItemLayoutResId;
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    public void addPhotos(List<RecentPhoto> photos) {
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }

    public void clearPhotos() {
        mPhotos.clear();
        notifyDataSetChanged();
    }

    public List<RecentPhoto> getPhotos() {
        return mPhotos;
    }

    public RecentPhoto getPhoto(int position) {
        return mPhotos.get(position);
    }

    public void setItemLayout(@LayoutRes int itemLayoutResId) {
        if (mItemLayoutResId != itemLayoutResId) {
            mItemLayoutResId = itemLayoutResId;

            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener */ {

        MaterialRippleLayout mRippleView;
        RoundedImageView mImage;
        TextView mTitle;
        TextView mTags;
        TextView mOwnerName;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (RoundedImageView) itemView.findViewById(R.id.img_thumbs);
            mTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mTags = (TextView) itemView.findViewById(R.id.txt_tags);
            mOwnerName = (TextView) itemView.findViewById(R.id.txt_owner_name);
            mRippleView = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
            mRippleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mView, getPosition()); //OnItemClickListener mItemClickListener;

                }
            });

        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
