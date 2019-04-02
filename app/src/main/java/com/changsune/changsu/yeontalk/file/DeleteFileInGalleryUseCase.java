package com.changsune.changsu.yeontalk.file;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteFileInGalleryUseCase extends BaseObservable<DeleteFileInGalleryUseCase.Listener> {

    private static final String TAG = "DeleteFileInGalleryUseC";

    public interface Listener {
        void onDeleteImageSucceeded(String imageId);
        void onDeleteImageFailed();

    }

    private YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ResponseBody> mCall;



    public DeleteFileInGalleryUseCase() {
        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void deleteImageInGallery(String userId, final String imageId) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.deleteImage(userId, imageId);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    notifySucceeded(imageId);

                } else {
                    notifyFailed();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(String imageId) {
        for (Listener listener : getListeners()) {
            listener.onDeleteImageSucceeded(imageId);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onDeleteImageFailed();
        }
    }
}
