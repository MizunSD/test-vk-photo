package ru.teaz.testvkphoto.domain.loader.impl;


import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONException;

import ru.teaz.testvkphoto.domain.loader.PhotoDataLoader;
import ru.teaz.testvkphoto.domain.net.ServerApi;
import ru.teaz.testvkphoto.domain.net.ServerApiFactory;

public class PhotoDataLoaderImpl implements PhotoDataLoader{

    private final int LOAD_COUNT = 10;
    private PhotoDataLoader.LoadCallback mCallback;

    private final ServerApi mServerApi = ServerApiFactory.create();

    public PhotoDataLoaderImpl(LoadCallback callback) {
        mCallback = callback;
    }

    @Override
    public void loadPhotoData() {
        getPhotoCount();
    }

    private void getPhotoCount() {
        mServerApi.getPhotoList(VKParameters.from(VKApiConst.COUNT, 1), new VKRequest.VKRequestListener() {
            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onComplete(VKResponse response) {
                try {
                    int photoCount = response.json.getJSONObject("response").getInt(VKApiConst.COUNT);
                    mCallback.setPhotoCount(photoCount);
                    int requestCount = photoCount / LOAD_COUNT + 1;
                    getPhotoMetadata(requestCount);
                } catch (JSONException e) {
                    mCallback.onError(new VKError(VKError.VK_API_ERROR));
                }
            }
        });
    }

    private void getPhotoMetadata(int requestCount) {
        VKRequest[] requests = new VKRequest[requestCount];
        for (int i = 0; i < requestCount; i++) {
            requests[i] = new VKRequest("photos.getAll",
                    VKParameters.from(VKApiConst.COUNT, LOAD_COUNT, VKApiConst.OFFSET, i * LOAD_COUNT));
        }
        VKBatchRequest batchRequest = new VKBatchRequest(requests);
        batchRequest.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                VKPhotoArray array = new VKPhotoArray();
                for (VKResponse resp: responses) {
                    try {
                        array.addAll((VKPhotoArray) new VKPhotoArray().parse(resp.json.getJSONObject("response")));
                    } catch (JSONException e) {
                        mCallback.onError(new VKError(VKError.VK_JSON_FAILED));
                    }
                }
                mCallback.onComplete(array);
            }

            @Override
            public void onError(VKError error) {
                mCallback.onError(error);
            }
        });
    }

}
