package com.changsune.changsu.yeontalk.screens.common.videokit;

import android.app.Activity;
import android.os.AsyncTask;

import com.changsune.changsu.yeontalk.chat.Chat;

public class VideoKit extends AsyncTask<String, Void, Void> {

    Listener mListener;

    public interface Listener {
        void onVideoKitSuccessed(String out, Chat chat);
        void onVideoKitFailed();
    }

    static {
        System.loadLibrary("native-lib");
    }

    Activity mActivity;
    String mMediaPath;
    Chat mChat;

    public VideoKit(Activity activity, Listener listener, String mediaPath, Chat chat) {
        mActivity = activity;
        mListener = listener;
        mMediaPath = mediaPath;
        mChat = chat;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
           RunCommand(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        mListener.onVideoKitSuccessed(mMediaPath, mChat);


    }

    native int RunCommand(String[] argv) throws Exception;
    native void StopProcess() throws Exception;
}
