package com.changsune.changsu.yeontalk.chat;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteChatUnShownUseCase extends BaseObservable<DeleteChatUnShownUseCase.Listener> {

    public interface Listener {
        void onDeleteChatUnShownUseCaseSucceeded();
        void onDeleteChatUnShownUseCaseFailed();
    }

    private static final String TAG = "InsertChatUseCase";

    private YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ResponseBody> mCall;

    public DeleteChatUnShownUseCase() {
        this.mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void deleteChatUnShownAndNotify(String room_id, String me_id) {

        cancelCurrentFetchIfActive();
        mCall = mYeonTalkApi.deletechatunshown(room_id, me_id);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        if (response.body().string().trim().equals("Success")) {
                            notifySucceeded();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        notifyFailed();
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

    private void notifySucceeded() {
        for (Listener listener : getListeners()) {
            listener.onDeleteChatUnShownUseCaseSucceeded();
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onDeleteChatUnShownUseCaseFailed();
        }
    }
}
