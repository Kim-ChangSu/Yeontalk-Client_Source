package com.changsune.changsu.yeontalk.me;

import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateMeDataUseCase extends BaseObservable<UpdateMeDataUseCase.Listener> {

    private static final String TAG = "UpdateMeDataUseCase";

    public interface Listener {
        void onUpdateMeDataUseCaseSucceeded(String data_key, String data_value);
        void onUpdateMeDataUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<ResponseBody> mCall;

    public UpdateMeDataUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void updateUserDataAndNotify(String user_id,
                                        final String data_key,
                                        final String data_value) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.updateUserData(user_id, data_key, data_value);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (result.trim().equals("Success")) {
                    notifySucceeded(data_key, data_value);
                } else {
                    Log.e(TAG, "onResponse: 2");
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

    private void notifySucceeded(String data_key, String data_value) {
        for (Listener listener : getListeners()) {
            listener.onUpdateMeDataUseCaseSucceeded(data_key, data_value);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onUpdateMeDataUseCaseFailed();
        }
    }
}
