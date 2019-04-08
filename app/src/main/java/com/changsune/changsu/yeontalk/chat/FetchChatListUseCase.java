package com.changsune.changsu.yeontalk.chat;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.ChatListResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchChatListUseCase extends BaseObservable<FetchChatListUseCase.Listener> {

    public interface Listener {
        void onFetchChatListUseCaseSucceeded(List<Chat> list);
        void onFetchChatListUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<ChatListResponseSchema> mCall;

    public FetchChatListUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void fetchChatListUseCaseAndNotify(String roomId, String meId, String userId, String chatTimeLastLoaded, String loadLimit) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.fetchChatList(roomId, meId, userId, chatTimeLastLoaded, loadLimit);
        mCall.enqueue(new Callback<ChatListResponseSchema>() {
            @Override
            public void onResponse(Call<ChatListResponseSchema> call, Response<ChatListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getChatList());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<ChatListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<Chat> chatList) {
        for (Listener listener : getListeners()) {
            listener.onFetchChatListUseCaseSucceeded(chatList);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchChatListUseCaseFailed();
        }
    }
}
