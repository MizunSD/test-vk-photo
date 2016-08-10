package ru.teaz.testvkphoto.domain.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.teaz.testvkphoto.domain.image.DiskCache;
import ru.teaz.testvkphoto.domain.image.MemoryCache;
import ru.teaz.testvkphoto.utils.Logger;

public class LoadBitmapTask extends AsyncTask<String, Void, Bitmap>{

    private final WeakReference<ImageView> imageViewReference;
    private String source;
    private MemoryCache memoryCache = MemoryCache.getInstance();
    private DiskCache diskCache = DiskCache.getInstance();

    public LoadBitmapTask(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        source = params[0];
        Logger.d(Thread.currentThread().getName());
        return getBitmap(source);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap getBitmap(String source) {
        Bitmap result = memoryCache.getBitmapFromCache(source);

        if (result == null) {
            result = diskCache.getBitmapFromCache(source);
            if (result == null) {
                result = loadFromNetwork(source);
            } else {
                Logger.d(source + " found on disk cache");
            }
        } else {
            Logger.d(source + " found on memory cache");
        }

        return result;
    }

    private Bitmap loadFromNetwork(String source) {
        Bitmap result = null;
        try {
            URL url = new URL(source);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            result = BitmapFactory.decodeStream(inputStream);
            memoryCache.addBitmapToCache(source, result);
            diskCache.addBitmapToCache(source, result);
        } catch (MalformedURLException e) {
            Logger.e("Bad url: " + source +"; e: " + e.getMessage());
        } catch (IOException e) {
            Logger.e("Error on open connection: " + source + "; e: " + e.getMessage());
        }
        return result;
    }
}
