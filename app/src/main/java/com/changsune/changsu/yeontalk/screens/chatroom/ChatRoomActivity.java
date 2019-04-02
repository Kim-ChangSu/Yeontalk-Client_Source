package com.changsune.changsu.yeontalk.screens.chatroom;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.changsune.changsu.yeontalk.chatroom.FetchChatRoomListUseCase;
import com.changsune.changsu.yeontalk.ChatService;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.changsune.changsu.yeontalk.chatroom.FetchChatRoomListUseCase;
import com.changsune.changsu.yeontalk.screens.common.bottomnavviewhelper.BottomNavViewHelper;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity implements FetchChatRoomListUseCase.Listener {

    // Static Variable (a~z)------------------------------------------------------------------------

    private static final String TAG = "ChatRoomActivity";

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // RecyclerView(a~z)

    RecyclerView mRecyclerView;

    // SwipeRefreshLayout(a~z)

    // TextView(a~z)

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // ArrayList (a~z)

    public static ArrayList<ChatRoom> mChatRoomArrayList;

    // Context (a~z)

    Context mContext;

    // Dialog (a~z)

    // ImageLoader (a~z)

    ImageLoader mImageLoader;

    // LayoutManager (a~z)

    LinearLayoutManager mLinearLayoutManager;

    // Networking (a~z)

    FetchChatRoomListUseCase mFetchChatRoomListUseCase;

    // RecyclerViewAdapter (a~z)

    public static ChatRoomRecyclerViewAdapter mChatRoomRecyclerViewAdapter;

    // SharedPreference
    SharedPreferences mSharedPreferences;

    // String (a~z)
    String mMeId;

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
        setContentView(R.layout.activity_chat_room);

        // Receiving Data from SharedPreference ----------------------------------------------------
        // SharedPreference(a~z)
        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mMeId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, null);
        //------------------------------------------------------------------------------------------

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // RecyclerView(a~z)

        mRecyclerView = findViewById(R.id.recycler_view_chat_list_id);

        // SwipeRefreshLayout(a~z)

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)

        mChatRoomArrayList = new ArrayList<>();

        // Dialog (a~z)

        // Image (a~z)

        mImageLoader = new ImageLoader(this);

        // LayoutManager (a~z)

        mLinearLayoutManager = new LinearLayoutManager(this);

        // Networking (a~z)

        mFetchChatRoomListUseCase = new FetchChatRoomListUseCase();

        // RecyclerViewAdapter (a~z)

        mChatRoomRecyclerViewAdapter = new ChatRoomRecyclerViewAdapter(this, mChatRoomArrayList, mImageLoader, mMeId);

        // String (a~z)

        // Extra

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        mFetchChatRoomListUseCase.fetchChatRoomListAndNotify(mMeId);

        // -----------------------------------------------------------------------------------------

        // Setting Up Toolbar ----------------------------------------------------------------------

        setUpToolbar();

        setupBottomNavigationView();

        //------------------------------------------------------------------------------------------

        // Setting RecyclerView --------------------------------------------------------------------

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mChatRoomRecyclerViewAdapter);

        // -----------------------------------------------------------------------------------------

        // Setting OnClickListener in Button -------------------------------------------------------

        // -----------------------------------------------------------------------------------------
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mFetchChatRoomListUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchChatRoomListUseCase.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mFetchChatRoomListUseCase.fetchChatRoomListAndNotify(mMeId);
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // UploadFileWithChatUseCase Listener

    @Override
    public void onFetchChatRoomListUseCaseSucceeded(List<ChatRoom> list) {
        mChatRoomArrayList.clear();
        mChatRoomArrayList.addAll(list);
        mChatRoomRecyclerViewAdapter.notifyDataSetChanged();
        Log.e(TAG, "onFetchChatListUseCaseSucceeded" + mChatRoomRecyclerViewAdapter.getItemCount());

    }

    @Override
    public void onFetchChatRoomListUseCaseFailed() {

        Log.e(TAG, "onFetchChatListUseCaseFailed");

    }

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("대화");

    }


    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        BottomNavViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavViewHelper.enableNavigation(this, bottomNavigationView);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }
}
