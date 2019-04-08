package com.changsune.changsu.yeontalk.screens.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;

import java.util.ArrayList;

public class GalleryViewPagerAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    Context mContext;
    ArrayList<String> mImages;
    ImageLoader mImageLoader;

    public GalleryViewPagerAdapter(Context context, ArrayList<String> images, ImageLoader imageLoader) {
        mContext = context;
        mImages = images;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.layout_gallery_item, container, false);
        mImageLoader.loadImageInGallery(mImages.get(position), (ImageView) view.findViewById(R.id.imageview_gallery_id));
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
