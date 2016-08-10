package ru.teaz.testvkphoto;

import android.app.Application;

import com.vk.sdk.VKSdk;

import ru.teaz.testvkphoto.domain.image.DiskCache;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        DiskCache.init(getApplicationContext());
    }
}
