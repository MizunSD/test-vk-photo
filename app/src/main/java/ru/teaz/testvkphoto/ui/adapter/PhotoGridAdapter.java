package ru.teaz.testvkphoto.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.domain.loader.ImageLoader;
import ru.teaz.testvkphoto.ui.interfaces.PhotoGridView;
import ru.teaz.testvkphoto.utils.VkApiUtils;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoGridViewHolder> {

    private VKPhotoArray array;
    private ImageLoader imageLoader;
    private PhotoGridView callback;

    public PhotoGridAdapter(PhotoGridView view, VKPhotoArray array) {
        this.array = array;
        this.imageLoader = new ImageLoader();
        this.callback = view;
    }

    public static class PhotoGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView photoPreview;
        private PhotoGridView callback;

        public PhotoGridViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            photoPreview = (ImageView)itemView.findViewById(R.id.photo_preview);
        }

        @Override
        public void onClick(View view) {
            callback.onPhotoClicked(getAdapterPosition());
        }

        public void setClickListener(PhotoGridView callback) {
            this.callback = callback;
        }
    }

    @Override
    public PhotoGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, parent, false);
        PhotoGridViewHolder vh = new PhotoGridViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PhotoGridViewHolder holder, int position) {
        holder.setClickListener(callback);
        imageLoader.displayImage(VkApiUtils.getMinPhotoAbove604Url(array.get(position)), holder.photoPreview);
    }

    @Override
    public int getItemCount() {
        return array.getCount();
    }

    public VKApiPhoto getItem(int position) {
        return array.get(position);
    }

    public void addPhotos(VKPhotoArray array) {
        this.array.addAll(array);
    }

    public VKPhotoArray getArray() {
        return array;
    }
}
