package ru.teaz.testvkphoto.ui.interfaces;

import android.support.annotation.StringRes;

import com.vk.sdk.api.model.VKPhotoArray;

public interface PhotoGridView extends MvpView{

    void showError(@StringRes int errId);
    void setPhotoCount(int count);
    void setVkPhotoArray(VKPhotoArray array);

}
