package com.changsune.changsu.yeontalk.screens.more;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.screens.common.bottomnavviewhelper.BottomNavViewHelper;
import com.changsune.changsu.yeontalk.screens.setting.SettingActivity;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.billing.BillingActivity;
import com.changsune.changsu.yeontalk.screens.common.bottomnavviewhelper.BottomNavViewHelper;
import com.changsune.changsu.yeontalk.screens.profile.ProfileActivity;
import com.changsune.changsu.yeontalk.screens.setting.SettingActivity;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;

public class MoreActivity extends AppCompatActivity {

    public LinearLayout mLinearLayout_myProfile;
    public LinearLayout mLinearLayout_setting;
    LinearLayout mLinearLayout_billing;

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, MoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mLinearLayout_myProfile = (LinearLayout) findViewById(R.id.linearlayout_myprofile_moreactivity_id);
        mLinearLayout_setting = (LinearLayout) findViewById(R.id.linearlayout_setting_moreactivity_id);
        mLinearLayout_billing = findViewById(R.id.linearlayout_billing_moreactivity_id);


        mLinearLayout_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileButtionClicked();
            }
        });

        mLinearLayout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingButtonClicked();
            }
        });

        mLinearLayout_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBillingButtonClicked();
            }
        });

        setupBottomNavigationView();

        setUpToolbar();
    }

    private void onBillingButtonClicked() {
        BillingActivity.start(this);
    }


    private void onSettingButtonClicked() {
        SettingActivity.start(this);
    }

    private void onProfileButtionClicked() {
        ProfileActivity.start(this);
    }


    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("기타");

    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        BottomNavViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavViewHelper.enableNavigation(this, bottomNavigationView);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }
}
