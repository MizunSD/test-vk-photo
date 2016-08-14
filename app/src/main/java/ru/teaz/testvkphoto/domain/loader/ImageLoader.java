package ru.teaz.testvkphoto.domain.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.teaz.testvkphoto.domain.image.DiskCache;
import ru.teaz.testvkphoto.domain.image.MemoryCache;
import ru.teaz.testvkphoto.utils.Logger;

public class ImageLoader {

    private MemoryCache memoryCache = MemoryCache.getInstance();
    private DiskCache diskCache = DiskCache.getInstance();
    private static ExecutorService executorService;

    public ImageLoader() {
        if (executorService == null)
            executorService = Executors.newFixedThreadPool(32);
    }

    public void displayImage(String source, ImageView imageView) {
        Bitmap bitmap = memoryCache.getBitmapFromCache(source);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(null);
            loadPhoto(source, imageView);
        }
    }

    private void loadPhoto(String source, ImageView imageView) {
        executorService.submit(new PhotoLoader(source, imageView));
    }

    private class PhotoLoader implements Runnable {

        private String source;
        private ImageView imageView;

        PhotoLoader(String source, ImageView imageView) {
            this.source = source;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            Logger.d(Thread.currentThread().getName());
            Bitmap bitmap = getBitmap(source);
            memoryCache.addBitmapToCache(source, bitmap);

            imageView.post(new DisplayImage(bitmap, imageView));

        }

        private Bitmap getBitmap(String source) {
            File f = diskCache.getFile(source);
            Bitmap bitmap = decodeFile(f);
            if (bitmap != null) {
                return bitmap;
            }
            try {
                URL imageUrl = new URL(source);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(50000);
                conn.setReadTimeout(50000);
                conn.setInstanceFollowRedirects(true);
                InputStream is = conn.getInputStream();
                OutputStream os = new FileOutputStream(f);
                copyStream(is, os);
                os.close();
                bitmap = decodeFile(f);
                return bitmap;
            } catch (MalformedURLException e) {
                Logger.e("Bad url " + source +"; e: " + e.getMessage());
                return null;
            } catch (IOException e) {
                Logger.e(e.getMessage());
                return null;
            }
        }

        private Bitmap decodeFile(File f){
            return BitmapFactory.decodeFile(f.getAbsolutePath());
        }

        private void copyStream(InputStream is, OutputStream os) {
            final int buffer_size=1024;
            try  {
                byte[] bytes=new byte[buffer_size];
                for(;;)  {
                    int count=is.read(bytes, 0, buffer_size);
                    if(count==-1)
                        break;
                    os.write(bytes, 0, count);
                }
            }  catch(Exception ex){
                Logger.e(ex.getMessage());
            }
        }
    }

    private class DisplayImage implements Runnable {
        private Bitmap bitmap;
        private ImageView imageView;

        DisplayImage(Bitmap bitmap, ImageView imageView) {
            this.bitmap = bitmap;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            imageView.setImageBitmap(bitmap);
        }
    }
}
