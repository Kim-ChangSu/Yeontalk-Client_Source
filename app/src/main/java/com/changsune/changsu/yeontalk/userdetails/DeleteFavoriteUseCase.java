package com.changsune.changsu.yeontalk.userdetails;

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

public class DeleteFavoriteUseCase extends BaseObservable<DeleteFavoriteUseCase.Listener> {

    public interface Listener {
        void onDeleteFavoriteUseCaseSucceeded(String string);
        void onDeleteFavoriteUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ResponseBody> mCall;

    public DeleteFavoriteUseCase() {
        mYeonTalkApi = (YeonTalkApi) new Retrofit.Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void deleteFavoriteAndNotify(String userIdFrom, String userIdTo) {

        cancelCurrentFetchIfActive();
        mCall = mYeonTalkApi.deleteFavorite(userIdFrom, userIdTo);

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

    private void notifySucceeded(String string) {
        for (Listener listener : getListeners()) {
            listener.onDeleteFavoriteUseCaseSucceeded(string);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onDeleteFavoriteUseCaseFailed();
        }
    }
}
