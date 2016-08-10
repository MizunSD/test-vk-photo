package ru.teaz.testvkphoto.domain.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.teaz.testvkphoto.utils.Logger;

public class DiskCache {

    private static volatile DiskCache instance;
    private static Context mContext;

    public static DiskCache init(Context context) {
        DiskCache localInstance = instance;
        if (localInstance == null) {
            synchronized (DiskCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DiskCache();
                    mContext = context;
                }
            }
        }

        return localInstance;
    }

    public static DiskCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DiskCache isn't init");
        }
        return instance;
    }

    public void addBitmapToCache(String name, Bitmap bitmap) {
        String cachedName = Uri.parse(name).getLastPathSegment();
        try {
            File file = new File(mContext.getCacheDir(), cachedName);
            if (!file.exists()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Logger.d(cachedName + " saved on disk cache");
            }
        } catch (FileNotFoundException e) {
            Logger.e("File " + cachedName + " not found");
        } catch (IOException e) {
            Logger.e("File " + cachedName + " IOException");
        }
    }

    @Nullable
    public Bitmap getBitmapFromCache(String name) {
        String cachedName = Uri.parse(name).getLastPathSegment();
        File file = new File(mContext.getCacheDir(), cachedName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            return null;
        }
    }

}
