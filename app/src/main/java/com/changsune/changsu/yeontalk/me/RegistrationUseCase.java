package com.changsune.changsu.yeontalk.me;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationUseCase extends BaseObservable<RegistrationUseCase.Listener> {

    private static final String TAG = "RegistrationUseCase";;

    public interface Listener {
        void onRegistrationUseCaseSucceeded(String result);
        void onRegistrationUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<ResponseBody> mCall;

    public RegistrationUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Retrofit.Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void registrationUseCaseAndNotify(String deviceId, String nickName, String gender, String birthYear, String nation, String region) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.insertRegistration(deviceId, nickName, gender, birthYear, nation, region);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        notifySucceeded(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private void notifySucceeded(String result) {
        for (Listener listener : getListeners()) {
            listener.onRegistrationUseCaseSucceeded(result);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onRegistrationUseCaseFailed();
        }
    }
}
