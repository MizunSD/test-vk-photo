package ru.teaz.testvkphoto.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.vk.sdk.api.model.VKPhotoArray;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.presenter.PhotoGridPresenter;
import ru.teaz.testvkphoto.ui.adapter.GridItemDecoration;
import ru.teaz.testvkphoto.ui.adapter.PhotoGridAdapter;
import ru.teaz.testvkphoto.ui.interfaces.PhotoGridView;

public class ContentActivity extends BaseActivity<PhotoGridView, PhotoGridPresenter> implements PhotoGridView{

    private GridLayoutManager gridLayoutManager;
    private PhotoGridAdapter adapter;

    @InjectView(R.id.grid_recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.content_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.addItemDecoration(new GridItemDecoration(10, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (savedInstanceState != null) {
            setVkPhotoArray((VKPhotoArray) savedInstanceState.getParcelable(PHOTOS));
        } else {
            getPresenter().loadPhotos(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if (adapter != null) {
            state.putParcelable(PHOTOS, adapter.getArray());
        }
    }

    @Override
    public void setVkPhotoArray(VKPhotoArray array) {
        adapter = new PhotoGridAdapter(this, array);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPhotoClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(PHOTOS, adapter.getArray());
        intent.putExtra(POSITION, position);
        startActivity(intent);
    }
}
