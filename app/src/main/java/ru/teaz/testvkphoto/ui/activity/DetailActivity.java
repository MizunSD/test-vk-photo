package ru.teaz.testvkphoto.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.ui.fragment.DetailPhotoFragment;
import ru.teaz.testvkphoto.ui.interfaces.DetailPhotoView;

public class DetailActivity extends AppCompatActivity implements DetailPhotoView {

    private static final String PHOTOS = "Photos";
    private static final String POSITION = "Position";

    @InjectView(R.id.pager)
    ViewPager viewPager;

    @InjectView(R.id.current_position)
    TextView tvPosition;

    @InjectView(R.id.total_count)
    TextView tvTotalCount;

    @InjectView(R.id.photo_likes_count)
    TextView tvLikesCount;

    @InjectView(R.id.photo_comments_count)
    TextView tvCommentsCount;

    @InjectView(R.id.detail_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.photo_text)
    TextView tvPhotoText;

    private int mPhotoCount;
    private int mPosition;
    private VKPhotoArray mVkPhotos;
    private PhotoPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        mVkPhotos = getIntent().getParcelableExtra(PHOTOS);
        if (mVkPhotos != null) {
            setTotalCount(mVkPhotos.getCount());
            fillData(mVkPhotos.get(getIntent().getIntExtra(POSITION, 0)));

            mAdapter = new PhotoPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mAdapter);
            viewPager.setCurrentItem(mPosition);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    fillData(mVkPhotos.get(position));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private class PhotoPagerAdapter extends FragmentStatePagerAdapter {
        public PhotoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailPhotoFragment.newInstance(mVkPhotos.get(position));
        }

        @Override
        public int getCount() {
            return mPhotoCount;
        }
    }

    @Override
    public void setTotalCount(int count) {
        mPhotoCount = count;
        tvTotalCount.setText(String.valueOf(count));
    }

    @Override
    public void setCurrentPosition(int position) {
        mPosition = position;
        tvPosition.setText(String.valueOf(position + 1));
    }

    @Override
    public void setLikesCount(int count) {
        tvLikesCount.setText(String.valueOf(count));
    }

    @Override
    public void setCommentsCount(int count) {
        tvCommentsCount.setText(String.valueOf(count));
    }

    @Override
    public void setPhotoText(String text) {
        tvPhotoText.setText(text);
    }

    @Override
    public void fillData(VKApiPhoto photo) {
        setCurrentPosition(mVkPhotos.indexOf(photo));
        setLikesCount(photo.likes);
        setCommentsCount(photo.comments);
        setPhotoText(photo.text);
    }

}
