package com.changsune.changsu.yeontalk.screens.registration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.me.RegistrationUseCase;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionUserDataDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionNickNameDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionRegistrationDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;

public class RegistrationActivity extends AppCompatActivity implements SelectionNickNameDialogFragment.Listener
        , SelectionUserDataDialogFragment.Listener, RegistrationUseCase.Listener{

    private static final String TAG = "RegistrationActivity";

    private Button mButton_registration;
    private LinearLayout mLayout_registration_birthyear;
    private LinearLayout mLayout_registration_gender;
    private LinearLayout mLayout_registration_nation;
    private LinearLayout mLayout_registration_nickname;
    private LinearLayout mLayout_registration_region;
    private TextView mTextView_registration_birthyear;
    private TextView mTextView_registration_gender;
    private TextView mTextView_registration_nation;
    private TextView mTextView_registration_nickname;
    private TextView mTextView_registration_region;

    private String mNickName;
    private String mGender;
    private String mBirthYear;
    private String mNation;
    private String mRegion;

    private DialogsManager mDialogsManager;

    private RegistrationUseCase mRegistrationUseCase;

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mButton_registration = findViewById(R.id.button_registration_id);
        mLayout_registration_birthyear = findViewById(R.id.layout_registration_birth_year_id);
        mLayout_registration_gender = findViewById(R.id.layout_registration_gender_id);
        mLayout_registration_nation = findViewById(R.id.layout_registration_nation_id);
        mLayout_registration_nickname = findViewById(R.id.layout_registration_nickname_id);
        mLayout_registration_region = findViewById(R.id.layout_registration_region_id);
        mTextView_registration_birthyear = findViewById(R.id.textview_registration_birthyear_id);
        mTextView_registration_gender = findViewById(R.id.textview_registration_gender_id);
        mTextView_registration_nation = findViewById(R.id.textview_registration_nation_id);
        mTextView_registration_nickname = findViewById(R.id.textview_registration_nickname_id);
        mTextView_registration_region = findViewById(R.id.textview_registration_region_id);

        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        mRegistrationUseCase = new RegistrationUseCase();

        mLayout_registration_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionNickNameDialog();
            }
        });

        mLayout_registration_birthyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("생년", mBirthYear);

            }
        });

        mLayout_registration_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("성별", mGender);
            }
        });

        mLayout_registration_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("국가", mNation);

            }
        });

        mLayout_registration_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNation == null) {
                    openRequestSelectionRegistrationDialog("국가먼저");
                } else {
                    openSelectionUserDataDialog("지역", mRegion);
                }
            }
        });

        mButton_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNickName == null) {
                    openRequestSelectionRegistrationDialog("닉네임");
                } else if (mGender == null) {
                    openRequestSelectionRegistrationDialog("성별");
                } else if (mBirthYear == null) {
                    openRequestSelectionRegistrationDialog("생년");
                } else if (mNation == null) {
                    openRequestSelectionRegistrationDialog("국가");
                } else if (mRegion == null) {
                    openRequestSelectionRegistrationDialog("지역");
                } else {
                    mRegistrationUseCase.registrationUseCaseAndNotify(Constants.DEVICE_ID, mNickName, mGender, mBirthYear, mNation, mRegion);

                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegistrationUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRegistrationUseCase.unregisterListener(this);
    }

    @Override
    public void onPositiveButtonClicked(String key, String value) {

        if (key.equals("성별")) {
            if(!value.equals(mGender)) {
                mGender = value;
                mTextView_registration_gender.setText(mGender);
            }
        } else if (key.equals("생년")) {
            if(!value.equals(mBirthYear)) {
                mBirthYear = value;
                mTextView_registration_birthyear.setText(mBirthYear);
            }
        } else if (key.equals("국가")) {
            if (!value.equals(mNation)) {
                mNation = value;
                mTextView_registration_nation.setText(mNation);
                mRegion = null;
                mTextView_registration_region.setText(R.string.registration_default);
            }

        } else if (key.equals("지역")) {
            if(!value.equals(mRegion)) {
                mRegion = value;
                mTextView_registration_region.setText(mRegion);
            }
        }
    }

    @Override
    public void onPositiveButtonClicked(String nickName) {
        mNickName = nickName;
        mTextView_registration_nickname.setText(mNickName);
    }

    @Override
    public void onRegistrationUseCaseSucceeded(String result) {
        if (result.equals("Success")) {
            UsersActivity.start(this);
            finish();
        } else {

        }
    }

    @Override
    public void onRegistrationUseCaseFailed() {

        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    private void openSelectionNickNameDialog() {
        mDialogsManager.showDialogWithId(SelectionNickNameDialogFragment.newInstance(this), mNickName);
    }

    private void openSelectionUserDataDialog(String key, String value) {

        if (key.equals("지역")) {
            mDialogsManager.showDialogWithId(SelectionUserDataDialogFragment.newInstance(this, value, mNation), key);
        } else {
            mDialogsManager.showDialogWithId(SelectionUserDataDialogFragment.newInstance(this, value), key);
        }
    }

    private void openRequestSelectionRegistrationDialog(String value) {
        mDialogsManager.showDialogWithId(RequestSelectionRegistrationDialogFragment.newInstance(), value);
    }
}
