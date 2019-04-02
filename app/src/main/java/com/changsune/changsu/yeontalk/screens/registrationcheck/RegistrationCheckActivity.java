package com.changsune.changsu.yeontalk.screens.registrationcheck;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionCheckBoxDialogFragment;
import com.changsune.changsu.yeontalk.screens.registration.RegistrationActivity;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class RegistrationCheckActivity extends AppCompatActivity {

    Button mButton;
    CheckBox mCheckBox1;
    CheckBox mCheckBox2;

    FragmentManager mFragmentManager;
    DialogsManager mDialogsManager;


    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, RegistrationCheckActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_check);

        mButton = findViewById(R.id.button_registration_check_id);
        mCheckBox1 = findViewById(R.id.check1);
        mCheckBox2 = findViewById(R.id.check2);

        mFragmentManager = getSupportFragmentManager();
        mDialogsManager = new DialogsManager(mFragmentManager);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCheckBox1.isChecked() == false) {

                    mDialogsManager.showDialogWithId(RequestSelectionCheckBoxDialogFragment.newInstance(), "checkBox1");

                } else if (mCheckBox2.isChecked() == false) {

                    mDialogsManager.showDialogWithId(RequestSelectionCheckBoxDialogFragment.newInstance(), "checkBox2");

                } else {

                    RegistrationActivity.start(RegistrationCheckActivity.this);
                    finish();

                }
            }
        });

    }
}
