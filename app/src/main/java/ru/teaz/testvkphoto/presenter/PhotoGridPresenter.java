package ru.teaz.testvkphoto.presenter;

import android.content.Context;

public interface PhotoGridPresenter extends MvpPresenter {

    void loadPhotos(Context context);
}
