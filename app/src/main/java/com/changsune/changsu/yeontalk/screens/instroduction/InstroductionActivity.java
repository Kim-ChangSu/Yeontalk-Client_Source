package com.changsune.changsu.yeontalk.screens.instroduction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;
import com.changsune.changsu.yeontalk.screens.profile.ProfileActivity;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;
import com.changsune.changsu.yeontalk.screens.profile.ProfileActivity;

public class InstroductionActivity extends AppCompatActivity implements UpdateMeDataUseCase.Listener {

    // Static Variable (a~z)------------------------------------------------------------------------
    public static final String EXTRA_INTRODUCTION = "EXTRA_INTRODUCTION";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // CircleImageView(a~z)

    // CrystalRangeSeekBar(a~z)

    // EditText(a~z)
    private EditText mEditText;

    // ImageButton(a~z)

    // LinearLayout(a~z)

    // NestedScrollView(a~z)

    // RecyclerView(a~z)

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TextView(a~z)
    private TextView mTextView_ok_toolbar;
    private TextView mTextView_num;

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)

    // Boolean(a~z)

    // DialogManager(a~z)

    // ImageLoader(a~z)

    // InputMethodManager(a~z)
    private InputMethodManager mInputMethodManager;

    // LinearLayoutManager(a~z)

    // Networking(a~z)
    private UpdateMeDataUseCase mUpdateMeDataUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)

    // SharedPreference(a~z)
    private SharedPreferences mSharedPreferences;

    // Socket(a~z)

    // String(a~z)
    private String mUserId;
    private String mIntroduction;

    // TextWatcher(a~z)
    private TextWatcher mTextWatcher;

    // Thread(a~z)

    // VideoKit(a~z)

    // Extra Class(a~z)

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context, String userId, String instroduction) {
        Intent intent =  new Intent(context, InstroductionActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_INTRODUCTION, instroduction);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instroduction);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // CircleImageView(a~z)

        // CrystalRangeSeekBar(a~z)

        // EditText(a~z)
        mEditText = findViewById(R.id.edittext_introduction_id);

        // ImageButton(a~z)

        // LinearLayout(a~z)

        // NestedScrollView(a~z)

        // RecyclerView(a~z)

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TextView(a~z)
        mTextView_num = findViewById(R.id.textview_num_introduction_id);
        mTextView_ok_toolbar = findViewById(R.id.textview_ok_toolbar_id);

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)

        // DialogManager (a~z)

        // ImageLoader (a~z)

        // InputMethodManager(a~z)
        mInputMethodManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);

        // LayoutManager (a~z)

        // Networking (a~z)
        mUpdateMeDataUseCase = new UpdateMeDataUseCase();

        // RecyclerViewAdapter (a~z)

        // SharedPreference(a~z)
        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);

        // String (a~z)

        // TextWatcher(a~z)

        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String introductionInput = mEditText.getText().toString().trim();
                if (mIntroduction.equals(introductionInput)) {
                    mTextView_ok_toolbar.setTextColor(getResources().getColor(R.color.lightGray));
                } else {
                    mTextView_ok_toolbar.setTextColor(Color.BLACK);
                    mTextView_ok_toolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onOkButtonClicked(introductionInput);
                        }
                    });
                }
                if (s.length() > 0) {
                    mTextView_num.setVisibility(View.VISIBLE);
                    mTextView_num.setText(s.length()+"/60");
                } else {
                    mTextView_num.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        // Thread(a~z)

        // Extra Class(a~z)

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------
        mUserId = getIntent().getExtras().getString(EXTRA_USER_ID);
        mIntroduction = getIntent().getExtras().getString(EXTRA_INTRODUCTION);
        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------------

        // BottomNavView

        // Button

        // CircleImageView(a~z)

        // CrystalRangeSeekBar

        // EditText(a~z)
        if (!mIntroduction.equals("")) {
            mEditText.setText(mIntroduction);
        }
        mEditText.requestFocus();
        mEditText.addTextChangedListener(mTextWatcher);

        // LinearLayout(a~z)

        // RecyclerView(a~z)

        // TabHost

        // TextView(a~z)

        // Toolbar
        setUpToolbar();

        //------------------------------------------------------------------------------------------
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mUpdateMeDataUseCase.registerListener(this);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUpdateMeDataUseCase.unregisterListener(this);
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // UpdateMeDataUseCase.Listener
    @Override
    public void onUpdateMeDataUseCaseSucceeded(String data_key, String data_value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_INSTRODUCTION, data_value);
        editor.commit();
        mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        ProfileActivity.start(this);
        finish();
    }

    @Override
    public void onUpdateMeDataUseCaseFailed() {

    }
    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    private void onOkButtonClicked(String introduction) {
        mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.INSTRODUCTION, introduction);

    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("자기 소개");
        mTextView_ok_toolbar.setVisibility(View.VISIBLE);

    }

    // ---------------------------------------------------------------------------------------------
}
