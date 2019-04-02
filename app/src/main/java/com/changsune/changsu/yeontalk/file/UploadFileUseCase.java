package com.changsune.changsu.yeontalk.file;

import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadFileUseCase extends BaseObservable<UploadFileUseCase.Listener> {

    public interface Listener {
        void onUploadFileUseCaseSucceeded(String fileName);
        void onUploadFileUseCaseFailed();
    }

    private static final String TAG = "UploadFileUseCase";

    private final YeonTalkApi mYeonTalkApi;

    @Nullable
    Call<ResponseBody> mCall;

    public UploadFileUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Retrofit.Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void uploadFileAndNotify(MultipartBody.Part fileToUpload,
                                    final String fileName,
                                    String user_id) {

        cancelCurrentFetchIfActive();

        RequestBody RequestBody_filename = RequestBody.create(MediaType.parse("text/plain"), fileName);
        RequestBody RequestBody_userId = RequestBody.create(MediaType.parse("text/plain"), user_id);

        mCall = mYeonTalkApi.uploadFile(fileToUpload, RequestBody_filename, RequestBody_userId);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    notifySucceeded(fileName);

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
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }
    private void notifySucceeded(String fileName) {
        for (Listener listener : getListeners()) {
            listener.onUploadFileUseCaseSucceeded(fileName);
        }
    }
    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onUploadFileUseCaseFailed();
        }
    }
}
