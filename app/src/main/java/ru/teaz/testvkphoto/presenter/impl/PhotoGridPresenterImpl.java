package ru.teaz.testvkphoto.presenter.impl;

import android.content.Context;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;

import ru.teaz.testvkphoto.domain.loader.DataLoaderFactory;
import ru.teaz.testvkphoto.domain.loader.PhotoDataLoader;
import ru.teaz.testvkphoto.presenter.PhotoGridPresenter;
import ru.teaz.testvkphoto.ui.interfaces.MvpView;
import ru.teaz.testvkphoto.ui.interfaces.PhotoGridView;
import ru.teaz.testvkphoto.utils.Logger;

public class PhotoGridPresenterImpl implements PhotoGridPresenter, PhotoDataLoader.LoadCallback{

    private PhotoGridView mView;
    private PhotoDataLoader mPhotoDataLoader = DataLoaderFactory.createPhotoDataLoader(this);

    @Override
    public void bindView(MvpView view) {
        if (view instanceof PhotoGridView) {
            mView = (PhotoGridView) view;
        } else {
            throw new IllegalArgumentException("View must be instance of PhotoGridView");
        }
    }

    @Override
    public void loadPhotos(Context context) {
        mPhotoDataLoader.loadPhotoData(context);
    }

    @Override
    public void onComplete(VKPhotoArray photos) {
        mView.setVkPhotoArray(photos);
    }

    @Override
    public void onError(VKError error) {
        Logger.e("loadPhotoData error: " + error.errorMessage);
    }

}
