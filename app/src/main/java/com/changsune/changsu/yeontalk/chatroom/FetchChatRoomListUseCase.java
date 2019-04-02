package com.changsune.changsu.yeontalk.chatroom;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.ChatRoomListResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchChatRoomListUseCase extends BaseObservable<FetchChatRoomListUseCase.Listener> {

    private static final String TAG = "FetchChatRoomListUseCase";

    public interface Listener {
        void onFetchChatRoomListUseCaseFailed();
        void onFetchChatRoomListUseCaseSucceeded(List<ChatRoom> list);
    }
    private YeonTalkApi mYeonTalkApi;

    @Nullable
    public Call<ChatRoomListResponseSchema> mCall;

    public FetchChatRoomListUseCase() {
        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    @SuppressLint({"LongLogTag"})
    public void fetchChatRoomListAndNotify(String userId) {
        cancelCurrentFetchIfActive();
        Log.e(TAG, "fetchChatListUseCaseAndNotify: ");
        mCall = mYeonTalkApi.fetchChatRoomListByUserId(userId);
        mCall.enqueue(new Callback<ChatRoomListResponseSchema>() {
            @Override
            public void onResponse(Call<ChatRoomListResponseSchema> call, Response<ChatRoomListResponseSchema> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: ");
                    notifySucceeded(response.body().getChatRoomLists());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<ChatRoomListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<ChatRoom> chatsLists) {
        for (Listener listener : getListeners()) {
            listener.onFetchChatRoomListUseCaseSucceeded(chatsLists);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchChatRoomListUseCaseFailed();
        }
    }
}
