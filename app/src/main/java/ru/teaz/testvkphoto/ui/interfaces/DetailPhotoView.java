package ru.teaz.testvkphoto.ui.interfaces;

import com.vk.sdk.api.model.VKApiPhoto;

public interface DetailPhotoView {

    void setTotalCount(int count);
    void setCurrentPosition(int position);
    void setLikesCount(int count);
    void setCommentsCount(int count);
    void setPhotoText(String text);
    void fillData(VKApiPhoto photo);
}
