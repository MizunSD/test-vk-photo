package ru.teaz.testvkphoto.ui.activity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vk.sdk.api.model.VKPhotoArray;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.presenter.PhotoGridPresenter;
import ru.teaz.testvkphoto.ui.adapter.PhotoGridAdapter;
import ru.teaz.testvkphoto.ui.interfaces.PhotoGridView;
import ru.teaz.testvkphoto.utils.Logger;

public class ContentActivity extends BaseActivity<PhotoGridView, PhotoGridPresenter> implements PhotoGridView{

    private GridLayoutManager gridLayoutManager;

    @InjectView(R.id.grid_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);

        getPresenter().loadPhotos();
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
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
    public void showError(@StringRes int errId) {

    }

    @Override
    public void setPhotoCount(int count) {
        Logger.d("photo count: " + count);
    }

    @Override
    public void setVkPhotoArray(VKPhotoArray array) {
        PhotoGridAdapter adapter = new PhotoGridAdapter(this, array);
        recyclerView.setAdapter(adapter);
    }
}
