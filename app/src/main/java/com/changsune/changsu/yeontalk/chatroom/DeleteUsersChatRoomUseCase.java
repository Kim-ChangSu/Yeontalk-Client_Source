package com.changsune.changsu.yeontalk.chatroom;

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

public class DeleteUsersChatRoomUseCase extends BaseObservable<DeleteUsersChatRoomUseCase.Listener> {

    public interface Listener {
        void onDeleteUsersChatRoomUseCaseSucceeded();
        void onDeleteUsersChatRoomUseCaseFailed();
    }

    private static final String TAG = "DeleteUsersChatRoomUseC";

    private YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ResponseBody> mCall;

    public DeleteUsersChatRoomUseCase() {
        this.mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void deleteUsersChatRoomAndNotify(String room_id, String me_id) {

        cancelCurrentFetchIfActive();
        mCall = mYeonTalkApi.deleteuserschatroom(room_id, me_id);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        if (response.body().string().trim().equals("Success")) {
                            notifySucceeded();
                        } else {
                            Log.e(TAG, "onResponse: " + response.body().string());
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
            listener.onDeleteUsersChatRoomUseCaseSucceeded();
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onDeleteUsersChatRoomUseCaseFailed();
        }
    }
}
