package ru.teaz.testvkphoto.domain.loader;

import ru.teaz.testvkphoto.domain.loader.impl.PhotoDataLoaderImpl;

public class DataLoaderFactory {


    public static PhotoDataLoader createPhotoDataLoader(PhotoDataLoader.LoadCallback callback) {
        return new PhotoDataLoaderImpl(callback);
    }
}
