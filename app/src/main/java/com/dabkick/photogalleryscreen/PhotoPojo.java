package com.dabkick.photogalleryscreen;


public class PhotoPojo {

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String path;

    public String getImagePath() {
        return path;
    }

    public void setImagePath(String name) {
        this.path = name;
    }

}