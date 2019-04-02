package com.changsune.changsu.yeontalk;

import android.util.Log;

import java.io.PrintWriter;

public class SenderPyThread extends Thread {

    private static final String TAG = "SenderPyThread";

    private PrintWriter mWriter;

    @Override
    public void run() {

        try {
            mWriter = new PrintWriter(ChatService.mSocket_py.getOutputStream());
            Log.e(TAG, "run: ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(final String message) {

        if (ChatService.mSocket_py != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: sendmessage" );
                    mWriter.println(message.replace("\n", "%n"));
                    mWriter.flush();
                }
            }).start();
        }
    }
}
