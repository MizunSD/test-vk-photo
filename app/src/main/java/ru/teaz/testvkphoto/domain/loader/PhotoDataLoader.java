package ru.teaz.testvkphoto.domain.loader;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;

public interface PhotoDataLoader {

    void loadPhotoData();

    interface LoadCallback {

        void setPhotoCount(int count);
        void onComplete(VKPhotoArray photos);
        void onError(VKError error);
    }
}
