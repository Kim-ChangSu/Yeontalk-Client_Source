package com.changsune.changsu.yeontalk.screens.common.imageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.imageloader.GlideApp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ImageLoader {

    private static final String TAG = "ImageLoader";

    private final Activity mActivity;
    private final RequestOptions mDefaultRequestOptions;

    public ImageLoader(Activity activity) {

        mActivity = activity;
        mDefaultRequestOptions = new RequestOptions().centerCrop();
    }

    public void loadImage(String uri, ImageView target) {
        GlideApp.with(mActivity).load(uri).into(target);
    }

    public void loadChatImage(String uri, ImageView target) {


        Glide.with(mActivity.getApplicationContext()).asBitmap().load(uri).into(target);
    }

    public void loadChatVideoImage(String uri, final ImageView target) {

        GlideApp.with(mActivity.getApplicationContext()).asBitmap().load(uri).into(target);

    }
}
