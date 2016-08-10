package ru.teaz.testvkphoto.domain.net;

import android.app.Activity;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public interface ServerApi {

    void login(Activity activity, String... scope);

    void getPhotoList(VKParameters vkParameters, VKRequest.VKRequestListener vkRequestListener);

}
