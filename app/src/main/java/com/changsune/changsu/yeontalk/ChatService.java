package com.changsune.changsu.yeontalk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.changsune.changsu.yeontalk.chatroom.FetchChatRoomListUseCase;
import com.changsune.changsu.yeontalk.screens.chatroom.ChatRoomActivity;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;
import com.changsune.changsu.yeontalk.screens.videocallrequest.VideoCallRequestActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ChatService extends Service implements FetchChatRoomListUseCase.Listener{

    private static final String TAG = "ChatService";
    private static final String EXTRA_ME_ID_FOR_SERVICE = "EXTRA_ME_ID_FOR_SERVICE";

    public static Socket mSocket = null;
    public static Socket mSocket_py = null;
    BufferedReader mBufferedReader;
    BufferedWriter mBufferedWriter;
    public static PrintWriter mPrintWriter;
    BufferedReader mBufferedReader_py;
    BufferedWriter mBufferedWriter_py;
    PrintWriter mPrintWriter_py;
    String mUserId;
    String mMessage;
    public static SenderThread mSenderThread;

    FetchChatRoomListUseCase mFetchChatRoomListUseCase;

    IBinder mIBinder;


    public static void start(Context context, String meId) {
        Intent intent =  new Intent(context, ChatService.class);
        intent.putExtra(EXTRA_ME_ID_FOR_SERVICE, meId);
        context.startService(intent);
    }
    public static void stop(Context context) {
        Intent intent =  new Intent(context, ChatService.class);
        context.stopService(intent);
    }

    public class LocalBinder extends Binder {
        public ChatService getService() {
            return ChatService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        mFetchChatRoomListUseCase = new FetchChatRoomListUseCase();
        mFetchChatRoomListUseCase.registerListener(this);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        mUserId = intent.getExtras().getString(EXTRA_ME_ID_FOR_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "onStartCommand: ");
                    mSocket = new Socket(Constants.BASE_IP, Constants.PORT);
                    mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
                    mPrintWriter = new PrintWriter(mBufferedWriter, true);
                    mPrintWriter.println(mUserId);
                    mPrintWriter.flush();
                    Log.e(TAG, "run: " + mUserId);
//                    mSenderThread = new SenderThread();
//                    mSenderThread.start();

                    while (true) {

                        String message = mBufferedReader.readLine();
                        onReceive(message);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "onStartCommand: ");
                    mSocket_py = new Socket(Constants.BASE_IP_PYTHON, Constants.PORT_PYTHON);
                    mBufferedReader_py = new BufferedReader(new InputStreamReader(mSocket_py.getInputStream()));
                    mBufferedWriter_py = new BufferedWriter(new OutputStreamWriter(mSocket_py.getOutputStream()));
//                    mPrintWriter_py = new PrintWriter(mBufferedWriter_py, true);
//                    mPrintWriter_py.println(mUserId);
//                    mPrintWriter_py.flush();
                    Log.e(TAG, "run_python: " + mUserId);

                    while (true) {

                        String message = mBufferedReader_py.readLine();
                        onReceive(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

    public static void sendMessage(final String message) {

        Log.e(TAG, "sendMessage: ");

        if (ChatService.mSocket != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println(message.replace("\n", "%n"));
                    mPrintWriter.flush();
                }
            }).start();
        }
    }

    //----------------------------------------------------------------------------------------------
    //온데스트로이 : 서비스가 종료될 때 실행

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mSocket != null){
            Log.e(TAG, "onDestroy: ");
//                sendMessage("Socket_Closed" + ">" + mUserId);
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "onDestroy: catch");
            }

        }

        mFetchChatRoomListUseCase.unregisterListener(this);
    }


    public void onReceive(String message) {

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
        String currentActivity = taskInfo.get(0).topActivity.getShortClassName();
        Log.e( "CURRENT Activity ",  currentActivity);



        if (currentActivity.equals(".screens.chatbot.ChatBotActivity")) {

            Intent intent = new Intent(Constants.CHATBOT_ACTIVITY_BROADCAST_RECEIVER_KEY);
            intent.putExtra(Constants.CHATBOT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY, message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        } else {

            String[] split = message.split(">", 6);

            String toUserId = split[0];
            String fromUserId = split[1];
            String roomId = split[2];
            String chat_date = split[3];
            String type = split[4];
            String chat_message = split[5].replace("%n", "\n");

            if (type.equals(Constants.VIDEO_CALL_TYPE)) {

                VideoCallRequestActivity.start(this, fromUserId, chat_date, chat_message);

            } else {

                if (currentActivity.equals(".screens.chatroom.ChatRoomActivity")){

                    mFetchChatRoomListUseCase.fetchChatRoomListAndNotify(mUserId);

                } else if (currentActivity.equals(".screens.chat.ChatActivity")){

                    Intent intent = new Intent(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_KEY);
                    intent.putExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_USER_ID_KEY, fromUserId);
                    intent.putExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_ROOM_ID_KEY, roomId);
                    intent.putExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_DATE_KEY, chat_date);
                    intent.putExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY, chat_message);
                    intent.putExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_TYPE_KEY, type);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                }

            }

        }



    }

    //----------------------------------------------------------------------------------------------

    //알림 : 오레오 버전 고려

    public void onNotification(String msg, String nick, String room){

        String channelId = "channelText";
        String channelName = "ChannelNameTest";

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notifManager.createNotificationChannel(mChannel);
        }

        int requestID = (int) System.currentTimeMillis();

        Resources res = getResources();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra("notification_id", 9999);
        i.putExtra("room", room);
        PendingIntent pi = PendingIntent.getActivity(this,requestID,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
        builder.setContentTitle(room)
                .setContentText(nick + " : " +msg)
                .setTicker("["+room + "]에 새로운 메시지가 왔습니다.")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL); //알림 사운드 진동설정

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        notifManager.notify(1234, builder.build());

    }

    //----------------------------------------------------------------------------------------------
    //앱 상태 확인 : 포그라운드, 백그라운드

    private boolean isAppIsInBackground(Context context) {

        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    //----------------------------------------------------------------------------------------------

    // FetchChatRoomListUserCase.Listener

    @Override
    public void onFetchChatRoomListUseCaseSucceeded(List<ChatRoom> list) {
        ChatRoomActivity.mChatRoomArrayList.clear();
        ChatRoomActivity.mChatRoomArrayList.addAll(list);
        ChatRoomActivity.mChatRoomRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchChatRoomListUseCaseFailed() {

    }

}



