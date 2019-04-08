package com.changsune.changsu.yeontalk.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.changsune.changsu.yeontalk.ChatService;
import com.changsune.changsu.yeontalk.Constants;

import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";
    private static final String EXTRA_ME_ID_FOR_RECEIVER = "EXTRA_ME_ID_FOR_RECEIVER";

    SharedPreferences mSharedPreferences;
    String mMeId;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        mSharedPreferences = context.getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, Context.MODE_PRIVATE);
        mMeId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, "");
        Log.e(TAG, "onReceive: " + mMeId);
        // 여기에서 네트워크 상태를 체크 하시면 됩니다.

// 네트워크 변환 Receiver
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        //TODO 네트워크 변환에 따른 이후 로직을 처리한다.
        //ex. noti,activity 호출,service 호출 등등
        Log.e("network Receiver", "network isConnected :  " + isConnected);

        if (isConnected) {
            if (mMeId != "") {

                Log.e(TAG, "onReceive: + startService");
                startService(context, mMeId);

            }
        } else {
            Log.e(TAG, "onReceive: stopService");
            stopService(context);
        }



        // 참고로 아래와 같이 네트워크 상태가 바꼈을 때, 로컬 Brocast를 날려서

        // 화면에 알린 후, 화면 별로 알맞게 제어 하는 것도 좋은 방법 인듯 하네요.
    }

    private void changeRecyclerViewIfInChatActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
        String currentActivity = taskInfo.get(0).topActivity.getShortClassName();
        Log.e( "CURRENT Activity ",  currentActivity);

        if (currentActivity.equals(".screens.chat.ChatActivity")){

            Intent intent = new Intent(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_FOR_CHANGING_VIEW_KEY);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        } else {

        }
    }

    private void startService(Context context, String meId){

        if(!isMyServiceRunning(context, ChatService.class)){
            ChatService.start(context, meId);
            changeRecyclerViewIfInChatActivity(context);
        } else {

            Log.e(TAG, "startService: is Service Running");

        }

    }

    private void stopService(Context context){

        if(isMyServiceRunning(context, ChatService.class)){
            ChatService.stop(context);

        } else {
            Log.e(TAG, "startService: is not Service Running");
        }

    }


    //서비스 러닝 확인
    private boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
