package ru.teaz.testvkphoto.domain.net;

import ru.teaz.testvkphoto.domain.net.impl.VkSdkApi;

public class ServerApiFactory {

    private static VkSdkApi mVkSdkApi;

    public static ServerApi create() {
        if (mVkSdkApi == null) {
            mVkSdkApi = new VkSdkApi();
        }

        return mVkSdkApi;
    }
}
