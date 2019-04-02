package com.changsune.changsu.yeontalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.changsune.changsu.yeontalk.screens.chatroom.ChatRoomActivity;
import com.changsune.changsu.yeontalk.R;

public class ModelActivity extends AppCompatActivity {

    // Static Variable (a~z)------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // RecyclerView(a~z)

    // SwipeRefreshLayout(a~z)

    // TextView(a~z)

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // ArrayList (a~z)

    // Context (a~z)

    // Dialog (a~z)

    // ImageLoader (a~z)

    // LayoutManager (a~z)

    // Networking (a~z)

    // RecyclerViewAdapter (a~z)

    // String (a~z)

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, ChatRoomActivity.class);
        context.startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        // Setting Up Toolbar ----------------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // RecyclerView(a~z)

        // SwipeRefreshLayout(a~z)

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        // Dialog (a~z)
        // Image (a~z)

        // LayoutManager (a~z)

        // Networking (a~z)

        // RecyclerViewAdapter (a~z)

        // String (a~z)

        // Extra

        // -----------------------------------------------------------------------------------------

        // Setting RecyclerView --------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Setting OnClickListener in Button -------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

}
