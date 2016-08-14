package ru.teaz.testvkphoto.ui.interfaces;

import com.vk.sdk.api.model.VKPhotoArray;

public interface PhotoGridView extends MvpView{

    void setVkPhotoArray(VKPhotoArray array);
    void onPhotoClicked(int position);

}
