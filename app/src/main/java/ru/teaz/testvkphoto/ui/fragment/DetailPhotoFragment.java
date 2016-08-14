package ru.teaz.testvkphoto.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiPhoto;

import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.domain.loader.ImageLoader;
import ru.teaz.testvkphoto.utils.VkApiUtils;

public class DetailPhotoFragment extends Fragment {

    private static final String PHOTO = "Photo";

    private VKApiPhoto mPhoto;
    private ImageLoader mImageLoader = new ImageLoader();

    private ImageView ivPhoto;

    public static DetailPhotoFragment newInstance(VKApiPhoto item) {
        DetailPhotoFragment fragment = new DetailPhotoFragment();

        Bundle args = new Bundle();
        args.putParcelable(PHOTO, item);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mPhoto = getArguments().getParcelable(PHOTO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_detail_photo, container, false);
        ivPhoto = (ImageView) rootView.findViewById(R.id.detail_photo);

        mImageLoader.displayImage(VkApiUtils.getMaxPhotoUrl(mPhoto), ivPhoto);

        return rootView;
    }
}
