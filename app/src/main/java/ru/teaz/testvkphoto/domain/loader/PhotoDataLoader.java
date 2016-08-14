package ru.teaz.testvkphoto.domain.loader;

import android.content.Context;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;

public interface PhotoDataLoader {

    void loadPhotoData(Context context);

    interface LoadCallback {

        void onComplete(VKPhotoArray photos);
        void onError(VKError error);
    }
}
