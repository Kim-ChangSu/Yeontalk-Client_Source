package com.changsune.changsu.yeontalk.screens.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.ChatService;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.me.SecessionUseCase;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionRegistrationDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionSessionDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SettingFriendDialogFragment;
import com.changsune.changsu.yeontalk.screens.registrationindex.RegistrationIndexActivity;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.changsune.changsu.yeontalk.R;

public class SettingActivity extends AppCompatActivity implements SettingFriendDialogFragment.Listener
        , RequestSelectionSessionDialogFragment.Listener, SecessionUseCase.Listener{

    // Static Variable (a~z)------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)
    private Button mButton_secession;

    // CrystalRangeSeekBar(a~z)
    private CrystalRangeSeekbar mCrystalRangeSeekbar;

    // EditText(a~z)

    // ImageButton(a~z)

    // LinearLayout(a~z)
    private LinearLayout mLayout_setting_gender;
    private LinearLayout mLayout_setting_nation;
    private LinearLayout mLayout_setting_region;

    // NestedScrollView(a~z)

    // RecyclerView(a~z)

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TextView(a~z)
    private TextView mTextView_setting_gender;
    private TextView mTextView_setting_nation;
    private TextView mTextView_setting_region;
    private TextView mTextView_setting_max_age;
    private TextView mTextView_setting_min_age;

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)

    // DialogManager(a~z)
    DialogsManager mDialogsManager;

    // ImageLoader(a~z)

    // LinearLayoutManager(a~z)

    // Networking(a~z)
    SecessionUseCase mSecessionUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences_setting;
    SharedPreferences mSharedPreferences_profile;
    SharedPreferences mSharedPreferences_images;
    SharedPreferences mSharedPreferences_chat_uploading;


    // Socket(a~z)

    // String(a~z)
    private String mGender;
    private String mMaxAge;
    private String mMinAge;
    private String mNation;
    private String mRegion;

    // Thread(a~z)

    // VideoKit(a~z)

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -----------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)
        mButton_secession = findViewById(R.id.button_secession_id);

        // CrystalRangeSeekBar(a~z)
        mCrystalRangeSeekbar = findViewById(R.id.rangeSeekbar);

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)
        mLayout_setting_gender = findViewById(R.id.layout_setting_gender_id);
        mLayout_setting_nation = findViewById(R.id.layout_setting_nation_id);
        mLayout_setting_region = findViewById(R.id.layout_setting_region_id);

        // NestedScrollView(a~z)

        // RecyclerView(a~z)

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TextView(a~z)
        mTextView_setting_gender = findViewById(R.id.textview_setting_gender_id);
        mTextView_setting_nation = findViewById(R.id.textview_setting_nation_id);
        mTextView_setting_region = findViewById(R.id.textview_setting_region_id);
        mTextView_setting_max_age = findViewById(R.id.textview_setting_max_age_id);
        mTextView_setting_min_age = findViewById(R.id.textview_setting_min_age_id);

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)

        // DialogManager (a~z)
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        // ImageLoader (a~z)

        // LayoutManager (a~z)

        // Networking (a~z)
        mSecessionUseCase = new SecessionUseCase();

        // RecyclerViewAdapter (a~z)

        // SharedPreference(a~z)
        mSharedPreferences_setting = getSharedPreferences(Constants.SHAREDPREF_KEY_SETTING, MODE_PRIVATE);
        mSharedPreferences_profile = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mSharedPreferences_images = getSharedPreferences(Constants.SHAREDPREF_KEY_IMAGES, MODE_PRIVATE);
        mSharedPreferences_chat_uploading = getSharedPreferences(Constants.SHAREDPREF_KEY_CHAT_UPLOADING, MODE_PRIVATE);

        // String (a~z)

        // Thread (a~z)

        // Extra

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        mGender = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_GENDER, null);
        mMaxAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, null);
        mMinAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, null);
        mNation = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_NATION, null);
        mRegion = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_REGION, null);


        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------

        // BottomNavView

        // Button
        mButton_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestSelectionSessionDialog();
            }
        });

        // CrystalRangeSeekBar
        if (mMinAge != null) {
            mCrystalRangeSeekbar.setMinStartValue(Integer.valueOf(mMinAge));
        }
        if (mMaxAge != null) {
            mCrystalRangeSeekbar.setMaxStartValue(Integer.valueOf(mMaxAge));
        }

        mCrystalRangeSeekbar.apply();
        mCrystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                onRangeSeekBarValueChanged(minValue, maxValue);
            }
        });

        mCrystalRangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                onRangeSeekBarValueChangeFinished(minValue, maxValue);

            }
        });

        // LinearLayout(a~z)
        mLayout_setting_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingFriendDialog("성별", mGender);
            }
        });

        mLayout_setting_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingFriendDialog("국가", mNation);

            }
        });

        mLayout_setting_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNation == null) {
                    openRequestSelectionSettingDialog("국가먼저");
                } else {
                    openSettingFriendDialog("지역", mRegion);
                }
            }
        });

        // RecyclerView(a~z)

        // TabHost

        // TextView(a~z)
        if (mGender != null) {
            mTextView_setting_gender.setText(mGender);
        }
        if (mMinAge != null) {
            mTextView_setting_min_age.setText(mMinAge);
        }
        if (mMaxAge != null) {
            mTextView_setting_max_age.setText(mMaxAge);
        }
        if (mNation != null) {
            mTextView_setting_nation.setText(mNation);
        }
        if (mRegion != null) {
            mTextView_setting_region.setText(mRegion);
        }

        // Toolbar

        setUpToolbar();

        //------------------------------------------------------------------------------------------
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mSecessionUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSecessionUseCase.unregisterListener(this);

    }


    // Setting Listener implemented ----------------------------------------------------------------

    // SettingFriendDialogFragment.Listener
    @Override
    public void onPositiveButtonClicked(String key, String value) {

        SharedPreferences.Editor editor = mSharedPreferences_setting.edit();

        if (key.equals("성별")) {
            if (value == null) {
                mGender = value;
                mTextView_setting_gender.setText(R.string.setting_default);
            } else if(!value.equals(mGender)) {
                mGender = value;
                mTextView_setting_gender.setText(mGender);
            }
            editor.putString(Constants.SHAREDPREF_KEY_SETTING_GENDER, mGender);
            editor.commit();

        } else if (key.equals("국가")) {
            if (value == null) {
                mNation = value;
                mTextView_setting_nation.setText(R.string.setting_default);
                mRegion = null;
                mTextView_setting_region.setText(R.string.setting_default);
            } else if (!value.equals(mNation)) {
                mNation = value;
                mTextView_setting_nation.setText(mNation);
                mRegion = null;
                mTextView_setting_region.setText(R.string.setting_default);
            }
            editor.putString(Constants.SHAREDPREF_KEY_SETTING_NATION, mNation);
            editor.putString(Constants.SHAREDPREF_KEY_SETTING_REGION, mRegion);
            editor.commit();

        } else if (key.equals("지역")) {

            if (value == null) {
                mRegion = value;
                mTextView_setting_region.setText(R.string.setting_default);
            } else if(!value.equals(mRegion)) {
                mRegion = value;
                mTextView_setting_region.setText(mRegion);
            }
            editor.putString(Constants.SHAREDPREF_KEY_SETTING_REGION, mRegion);
            editor.commit();
        }

    }

    @Override
    public void onButtonSessionClicked() {

        String meId = mSharedPreferences_profile.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, "");
        mSecessionUseCase.secessionAndNotify(meId);

    }

    @Override
    public void onSecessionUseCaseSucceeded() {

        SharedPreferences.Editor editor_setting = mSharedPreferences_setting.edit();
        SharedPreferences.Editor editor_profile = mSharedPreferences_profile.edit();
        SharedPreferences.Editor editor_images = mSharedPreferences_images.edit();
        SharedPreferences.Editor editor_chat_uploading = mSharedPreferences_chat_uploading.edit();

        editor_setting.clear();
        editor_setting.commit();

        editor_profile.clear();
        editor_profile.commit();

        editor_images.clear();
        editor_images.commit();

        editor_chat_uploading.clear();
        editor_chat_uploading.commit();

        ChatService.stop(this);

        Intent intent = new Intent(this, RegistrationIndexActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);

    }

    @Override
    public void onSecessionUseCaseFailed() {

    }



    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    private void onRangeSeekBarValueChanged(Number minValue, Number maxValue) {
        if (String.valueOf(minValue).equals("50")) {
            mTextView_setting_min_age.setText("50+");
        } else {
            mTextView_setting_min_age.setText(String.valueOf(minValue));
        }
        if (String.valueOf(maxValue).equals("50")) {
            mTextView_setting_max_age.setText("50+");
        } else {
            mTextView_setting_max_age.setText(String.valueOf(maxValue));
        }
    }

    private void onRangeSeekBarValueChangeFinished(Number minValue, Number maxValue) {

        SharedPreferences.Editor editor = mSharedPreferences_setting.edit();

        if (String.valueOf(minValue).equals("19")) {
            mMinAge = null;
        } else {
            mMinAge = String.valueOf(minValue);
        }
        if (String.valueOf(maxValue).equals("50")) {
            mMaxAge = null;
        } else {
            mMaxAge = String.valueOf(maxValue);
        }

        editor.putString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, mMaxAge);
        editor.putString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, mMinAge);
        editor.commit();
    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("설정");

    }

    private void openSettingFriendDialog(String key, String value) {

        if (key.equals("지역")) {
            mDialogsManager.showDialogWithId(SettingFriendDialogFragment.newInstance(this, value, mNation), key);
        } else {
            mDialogsManager.showDialogWithId(SettingFriendDialogFragment.newInstance(this, value), key);
        }
    }

    private void openRequestSelectionSettingDialog(String value) {
        mDialogsManager.showDialogWithId(RequestSelectionRegistrationDialogFragment.newInstance(), value);
    }

    private void openRequestSelectionSessionDialog() {
        mDialogsManager.showDialogWithId(RequestSelectionSessionDialogFragment.newInstance(this), "");
    }

    // ---------------------------------------------------------------------------------------------
}
