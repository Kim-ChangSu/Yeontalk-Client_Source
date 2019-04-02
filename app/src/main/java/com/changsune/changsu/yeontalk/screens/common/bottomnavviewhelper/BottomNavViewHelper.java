package com.changsune.changsu.yeontalk.screens.common.bottomnavviewhelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.changsune.changsu.yeontalk.screens.chatroom.ChatRoomActivity;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.chatroom.ChatRoomActivity;
import com.changsune.changsu.yeontalk.screens.more.MoreActivity;
import com.changsune.changsu.yeontalk.screens.users.UsersActivity;

public class BottomNavViewHelper {

    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView){

    }

    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_person:
                        UsersActivity.start(context);
                        break;

                    case R.id.ic_chat:
                        Intent intent2  = new Intent(context, ChatRoomActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_more:
                        Intent intent3 = new Intent(context, MoreActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                }


                return false;
            }
        });
    }
}
