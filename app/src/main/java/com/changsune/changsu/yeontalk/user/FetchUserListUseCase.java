package com.changsune.changsu.yeontalk.user;

import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.networking.MultiUsersListResponse;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;
import com.changsune.changsu.yeontalk.chat.Chat;
import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.networking.ChatListResponseSchema;
import com.changsune.changsu.yeontalk.networking.MultiUsersListResponse;
import com.changsune.changsu.yeontalk.networking.UserListResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchUserListUseCase extends BaseObservable<FetchUserListUseCase.Listener> {

    private static final String TAG = "FetchUserListUseCase";

    public interface Listener {
        void onFetchUserListUseCaseSucceeded(List<Me> list_me, List<User> list_chatbot, List<User> list_favorites, List<User> list_users, String user_login_time_loaded_last);
        void onFetchUserListUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<MultiUsersListResponse> mCall;

    public FetchUserListUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void fetchUserListUseCaseAndNotify(String user_device_id,
                                              String load_limit,
                                              final String user_login_time_loaded_last,
                                              String selection_gender,
                                              String selection_max_birthyear,
                                              String selection_min_birthyear,
                                              String selection_nation,
                                              String selection_region) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.fetchUserList(user_device_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region);
        mCall.enqueue(new Callback<MultiUsersListResponse>() {
            @Override
            public void onResponse(Call<MultiUsersListResponse> call, Response<MultiUsersListResponse> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getList_multiple_userlist().get(0).getUserList_me(),
                            response.body().getList_multiple_userlist().get(0).getUserList_chatbot(),
                            response.body().getList_multiple_userlist().get(0).getUserList_favorite(),
                            response.body().getList_multiple_userlist().get(0).getUserList_users(),
                            user_login_time_loaded_last);
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<MultiUsersListResponse> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<Me> list_me, List<User> list_chatbot, List<User> list_favorites, List<User> list_users, String user_login_time_loaded_last) {
        for (Listener listener : getListeners()) {
            listener.onFetchUserListUseCaseSucceeded(list_me, list_chatbot, list_favorites, list_users, user_login_time_loaded_last);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchUserListUseCaseFailed();
        }
    }
}
