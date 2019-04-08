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

public class SecessionUseCase extends BaseObservable<SecessionUseCase.Listener> {

    private static final String TAG = "UpdateMeDataUseCase";

    public interface Listener {
        void onSecessionUseCaseSucceeded();
        void onSecessionUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<ResponseBody> mCall;

    public SecessionUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void secessionAndNotify(final String user_id) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.secession(user_id);
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
                    notifySucceeded();
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

    private void notifySucceeded() {
        for (Listener listener : getListeners()) {
            listener.onSecessionUseCaseSucceeded();
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onSecessionUseCaseFailed();
        }
    }
}
