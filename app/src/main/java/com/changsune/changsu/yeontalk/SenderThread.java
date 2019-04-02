package com.changsune.changsu.yeontalk;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SenderThread extends Thread {

    private static final String TAG = "SenderThread";

    private PrintWriter mWriter;

    public SenderThread(PrintWriter writer) {
        mWriter = writer;
    }

    @Override
    public void run() {

        try {
            mWriter = new PrintWriter(ChatService.mSocket.getOutputStream());
            Log.e(TAG, "run: ");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void sendMessage(final String message) {

        if (ChatService.mSocket != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWriter.println(message.replace("\n", "%n"));
                    mWriter.flush();
                }
            }).start();
        }
    }
}
