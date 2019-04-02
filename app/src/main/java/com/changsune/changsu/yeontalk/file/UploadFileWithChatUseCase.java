package com.changsune.changsu.yeontalk.file;

import android.support.annotation.Nullable;
import android.util.Log;

import com.changsune.changsu.yeontalk.chat.Chat;
import com.changsune.changsu.yeontalk.common.BaseObservable;
import com.changsune.changsu.yeontalk.networking.YeonTalkApi;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadFileWithChatUseCase extends BaseObservable<UploadFileWithChatUseCase.Listener> {

    public interface Listener {
        void onUploadFileWithChatUseCaseSucceeded(String room_id, String from_user_id, String to_user_id, String file_name, String date, String type, String status, Chat chat, String size);
        void onUploadFileWithChatUseCaseFailed(Chat chat);
    }

    private static final String TAG = "UploadFileWithChatUseCa";

    private final YeonTalkApi mYeonTalkApi;

    @Nullable
    Call<ResponseBody> mCall;

    public UploadFileWithChatUseCase() {

        mYeonTalkApi = (YeonTalkApi) new Retrofit.Builder().baseUrl("http://52.79.51.149/yeontalk/").addConverterFactory(GsonConverterFactory.create()).build().create(YeonTalkApi.class);
    }

    public void uploadFileWithChatUseCaseAndNotify(MultipartBody.Part fileToUpload,
                                                   final String fileName,
                                                   final String chatRoomId,
                                                   final String fromUserId,
                                                   final String toUserId,
                                                   final String type,
                                                   final String date,
                                                   final String status,
                                                   final String file_size,
                                                   final Chat chat) {

        cancelCurrentFetchIfActive();

        RequestBody RequestBody_filename = RequestBody.create(MediaType.parse("text/plain"), fileName);
        RequestBody RequestBody_chatRoomId = RequestBody.create(MediaType.parse("text/plain"), chatRoomId);
        RequestBody RequestBody_fromUserId = RequestBody.create(MediaType.parse("text/plain"), fromUserId);
        RequestBody RequestBody_toUserId = RequestBody.create(MediaType.parse("text/plain"), toUserId);
        RequestBody RequestBody_type = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody RequestBody_date = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody RequestBody_status = RequestBody.create(MediaType.parse("text/plain"), status);
        RequestBody RequestBody_file_size = RequestBody.create(MediaType.parse("text/plain"), file_size);

        mCall = mYeonTalkApi.uploadFileWithChat(fileToUpload, RequestBody_filename, RequestBody_chatRoomId, RequestBody_fromUserId, RequestBody_toUserId, RequestBody_type, RequestBody_date, RequestBody_status, RequestBody_file_size);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    notifySucceeded(chatRoomId, fromUserId, toUserId, fileName, date, type, status, chat, file_size);

                } else {

                    notifyFailed(chat);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                notifyFailed(chat);
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (!(mCall == null || mCall.isCanceled() || mCall.isExecuted())) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(String room_id, String from_user_id, String to_user_id, String file_name, String date, String type, String status, Chat chat, String size) {
        for (Listener listener : getListeners()) {
            listener.onUploadFileWithChatUseCaseSucceeded(room_id, from_user_id, to_user_id, file_name, date, type, status, chat, size);
        }
    }

    private void notifyFailed(Chat chat) {
        for (Listener listener : getListeners()) {
            listener.onUploadFileWithChatUseCaseFailed(chat);
        }
    }
}
