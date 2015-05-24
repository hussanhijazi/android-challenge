package br.com.helabs.flickr.activities;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.helabs.flickr.R;
import br.com.helabs.flickr.fragments.DetailsFragment_;
import br.com.helabs.flickr.fragments.ListFragment;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements ListFragment.RecentPicturesSelectedListener, FragmentManager.OnBackStackChangedListener {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.txt_title)
    TextView mTitle;

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private FragmentManager mFragmentManager;
    private boolean pendingIntroAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        mFragmentManager = getSupportFragmentManager();
        ListFragment mListFragment;
        if (savedInstanceState == null) {
            mListFragment = ListFragment.newInstance();
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, mListFragment, getString(R.string.list_fragment))
                    .commit();
        }

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
    }

    @AfterViews
    public void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });

        shouldDisplayHomeUp();
    }

    public void onPhotoSelected(Long photoId, String photoTitle, String photoOwner) {
        DetailsFragment_ photoFrag = (DetailsFragment_)
                mFragmentManager.findFragmentByTag(getString(R.string.details_fragment));
        if (photoFrag == null) {
            mTitle.setText(getString(R.string.photo_details));
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, DetailsFragment_.newInstance(photoId, photoTitle, photoOwner), getString(R.string.details_fragment));
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(getString(R.string.details_fragment));
            transaction.commit();

        }

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        if (canback) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            if (upArrow != null) {
                upArrow.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
            }
            mToolbar.setNavigationIcon(upArrow);
        }
        else {
            mToolbar.setNavigationIcon(null);
            mTitle.setText(getString(R.string.recent_photos));
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }
    private void startIntroAnimation() {
        int actionbarSize = (int) (56 * Resources.getSystem().getDisplayMetrics().density);
        mToolbar.setTranslationY(-actionbarSize);
        mToolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mTitle.setTranslationY(-actionbarSize);
        mTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);

    }
}
