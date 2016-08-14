package ru.teaz.testvkphoto.domain.net.impl;

import android.app.Activity;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import ru.teaz.testvkphoto.domain.net.ServerApi;

public class VkSdkApi implements ServerApi{

    @Override
    public void login(Activity activity, String... scope) {
        VKSdk.login(activity, scope);
    }

    @Override
    public void getPhotoList(VKParameters vkParameters, VKRequest.VKRequestListener listener) {
        VKRequest request = new VKRequest("photos.getAll", vkParameters);
        request.executeWithListener(listener);
    }
}
