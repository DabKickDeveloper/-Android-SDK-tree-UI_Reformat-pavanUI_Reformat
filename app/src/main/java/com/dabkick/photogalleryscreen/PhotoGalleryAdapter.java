package com.dabkick.photogalleryscreen;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.PhotoGalleryViewHolder> {

    List<PhotoPojo> imagePathList;
    Context context;

    PhotoGalleryAdapter(Context context, List<PhotoPojo> imagePathList) {
        this.context = context;
        this.imagePathList = imagePathList;
    }

    @NonNull
    @Override
    public PhotoGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new PhotoGalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoGalleryViewHolder holder, int position) {
        holder.attachViews(imagePathList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagePathList.size();
    }

    class PhotoGalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewGallery, imageSelected;

        public PhotoGalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGallery = itemView.findViewById(R.id.imageGallery);
            imageSelected = itemView.findViewById(R.id.imageChecked);
        }

        void attachViews(final PhotoPojo photoPojo) {
            imageSelected.setVisibility(photoPojo.isChecked() ? View.VISIBLE : View.GONE);
            int imageViewSize = getScreenWidthPx() / 4;
            imageSelected.getLayoutParams().width = imageViewSize + DpToPx(2);
            imageSelected.getLayoutParams().height = imageViewSize + DpToPx(2);
            /*imageViewGallery.getLayoutParams().width = context.getResources().getDisplayMetrics().widthPixels /4;
            imageViewGallery.getLayoutParams().height = context.getResources().getDisplayMetrics().widthPixels /4;*/
            Picasso.with(context).load(new File(photoPojo.getImagePath())).centerCrop().resize(imageViewSize, imageViewSize).into(imageViewGallery);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoPojo.setChecked(!photoPojo.isChecked());
                    imageSelected.setVisibility(photoPojo.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public int getScreenWidthPx() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels - DpToPx(10);
    }

    public int DpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density + 0.5f);
    }

    public ArrayList<PhotoPojo> getSelected() {
        ArrayList<PhotoPojo> selected = new ArrayList<>();
        for (int i = 0; i < imagePathList.size(); i++) {
            if (imagePathList.get(i).isChecked()) {
                selected.add(imagePathList.get(i));
            }
        }
        return selected;
    }
}
