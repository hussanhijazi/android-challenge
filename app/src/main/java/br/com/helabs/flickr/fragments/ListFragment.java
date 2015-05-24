package br.com.helabs.flickr.fragments;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

import br.com.helabs.flickr.R;
import br.com.helabs.flickr.activities.MainActivity;
import br.com.helabs.flickr.adapters.ListPhotosAdapter;
import br.com.helabs.flickr.api.FlickApi;
import br.com.helabs.flickr.api.GetRecentPhotos;
import br.com.helabs.flickr.listeners.OnItemClickListener;
import br.com.helabs.flickr.models.RecentPhoto;
import br.com.helabs.flickr.models.RecentsPhotos;
import br.com.helabs.flickr.utils.EndlessRecyclerViewOnScrollListener;
import br.com.helabs.flickr.utils.MyCache;
import br.com.helabs.flickr.utils.MySwipeRefresh;
import br.com.helabs.flickr.utils.Utils;


@EFragment(R.layout.list_fragment)
public class ListFragment extends Fragment {

    private EndlessRecyclerViewOnScrollListener mEndlessRecyclerViewListener;

    public interface RecentPicturesSelectedListener {
        void onPhotoSelected(Long id, String title, String ownerName);
    }


    @RestService
    FlickApi myRestClient; // Inject it

    @ViewById(R.id.list_photos)
    RecyclerView mPhotoList;

    @ViewById(R.id.swipe_container)
    MySwipeRefresh mSwipeRefresh;

    @ViewById(R.id.progress_bar)
    ProgressBar mProgressBar;

    @ViewById(R.id.lyt_progress_bar)
    LinearLayout mLayoutProgressBar;


    private LinearLayoutManager mLayoutManager;
    private ListPhotosAdapter mAdapter;
    private RecentsPhotos items;

    private RecentPicturesSelectedListener mCallback;
    private List<RecentPhoto> itemsTotal = new ArrayList<>();


    private static final int PER_PAGE = 25;
    private static int mPage = 1;

    Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            mSwipeRefresh.setRefreshing(true);
        }
    };


    void initStartSwipeRefresh()
    {
    //    mSwipeRefresh.post(mRefreshRunnable);
    }

    public static ListFragment newInstance() {
        return new ListFragment_();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (RecentPicturesSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RecentPicturesSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @AfterViews
    public void init() {

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.recent_photos);

        initSwipeRefresh();

        initPhotoList();

        startLoadMoreAnimation();
        getItemsInBackground(false, mPage);

    }

    private void initPhotoList() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ListPhotosAdapter(getActivity(), new ArrayList<RecentPhoto>());
        mEndlessRecyclerViewListener = new EndlessRecyclerViewOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                startLoadMoreAnimation();
                mPage = current_page;
                getItemsInBackground(false, mPage);

            }
        };


        mPhotoList.setLayoutManager(mLayoutManager);
        mPhotoList.setHasFixedSize(true);
        mPhotoList.setOnScrollListener(mEndlessRecyclerViewListener);
        mPhotoList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mCallback.onPhotoSelected(mAdapter.getPhoto(position).getId(), mAdapter.getPhoto(position).getTitle(), mAdapter.getPhoto(position).getOwnerName());
            }
        });
    }

    private void initSwipeRefresh() {
        mSwipeRefresh.setColorScheme(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        mSwipeRefresh.setView(mPhotoList);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getItemsInBackground(true, mPage);

            }
        });
    }

    @Background
    void getItemsInBackground(boolean clearCache, int page) {
        items = getItems(clearCache, page);
        showResult(items, clearCache);
    }

    @UiThread
    void showResult(RecentsPhotos items, boolean clearCache) {

        if (clearCache) {
            mPhotoList.setOnScrollListener(new EndlessRecyclerViewOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    startLoadMoreAnimation();
                    mPage = current_page;
                    getItemsInBackground(false, mPage);

                }
            });
            mAdapter.clearPhotos();
        }

        mAdapter.addPhotos(items.getPhoto());
        mSwipeRefresh.setRefreshing(false);
        mLayoutProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onPause() {
        mPage = 1;
        super.onPause();
    }

    @Click(R.id.btn_commented_photo)
    void btnCommentedPhoto() {
        ((MainActivity) getActivity()).onPhotoSelected(Long.valueOf(109722179), "", "");
    }

    public RecentsPhotos getItems(boolean clearCache, int page) {

        String key = Utils.LIST_CACHE + Integer.toString(page) + Integer.toString(PER_PAGE);

        if (clearCache)
            MyCache.clearAll(getActivity());

        if (!MyCache.has(key, getActivity())) {
            GetRecentPhotos aList = myRestClient.getRecentPhotos(getString(R.string.flickr_api_key), PER_PAGE, page);
            MyCache.add(key, aList.getPhotos(), getActivity());
            return aList.getPhotos();
        }

        return (RecentsPhotos) MyCache.get(key, getActivity());
    }

    private void startLoadMoreAnimation() {
        mLayoutProgressBar.setVisibility(View.VISIBLE);
        int actionbarSize = (int) (56 * Resources.getSystem().getDisplayMetrics().density);
        mLayoutProgressBar.setTranslationY(actionbarSize);
        mLayoutProgressBar.animate()
                .translationY(0)
                .setDuration(450)
                .setStartDelay(0);
    }

}
