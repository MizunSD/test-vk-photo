package ru.teaz.testvkphoto.domain.image;

import android.graphics.Bitmap;
import android.util.LruCache;

import ru.teaz.testvkphoto.utils.Logger;

public class MemoryCache {

    private static volatile MemoryCache instance;
    private static LruCache<String, Bitmap> mCache;

    public static MemoryCache getInstance() {
        MemoryCache localInstance = instance;
        if (localInstance == null) {
            synchronized (MemoryCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MemoryCache();
                    final int memory = (int) (Runtime.getRuntime().maxMemory() / 1024);
                    final int cacheSize = memory / 4;
                    mCache = new LruCache<String, Bitmap>(cacheSize){
                        @Override
                        protected int sizeOf(String key, Bitmap bitmap) {
                            return bitmap.getByteCount() / 1024;
                        }
                    };
                }
            }
        }
        return localInstance;
    }

    public void addBitmapToCache(String key, Bitmap value) {
        if (getBitmapFromCache(key) == null) {
            mCache.put(key, value);
            Logger.d(key + " saved on memory cache");
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        return mCache.get(key);
    }

}
