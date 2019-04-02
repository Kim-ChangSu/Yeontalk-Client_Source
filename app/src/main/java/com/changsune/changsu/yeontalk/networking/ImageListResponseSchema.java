package com.changsune.changsu.yeontalk.networking;

import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.image.Image;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageListResponseSchema {
    @SerializedName("image_list")
    private final List<Image> mImageList;

    public ImageListResponseSchema(List<Image> imageList) {
        mImageList = imageList;
    }

    public List<Image> getImageList() {
        return mImageList;
    }
}
