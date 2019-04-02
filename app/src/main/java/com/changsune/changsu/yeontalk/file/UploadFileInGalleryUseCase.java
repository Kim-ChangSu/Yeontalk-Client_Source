package com.changsune.changsu.yeontalk.file;

import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.networking.ImageListResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadFileInGalleryUseCase extends BaseObservable<UploadFileInGalleryUseCase.Listener> {

    public interface Listener {
        void onUploadFileInGalleryUseCaseSucceeded(List<Image> ex_images, List<Image> new_images);
        void onUploadFileInGalleryUseCaseFailed();
    }

    private static final String TAG = "UploadFileUseCase";

    private final YeonTalkApi mYeonTalkApi;

    @Nullable
    Call<ImageListResponseSchema> mCall;

    public UploadFileInGalleryUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Retrofit.Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void uploadFileInGalleryAndNotify(final ArrayList<Image> images,
                                             String user_id) {

        ArrayList<MultipartBody.Part> fileToUploadList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            if (images.get(i) != null) {
                File file = new File(images.get(i).getImageUrl());
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file" + String.valueOf(i + 1), file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
                fileToUploadList.add(fileToUpload);
            } else {
                fileToUploadList.add(null);
            }
        }

        cancelCurrentFetchIfActive();

        RequestBody RequestBody_userId = RequestBody.create(MediaType.parse("text/plain"), user_id);

        mCall = mYeonTalkApi.uploadImages(fileToUploadList.get(0), fileToUploadList.get(1), fileToUploadList.get(2) , fileToUploadList.get(3), fileToUploadList.get(4), RequestBody_userId);

        mCall.enqueue(new Callback<ImageListResponseSchema>() {
            @Override
            public void onResponse(Call<ImageListResponseSchema> call, Response<ImageListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(images, response.body().getImageList());
                } else {
                    notifyFailed();
                }

            }

            @Override
            public void onFailure(Call<ImageListResponseSchema> call, Throwable t) {
                notifyFailed();

            }
        });
    }
    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }
    private void notifySucceeded(List<Image> ex_images, List<Image> new_images) {
        for (Listener listener : getListeners()) {
            listener.onUploadFileInGalleryUseCaseSucceeded(ex_images, new_images);
        }
    }
    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onUploadFileInGalleryUseCaseFailed();
        }
    }
}
