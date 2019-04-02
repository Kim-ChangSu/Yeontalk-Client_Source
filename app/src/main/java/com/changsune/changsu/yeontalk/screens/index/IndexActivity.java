package com.changsune.changsu.yeontalk.screens.index;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.IndexServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.registrationindex.RegistrationIndexActivity;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.me.ConfirmMeUseCase;
import com.changsune.changsu.yeontalk.me.DeviceIdFactory;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.IndexServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.registrationindex.RegistrationIndexActivity;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;

public class IndexActivity extends AppCompatActivity implements ConfirmMeUseCase.Listener{

    private static final String TAG = "IndexActivity";

    ImageView mImageView;

    DeviceIdFactory mDeviceIdFactory;
    ConfirmMeUseCase mConfirmMeUseCase;
    DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mDeviceIdFactory = new DeviceIdFactory(getApplicationContext());
        mConfirmMeUseCase = new ConfirmMeUseCase();
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        Constants.DEVICE_ID = mDeviceIdFactory.getDeviceId();
        mConfirmMeUseCase.confirmUserUseCaseAndNotify(Constants.DEVICE_ID);

        Log.e(TAG, "onCreate: " + Constants.DEVICE_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mConfirmMeUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mConfirmMeUseCase.unregisterListener(this);
    }

    @Override
    public void onConfirmMeUseCaseSucceeded(String result) {

        if (result.equals("Registered")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UsersActivity.start(IndexActivity.this);
                    finish();
                }
            }, 1000);

        } else if (result.equals("Blocked")) {

            Log.e(TAG, "onConfirmUserUseCaseSucceeded: Blocked");

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RegistrationIndexActivity.start(IndexActivity.this);
                    finish();
                }
            }, 1000);

        }
    }

    @Override
    public void onConfirmMeUseCaseFailed() {
        mDialogsManager.showDialogWithId(IndexServerErrorDialogFragment.newInstance(), "");
    }
}
