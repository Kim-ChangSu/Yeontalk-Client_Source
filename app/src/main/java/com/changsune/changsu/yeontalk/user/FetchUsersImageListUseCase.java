package com.changsune.changsu.yeontalk.user;

import android.support.annotation.Nullable;

import com.changsune.changsu.yeontalk.networking.YeonTalkApi;
import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.networking.MultiUsersListResponse;
import com.changsune.changsu.yeontalk.networking.UserListResponseSchema;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchUsersImageListUseCase extends BaseObservable<FetchUsersImageListUseCase.Listener> {

    private static final String TAG = "FetchUserListUseCase";

    public interface Listener {
        void onFetchUsersImageListUseCaseSucceeded(List<User> list_users_image, String user_login_time_loaded_last);
        void onFetchUsersImageListUseCaseFailed();
    }

    private final YeonTalkApi mYeonTalkApi;

    @Nullable Call<UserListResponseSchema> mCall;

    public FetchUsersImageListUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void fetchUsersImageListAndNotify(String me_id,
                                             String load_limit,
                                             final String user_login_time_loaded_last,
                                             String selection_gender,
                                             String selection_max_birthyear,
                                             String selection_min_birthyear,
                                             String selection_nation,
                                             String selection_region) {

        cancelCurrentFetchIfActive();

        mCall = mYeonTalkApi.fetchUsersImageList(me_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region);
        mCall.enqueue(new Callback<UserListResponseSchema>() {
            @Override
            public void onResponse(Call<UserListResponseSchema> call, Response<UserListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getUserList_users(), user_login_time_loaded_last);

                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<UserListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<User> list_users_image, String user_login_time_loaded_last) {
        for (Listener listener : getListeners()) {
            listener.onFetchUsersImageListUseCaseSucceeded(list_users_image, user_login_time_loaded_last);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchUsersImageListUseCaseFailed();
        }
    }
}
