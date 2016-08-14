package ru.teaz.testvkphoto.domain.image;

import android.content.Context;

import java.io.File;

public class DiskCache {

    private static volatile DiskCache instance;
    private static Context mContext;
    private static File cacheDir;

    public static DiskCache init(Context context) {
        DiskCache localInstance = instance;
        if (localInstance == null) {
            synchronized (DiskCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DiskCache();
                    mContext = context;
                    cacheDir = mContext.getFilesDir();
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs();
                    }
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

    public File getFile(String name) {
        String cachedName = String.valueOf(name.hashCode());
        File file = new File(cacheDir, cachedName);
        return file;
    }

}
