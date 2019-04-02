package com.changsune.changsu.yeontalk.chat;

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

public class InsertChatUseCase extends BaseObservable<InsertChatUseCase.Listener> {

    public interface Listener {
        void onInsertChatUseCaseSucceeded(String str, String str2, String str3, String str4, String str5, String str6, String status, Chat chat);
        void onInsertChatUseCaseFailed(Chat chat);
    }

    private static final String TAG = "InsertChatUseCase";

    private YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ResponseBody> mCall;

    public InsertChatUseCase() {
        this.mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void insertChatUseCaseAndNotify(String room_id, final String from_user_id, final String to_user_id, final String message, final String date, final String type, final String status, final Chat chat) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.insertChat(room_id, from_user_id, to_user_id, type, message, date, status);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    String room_id_from_database = null;
                    try {
                        room_id_from_database = response.body().string().trim();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.e(TAG, "onResponse: " + room_id_from_database);

                    notifySucceeded(room_id_from_database, from_user_id, to_user_id, message, date, type, status, chat);
                } else {

                    notifyFailed(chat);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyFailed(chat);
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(String room_id, String from_user_id, String to_user_id, String message, String date, String type, String status, Chat chat) {
        for (Listener listener : getListeners()) {
            listener.onInsertChatUseCaseSucceeded(room_id, from_user_id, to_user_id, message, date, type, status, chat);
        }
    }

    private void notifyFailed(Chat chat) {
        for (Listener listener : getListeners()) {
            listener.onInsertChatUseCaseFailed(chat);
        }
    }
}
