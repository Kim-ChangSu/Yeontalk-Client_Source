package com.changsune.changsu.yeontalk.image;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("image_id")
    String mImageId;
    @SerializedName("image_url")
    String mImageUrl;

    public Image(String imageId, String imageUrl) {
        mImageId = imageId;
        mImageUrl = imageUrl;
    }

    public String getImageId() {
        return mImageId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageId(String imageId) {
        mImageId = imageId;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
