package com.changsune.changsu.yeontalk.screens.registrationindex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.registrationcheck.RegistrationCheckActivity;

public class RegistrationIndexActivity extends AppCompatActivity {

    Button mButton;

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, RegistrationIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_index);

        mButton = findViewById(R.id.button_registration_index_id);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationCheckActivity.start(RegistrationIndexActivity.this);
                finish();
            }
        });

    }
}
