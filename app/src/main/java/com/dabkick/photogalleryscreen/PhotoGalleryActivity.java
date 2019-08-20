package com.dabkick.photogalleryscreen;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryActivity extends AppCompatActivity {

    RecyclerView galleryRecyclerView;
    PhotoGalleryAdapter photoGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_photo_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PhotoGalleryActivity.this, "Back pressed..", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setImageDrawable(ContextCompat.getDrawable(PhotoGalleryActivity.this, R.drawable.add_queue));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoGalleryAdapter.getSelected();
                if (photoGalleryAdapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < photoGalleryAdapter.getSelected().size(); i++) {
                        stringBuilder.append(photoGalleryAdapter.getSelected().get(i).getImagePath());
                        stringBuilder.append("\n");
                    }
                    Toast.makeText(PhotoGalleryActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "No images selected!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        galleryRecyclerView = findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(PhotoGalleryActivity.this, 4);
        galleryRecyclerView.setLayoutManager(layoutManager);

        List<PhotoPojo> stringArrayList = getAllShownImagesPath(PhotoGalleryActivity.this);

        photoGalleryAdapter = new PhotoGalleryAdapter(PhotoGalleryActivity.this, stringArrayList);
        galleryRecyclerView.setAdapter(photoGalleryAdapter);
    }

    private List<PhotoPojo> getAllShownImagesPath(Context context) {

        int column_index_data, column_index_folder_name;
        List<PhotoPojo> listOfAllImages = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            PhotoPojo photoPojo = new PhotoPojo();
            String absolutePathOfImage = cursor.getString(column_index_data);
            photoPojo.setImagePath(absolutePathOfImage);
            listOfAllImages.add(photoPojo);
        }

        return listOfAllImages;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.photo_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
