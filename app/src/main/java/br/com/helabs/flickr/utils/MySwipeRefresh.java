package br.com.helabs.flickr.utils;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MySwipeRefresh extends SwipeRefreshLayout {
    private boolean mMeasured = false;
    private boolean mPreMeasureRefreshing = false;

    private RecyclerView recyclerView;

    public MySwipeRefresh(Context context) {
        super(context);
    }

    public MySwipeRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setView(RecyclerView view) {
        this.recyclerView = view;
    }

    @Override
    public boolean canChildScrollUp() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() != 0;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mMeasured) {
            mMeasured = true;
            setRefreshing(mPreMeasureRefreshing);
        }
    }


    @Override
    public void setRefreshing(boolean refreshing) {
        if (mMeasured) {
            super.setRefreshing(refreshing);
        } else {
            mPreMeasureRefreshing = refreshing;
        }
    }
}
