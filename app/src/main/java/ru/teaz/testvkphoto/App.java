package ru.teaz.testvkphoto;

import android.app.Application;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import ru.teaz.testvkphoto.domain.image.DiskCache;
import ru.teaz.testvkphoto.utils.Logger;

public class App extends Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Logger.e("Token is null");
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(getApplicationContext());
        DiskCache.init(getApplicationContext());
    }
}
