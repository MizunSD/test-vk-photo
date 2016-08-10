package ru.teaz.testvkphoto.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vk.sdk.api.model.VKPhotoArray;

import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.domain.loader.LoadBitmapTask;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoGridViewHolder> {

    private Context context;
    private VKPhotoArray array;

    public PhotoGridAdapter(Context context, VKPhotoArray array) {
        this.context = context;
        this.array = array;
    }

    public static class PhotoGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView photoPreview;

        public PhotoGridViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            photoPreview = (ImageView)itemView.findViewById(R.id.photo_preview);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
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
        LoadBitmapTask task = new LoadBitmapTask(holder.photoPreview);
        task.execute(array.get(position).photo_604);
    }

    @Override
    public int getItemCount() {
        return array.getCount();
    }

    public void addPhotos(VKPhotoArray array) {
        this.array.addAll(array);
    }
}
