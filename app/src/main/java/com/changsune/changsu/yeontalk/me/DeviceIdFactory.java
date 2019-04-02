package com.changsune.changsu.yeontalk.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class DeviceIdFactory {
    
    public static final String PREFS_DEVICE_ID = "device_id";
    public static final String PREFS_FILE = "device_id.xml";
    public String mDevice_id;
    
    public DeviceIdFactory(Context context) {

        if (mDevice_id == null) {
            synchronized (DeviceIdFactory.class) {
                if (mDevice_id == null) {
                    SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    mDevice_id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (mDevice_id == null) {
                        String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
                        if (!"9774d56d682e549c".equals(androidId)) {
                            mDevice_id = androidId;
                        } else if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
                            return;
                        } else {
                            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            if (Build.VERSION.SDK_INT >= 26) {
                                mDevice_id = tm.getImei();
                            } else {
                                mDevice_id = tm.getDeviceId();
                            }
                        }
                        prefs.edit().putString(PREFS_DEVICE_ID, mDevice_id.toString()).commit();
                    }
                }
            }
        }
    }

    public String getDeviceId() {

        return mDevice_id;

    }
}
