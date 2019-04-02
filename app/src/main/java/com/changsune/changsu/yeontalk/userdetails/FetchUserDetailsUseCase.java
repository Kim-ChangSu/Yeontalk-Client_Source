package com.changsune.changsu.yeontalk.userdetails;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.networking.UserDetailsResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;
import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.UserDetailsResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchUserDetailsUseCase extends BaseObservable<FetchUserDetailsUseCase.Listener> {

    private static final String TAG = "FetchUserDetailsUseCase";

    public interface Listener {
        void onFetchUserDetailsUseCaseSucceeded(UserDetails userDetails);
        void onFetchUserDetailsUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<UserDetailsResponseSchema> mCall;

    public FetchUserDetailsUseCase() {
        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void fetchUserDetailsAndNotify(String userId) {
        cancelCurrentFetchIfActive();
        mCall = mYeonTalkApi.fetchUserDetails(userId);
        mCall.enqueue(new Callback<UserDetailsResponseSchema>() {
            @Override
            public void onResponse(Call<UserDetailsResponseSchema> call, Response<UserDetailsResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getUser());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(UserDetails userDetails) {
        for (Listener listener : getListeners()) {
            listener.onFetchUserDetailsUseCaseSucceeded(userDetails);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchUserDetailsUseCaseFailed();
        }
    }
}
