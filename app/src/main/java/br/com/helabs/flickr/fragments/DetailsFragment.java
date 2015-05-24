package br.com.helabs.flickr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

import br.com.helabs.flickr.R;
import br.com.helabs.flickr.adapters.ListCommentsAdapter;
import br.com.helabs.flickr.api.FlickApi;
import br.com.helabs.flickr.api.GetPhotoComments;
import br.com.helabs.flickr.api.GetPhotoInfo;
import br.com.helabs.flickr.api.GetPhotoSizes;
import br.com.helabs.flickr.models.Comment;
import br.com.helabs.flickr.utils.DividerItemDecoration;
import br.com.helabs.flickr.utils.MyCache;
import br.com.helabs.flickr.utils.RecyclerViewLinearLayout;
import br.com.helabs.flickr.utils.Utils;


@EFragment(R.layout.details_fragment)
public class DetailsFragment extends Fragment {

    @RestService
    FlickApi myRestClient; // Inject it

    @ViewById(R.id.img_main_photo)
    ImageView mMainPhoto;

    @ViewById(R.id.img_owner)
    ImageView mOwnerPhoto;

    @ViewById(R.id.txt_title)
    TextView mTitle;

    @ViewById(R.id.owner_name)
    TextView mOwnerName;

    @ViewById(R.id.txt_comments)
    TextView mComments;

    @ViewById(R.id.progress_bar)
    ProgressBar mProgressBar;

    @ViewById(R.id.list_comments)
    RecyclerView mCommentsList;


    private static final String PHOTO_ID = "photo.id";
    private static final String PHOTO_TITLE = "photo.title";
    private static final String PHOTO_OWNER = "photo.owner";
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    private Long photoId;
    private String photoTitle;
    private String photoOwner;
    private GetPhotoInfo photoInfo;
    private GetPhotoSizes sizes;
    private String urlPhoto;
    private GetPhotoComments photoComments;
    private LinearLayoutManager mLayoutManager;
    private ListCommentsAdapter mAdapter;

    private class ImageLoadedCallback implements Callback {
        final ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {
        }

        @Override
        public void onError() {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        this.photoId = b.getLong(PHOTO_ID);
        this.photoTitle = b.getString(PHOTO_TITLE);
        this.photoOwner = b.getString(PHOTO_OWNER);
    }

    public static DetailsFragment newInstance(Long photoId, String photoTitle, String photoOwner) {
        DetailsFragment_ f = new DetailsFragment_();
        Bundle b = new Bundle();
        b.putLong(PHOTO_ID, photoId);
        b.putString(PHOTO_TITLE, photoTitle);
        b.putString(PHOTO_OWNER, photoOwner);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @AfterViews
    public void init() {

        mProgressBar.setVisibility(View.VISIBLE);

        initCommentList();
        initData();

        if (photoInfo == null && sizes == null)
            getPhotoBackground();
        else
            showResult(sizes, photoInfo, photoComments);
    }

    private void initData() {
        if (photoTitle != null) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.photo_details);
            mTitle.setText(photoTitle);
            mTitle.setVisibility(View.VISIBLE);
        } else {
            mTitle.setVisibility(View.GONE);
        }

        if (photoOwner != null) {
            mOwnerName.setText(photoOwner);
            mOwnerName.setVisibility(View.VISIBLE);
        } else {
            mOwnerName.setVisibility(View.GONE);
        }
    }

    private void initCommentList() {

        mLayoutManager = new RecyclerViewLinearLayout(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new ListCommentsAdapter(getActivity(), new ArrayList<Comment>());
        mCommentsList.setLayoutManager(mLayoutManager);
        mCommentsList.setAdapter(mAdapter);
        mCommentsList.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mCommentsList.addItemDecoration(itemDecoration);

    }

    @Background
    void getPhotoBackground() {
        // photoId = (long) 109722179;
        String keyCachePhoto = Utils.DETAIL_PHOTO_CACHE + Long.toString(photoId);
        String keyCachePhotoSizes = Utils.DETAIL_SIZES_CACHE + Long.toString(photoId);
        String keyCachePhotoComments = Utils.DETAIL_COMMENTS_CACHE + Long.toString(photoId);

        try {
            if (!MyCache.has(keyCachePhoto, getActivity())) {
                photoInfo = myRestClient.getPhotoInfo(getString(R.string.flickr_api_key), photoId);
                MyCache.add(keyCachePhoto, photoInfo, getActivity());

            }
            if (!MyCache.has(keyCachePhotoSizes, getActivity())) {
                sizes = myRestClient.getPhotoSizes(getString(R.string.flickr_api_key), photoId);
                MyCache.add(keyCachePhotoSizes, sizes, getActivity());
            }
            if (!MyCache.has(keyCachePhotoComments, getActivity())) {
                photoComments = myRestClient.getPhotoComments(getString(R.string.flickr_api_key), photoId);
                MyCache.add(keyCachePhotoComments, photoComments, getActivity());
            }
            showResult(
                    (GetPhotoSizes) MyCache.get(keyCachePhotoSizes, getActivity()),
                    (GetPhotoInfo) MyCache.get(keyCachePhoto, getActivity()),
                    (GetPhotoComments) MyCache.get(keyCachePhotoComments, getActivity())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void showResult(GetPhotoSizes sizes, GetPhotoInfo info, GetPhotoComments comments) {
        if (sizes != null && info != null && comments != null) {
            if (sizes.getSizes() != null) {
                if (sizes.getSizes().getSize().size() > 6)
                    urlPhoto = sizes.getSizes().getSize().get(6).getSource();
                else
                    urlPhoto = sizes.getSizes().getSize().get(5).getSource();
            }

            if (photoTitle.equals(""))
                mTitle.setText(info.getPhoto().getTitle().getContent());

            if (photoOwner.equals(""))
                mOwnerName.setText(info.getPhoto().getOwner().getRealname());

            Picasso.with(getActivity())
                    .load(urlPhoto)
//                    .fit()
                    .into(mMainPhoto, new ImageLoadedCallback(mProgressBar) {
                        @Override
                        public void onSuccess() {
                            if (mProgressBar != null) {
                                mProgressBar.setVisibility(View.GONE);
                                animatePhoto(mMainPhoto);
                            }
                        }

                    });
            mComments.setText(info.getPhoto().getComments().getContent() + " " + getString(R.string.comments));
            Picasso.with(getActivity())
                    .load(info.getPhoto().getOwnerPic())
                    .placeholder(R.drawable.ic_account_white_48dp)
                    .fit()
                    .into(mOwnerPhoto);

            if (comments.getComments() != null && comments.getComments().getComment() != null)
                mAdapter.addComments(comments.getComments().getComment());
        }
    }

    private void animatePhoto(ImageView mMainPhoto) {
        mMainPhoto.setScaleY(0);
        mMainPhoto.setScaleX(0);
        mMainPhoto.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(300)
                .setInterpolator(INTERPOLATOR)
                .setStartDelay(0)
                .start();

    }

}
