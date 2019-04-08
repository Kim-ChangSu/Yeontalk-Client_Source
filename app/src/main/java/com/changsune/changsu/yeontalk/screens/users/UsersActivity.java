package com.changsune.changsu.yeontalk.screens.users;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.ChatService;
import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.receiver.NetworkChangeReceiver;
import com.changsune.changsu.yeontalk.screens.common.bottomnavviewhelper.BottomNavViewHelper;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.userdetails.UserDetailsActivity;
import com.changsune.changsu.yeontalk.user.FetchUsersImageListUseCase;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.user.FetchUserListUseCase;
import com.changsune.changsu.yeontalk.user.User;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UsersRecyclerViewAdapter.OnUsersItemClickListener
        , FetchUserListUseCase.Listener, MeRecyclerViewAdapter.OnMeItemClickListener, UsersImageRecyclerViewAdapter.OnItemClickListener
        , FetchUsersImageListUseCase.Listener {

    private static final String TAG = "UsersActivity";

    // Static Variable (a~z)------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // LinearLayout(a~z)
    LinearLayout mLinearLayout_favorites_text;

    // NestedScrollView(a~z)
    NestedScrollView mNestedScrollView_firstTab;
    NestedScrollView mNestedScrollView_secondTab;

    // ProgressBar(a~z)
    ProgressBar mProgressBar_firstTab;
    ProgressBar mProgressBar_secondTab;

    // RecyclerView(a~z)
    RecyclerView mRecyclerView_me;
    RecyclerView mRecyclerView_favorites;
    RecyclerView mRecyclerView_users;
    RecyclerView mRecyclerView_images;
    RecyclerView mRecyclerView_chatbot;

    // SwipeRefreshLayout(a~z)
    SwipeRefreshLayout mSwipeRefreshLayout_firstTab;
    SwipeRefreshLayout mSwipeRefreshLayout_secondTab;

    // TabHost(a~z)
    TabHost mTabHost;

    // TextView(a~z)

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)
    ArrayList<Me> mList_me;
    ArrayList<User> mList_favorites;
    ArrayList<User> mList_users;
    ArrayList<User> mList_images;
    ArrayList<User> mList_chatbot;

    // Boolean(a~z)
    Boolean mBoolean_isPossibleLoadMore_users;
    Boolean mBoolean_isPossibleLoadMore_users_image;

    Boolean mBoolean_lockList_users;
    Boolean mBoolean_lockList_users_image;


    Boolean mBoolean_isBoundedChatService;


    // DialogManager(a~z)
    DialogsManager mDialogsManager;

    // GridLayoutManager(a~z)
    GridLayoutManager mGridLayoutManager;

    // ImageLoader(a~z)
    ImageLoader mImageLoader;

    // IntentFilter(a~z)
    IntentFilter mFilter;

    // LinearLayoutManager(a~z)
    LinearLayoutManager mLinearLayoutManager_me;
    LinearLayoutManager mLinearLayoutManager_favorites;
    LinearLayoutManager mLinearLayoutManager_users;
    LinearLayoutManager mLinearLayoutManager_chatbot;

    // Networking(a~z)
    FetchUserListUseCase mFetchUserListUseCase;
    FetchUsersImageListUseCase mFetchUsersImageListUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)
    MeRecyclerViewAdapter mMeRecyclerViewAdapter;
    UsersRecyclerViewAdapter mUsersRecyclerViewAdapter_favorites;
    UsersRecyclerViewAdapter mUsersRecyclerViewAdapter_users;
    UsersRecyclerViewAdapter mUsersRecyclerViewAdapter_chatbot;
    UsersImageRecyclerViewAdapter mUsersImageRecyclerViewAdapter;

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences_setting;
    SharedPreferences mSharedPreferences_profile;
    SharedPreferences mSharedPreferences_images;

    // Socket(a~z)

    // String(a~z)
    private String mGender;
    private String mMaxAge;
    private String mMinAge;
    private String mNation;
    private String mRegion;

    // Socket(a~z)

    // Thread(a~z)

    // VideoKit(a~z)

    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    NetworkChangeReceiver mNetworkChangeReceiver;

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, UsersActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)
        mLinearLayout_favorites_text = findViewById(R.id.linearlayout_favorites_text_id);

        // NestedScrollView(a~z)
        mNestedScrollView_firstTab = findViewById(R.id.nestedscrollview);
        mNestedScrollView_secondTab = findViewById(R.id.nestedscrollview_images);

        // ProgressBar(a~z)
        mProgressBar_firstTab = findViewById(R.id.bottom_progressbar_id);
        mProgressBar_secondTab = findViewById(R.id.bottom_progressbar_images_id);

        // RecyclerView(a~z)
        mRecyclerView_me = findViewById(R.id.recyclerview_myprofile_id);
        mRecyclerView_favorites = findViewById(R.id.recyclerview_favoriteslist_id);
        mRecyclerView_users = findViewById(R.id.recyclerview_userslist_id);
        mRecyclerView_chatbot = findViewById(R.id.recyclerview_chatbot_id);
        mRecyclerView_images = findViewById(R.id.recyclerview_users_image_list_id);

        // SwipeRefreshLayout(a~z)
        mSwipeRefreshLayout_firstTab = findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout_secondTab = findViewById(R.id.swiperefreshlayout_images);

        // TabHost(a~z)

        mTabHost = findViewById(R.id.tabHost);

        // TextView(a~z)

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        mList_me = new ArrayList<>();
        mList_favorites = new ArrayList<>();
        mList_users = new ArrayList<>();
        mList_images = new ArrayList<>();
        mList_chatbot = new ArrayList<>();

        mBoolean_lockList_users = false;
        mBoolean_lockList_users_image = false;

        // DialogManager (a~z)
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        // GridLayoutManager(a~z)
        mGridLayoutManager = new GridLayoutManager(this, 3);

        // ImageLoader (a~z)
        mImageLoader = new ImageLoader(this);

        // LayoutManager (a~z)
        mLinearLayoutManager_me = new LinearLayoutManager(this);
        mLinearLayoutManager_favorites = new LinearLayoutManager(this);
        mLinearLayoutManager_users = new LinearLayoutManager(this);
        mLinearLayoutManager_chatbot = new LinearLayoutManager(this);

        // Networking (a~z)
        mFetchUserListUseCase = new FetchUserListUseCase();
        mFetchUsersImageListUseCase = new FetchUsersImageListUseCase();

        // RecyclerViewAdapter (a~z)
        mMeRecyclerViewAdapter = new MeRecyclerViewAdapter(this, mList_me, this, mImageLoader);
        mUsersRecyclerViewAdapter_favorites = new UsersRecyclerViewAdapter(this, mList_favorites, this, mImageLoader);
        mUsersRecyclerViewAdapter_users = new UsersRecyclerViewAdapter(this, mList_users, this, mImageLoader);
        mUsersRecyclerViewAdapter_chatbot = new UsersRecyclerViewAdapter(this, mList_chatbot, this, mImageLoader);
        mUsersImageRecyclerViewAdapter = new UsersImageRecyclerViewAdapter(this, mList_images, this, mImageLoader);


        // SharedPreference(a~z)
        mSharedPreferences_setting = getSharedPreferences(Constants.SHAREDPREF_KEY_SETTING, MODE_PRIVATE);
        mSharedPreferences_profile = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mSharedPreferences_images = getSharedPreferences(Constants.SHAREDPREF_KEY_IMAGES, MODE_PRIVATE);

        // String (a~z)

        // Thread (a~z)

        // Extra

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        mGender = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_GENDER, "");
        mMaxAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, "100");
        mMinAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, "0");
        mNation = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_NATION, "");
        mRegion = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_REGION, "");

        // -----------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------
        mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
                Constants.LOAD_LIMIT,
                "",
                mGender,
                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                mNation,
                mRegion);

        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------------

        // BottomNavView
        setUpBottomNavigationView();

        // Button

        // LinearLayout(a~z)

        // NestedScrollView(a~z)
        mNestedScrollView_firstTab.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

                if (i1 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && mBoolean_lockList_users == false) {

                    if (mBoolean_isPossibleLoadMore_users) {
                        mBoolean_lockList_users = true;

                        mProgressBar_firstTab.setVisibility(View.VISIBLE);
                        String user_login_time_loaded_last = mList_users.get(mList_users.size()-1).getUserLogInTime();
                        // Receiving Data from DataBase ------------------------------------------------------------
                        mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
                                "30",
                                user_login_time_loaded_last,
                                mGender,
                                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                                mNation,
                                mRegion);
                        // -----------------------------------------------------------------------------------------
                    } else {
                        mBoolean_lockList_users = false;
                    }
                }
            }
        });

        mNestedScrollView_secondTab.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && mBoolean_lockList_users_image == false) {

                    if (mBoolean_isPossibleLoadMore_users_image) {
                        mBoolean_lockList_users_image = true;
                        mProgressBar_secondTab.setVisibility(View.VISIBLE);
                        String user_login_time_loaded_last = mList_images.get(mList_images.size()-1).getUserLogInTime();
                        // Receiving Data from DataBase ------------------------------------------------------------
                        mFetchUsersImageListUseCase.fetchUsersImageListAndNotify(mList_me.get(0).getMeId(),
                                "24",
                                user_login_time_loaded_last,
                                mGender,
                                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                                String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                                mNation,
                                mRegion);
                        // -----------------------------------------------------------------------------------------
                    } else {
                        mBoolean_lockList_users_image = false;
                    }
                }
            }
        });

        // RecyclerView(a~z)

        // me
        setUpMeRecyclerView(mRecyclerView_me, mLinearLayoutManager_me, mMeRecyclerViewAdapter);

        // favorites
        setUpUsersRecyclerView(mRecyclerView_favorites, mLinearLayoutManager_favorites, mUsersRecyclerViewAdapter_favorites);

        // users
        setUpUsersRecyclerView(mRecyclerView_users, mLinearLayoutManager_users, mUsersRecyclerViewAdapter_users);

        // chatBot
        setUpUsersRecyclerView(mRecyclerView_chatbot, mLinearLayoutManager_chatbot, mUsersRecyclerViewAdapter_chatbot);

        // users_image
        mRecyclerView_images.setLayoutManager(mGridLayoutManager);
        mRecyclerView_images.setAdapter(mUsersImageRecyclerViewAdapter);

        // SwipeRefreshLayout(a~z)
        mSwipeRefreshLayout_firstTab.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
                        Constants.LOAD_LIMIT,
                        "",
                        mGender,
                        String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                        String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                        mNation,
                        mRegion);

            }
        });
        mSwipeRefreshLayout_secondTab.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFetchUsersImageListUseCase.fetchUsersImageListAndNotify(mList_me.get(0).getMeId(),
                        Constants.LOAD_LIMIT,
                        "",
                        mGender,
                        String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                        String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                        mNation,
                        mRegion);

            }
        });

        // TabHost(a~z)
        setUpTabHost(mTabHost);

        // Toolbar
        setUpToolbar();

        //------------------------------------------------------------------------------------------

        mNetworkChangeReceiver = new NetworkChangeReceiver();
        mFilter = new IntentFilter();
        mFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mFetchUserListUseCase.registerListener(this);
        mFetchUsersImageListUseCase.registerListener(this);

        registerReceiver(mNetworkChangeReceiver, mFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchUserListUseCase.unregisterListener(this);
        mFetchUsersImageListUseCase.unregisterListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mNetworkChangeReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Receiving Data from SharedPreference ----------------------------------------------------

        mGender = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_GENDER, "");
        mMaxAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, "100");
        mMinAge = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, "0");
        mNation = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_NATION, "");
        mRegion = mSharedPreferences_setting.getString(Constants.SHAREDPREF_KEY_SETTING_REGION, "");

        // -----------------------------------------------------------------------------------------

        if (mTabHost.getCurrentTab() == 0) {
            // Receiving Data from DataBase ------------------------------------------------------------
            mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
                    Constants.LOAD_LIMIT,
                    "",
                    mGender,
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                    mNation,
                    mRegion);
            // -----------------------------------------------------------------------------------------
        } else {
            mFetchUsersImageListUseCase.fetchUsersImageListAndNotify(mList_me.get(0).getMeId(),
                    Constants.LOAD_LIMIT,
                    "",
                    mGender,
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                    mNation,
                    mRegion);
        }


    }

    // Setting Listener implemented ----------------------------------------------------------------

    // UsersRecyclerViewAdapter.Listener
    @Override
    public void onUsersItemClick(int position, String userId) {

        boolean isFavorite = false;
        for (int i = 0; i < mList_favorites.size(); i++) {
            if (userId.equals(mList_favorites.get(i).getUserId())) {
                isFavorite = true;
            }
        }
        UserDetailsActivity.start(this, userId, mList_me.get(0).getMeId(), isFavorite);
    }

    // MeRecyclerViewAdapter.Listener
    @Override
    public void onMeItemClick(int position, String userId) {
        boolean isFavorite = false;
        UserDetailsActivity.start(this, userId, mList_me.get(0).getMeId(), isFavorite);
    }

    // FetchUserListUseCase.Listener
    @Override
    public void onFetchUserListUseCaseSucceeded(List<Me> list_me, List<User> list_chatbot, List<User> list_favorites, List<User> list_users, String user_login_time_loaded_last) {
        if (list_users.size() < Integer.valueOf(Constants.LOAD_LIMIT)) {
            mBoolean_isPossibleLoadMore_users = false;
        } else {
            mBoolean_isPossibleLoadMore_users = true;
        }

        if (user_login_time_loaded_last !=null && !user_login_time_loaded_last.equals("")) {
            mProgressBar_firstTab.setVisibility(View.GONE);
            Log.e(TAG, "onFetchUserListUseCaseSucceeded: " + list_users.size());
            loadMoreUsersRecyclerViewData(mList_users, list_users, mUsersRecyclerViewAdapter_users);
            mBoolean_lockList_users = false;

        } else {
            resetMeRecyclerViewData(mList_me, list_me, mMeRecyclerViewAdapter);
            resetUsersRecyclerViewData(mList_favorites, list_favorites, mUsersRecyclerViewAdapter_favorites);
            resetUsersRecyclerViewData(mList_users, list_users, mUsersRecyclerViewAdapter_users);
            resetUsersRecyclerViewData(mList_chatbot, list_chatbot, mUsersRecyclerViewAdapter_chatbot);

            if (mSwipeRefreshLayout_firstTab.isRefreshing()) {
                mSwipeRefreshLayout_firstTab.setRefreshing(false);
            }
            if (!isMyServiceRunning(ChatService.class)) {
                startService(list_me.get(0).getMeId());
            }
        }

        SharedPreferences.Editor editor = mSharedPreferences_profile.edit();
        editor.clear();
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, mList_me.get(0).getMeId());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NICKNAME, mList_me.get(0).getMeNickname());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_GENDER, mList_me.get(0).getMeGender());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_BIRTHYEAR, mList_me.get(0).getMeBirthYear());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NATION, mList_me.get(0).getMeNation());
        if (!mList_me.get(0).getMeRegion().equals("")) {
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_REGION, mList_me.get(0).getMeRegion());
        }
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_IMAGE, mList_me.get(0).getMeProfileImage());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_INSTRODUCTION, mList_me.get(0).getMeIntroduction());
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_POINT, mList_me.get(0).getMePoint());
        editor.commit();

        SharedPreferences.Editor editor1 = mSharedPreferences_images.edit();
        editor1.clear();
        for (int i = 0; i < mList_me.get(0).getMeImages().size(); i++) {
            editor1.putString(String.valueOf(i),  mList_me.get(0).getMeImages().get(i).getImageId()+">"+ mList_me.get(0).getMeImages().get(i).getImageUrl());
        }
        editor1.commit();

    }


    @Override
    public void onFetchUserListUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // UsersImageRecyclerViewAdapter.Listener

    @Override
    public void onItemClick(int position, String userId) {

        boolean isFavorite = false;
        for (int i = 0; i < mList_favorites.size(); i++) {
            if (userId.equals(mList_favorites.get(i).getUserId())) {
                isFavorite = true;
            }
        }
        UserDetailsActivity.start(this, userId, mList_me.get(0).getMeId(), isFavorite);

    }

    // FetchUsersImageListUseCase.Listener
    @Override
    public void onFetchUsersImageListUseCaseSucceeded(List<User> list_users_image, String user_login_time_loaded_last) {
        if (list_users_image.size() < Integer.valueOf(Constants.LOAD_LIMIT)) {
            mBoolean_isPossibleLoadMore_users_image = false;
        } else {
            mBoolean_isPossibleLoadMore_users_image = true;
        }

        if (user_login_time_loaded_last !=null && !user_login_time_loaded_last.equals("")) {
            mProgressBar_secondTab.setVisibility(View.GONE);
            mList_images.addAll(list_users_image);
            mUsersImageRecyclerViewAdapter.notifyDataSetChanged();
            mBoolean_lockList_users_image = false;
        } else {
            mList_images.clear();
            mList_images.addAll(list_users_image);
            mUsersImageRecyclerViewAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout_secondTab.isRefreshing()) {
                mSwipeRefreshLayout_secondTab.setRefreshing(false);
            }
        }


    }

    @Override
    public void onFetchUsersImageListUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // Tab
    private void onTab1Clicked() {
        mTabHost.setCurrentTab(0);
        if (!mSwipeRefreshLayout_firstTab.isRefreshing()) {
            mSwipeRefreshLayout_firstTab.setRefreshing(true);
            // Receiving Data from DataBase ------------------------------------------------------------
            mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
                    Constants.LOAD_LIMIT,
                    "",
                    mGender,
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                    mNation,
                    mRegion);
            // -----------------------------------------------------------------------------------------
        }

    }

    private void onTab2Clicked() {
        mTabHost.setCurrentTab(1);
        if (!mSwipeRefreshLayout_secondTab.isRefreshing()) {
            mSwipeRefreshLayout_secondTab.setRefreshing(true);
            mFetchUsersImageListUseCase.fetchUsersImageListAndNotify(mList_me.get(0).getMeId(),
                    Constants.LOAD_LIMIT,
                    "",
                    mGender,
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMinAge)),
                    String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(mMaxAge)),
                    mNation,
                    mRegion);
        }
    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("친구");

    }

    private void setUpBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        BottomNavViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavViewHelper.enableNavigation(this, bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    private void setUpTabHost(TabHost tabHost_casted) {
        TabHost tabHost = tabHost_casted;
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("목록");
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("사진");
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTab1Clicked();
            }
        });
        tabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTab2Clicked();
            }
        });
    }

    private void setUpUsersRecyclerView(RecyclerView recyclerView_casted,
                                        LinearLayoutManager linearLayoutManager_constructed,
                                        UsersRecyclerViewAdapter usersRecyclerViewAdapter_constructed) {
        recyclerView_casted.setLayoutManager(linearLayoutManager_constructed);
        recyclerView_casted.setHasFixedSize(true);
        recyclerView_casted.setAdapter(usersRecyclerViewAdapter_constructed);
    }

    private void setUpMeRecyclerView(RecyclerView recyclerView_casted,
                                        LinearLayoutManager linearLayoutManager_constructed,
                                        MeRecyclerViewAdapter meRecyclerViewAdapter_constructed) {
        recyclerView_casted.setLayoutManager(linearLayoutManager_constructed);
        recyclerView_casted.setHasFixedSize(true);
        recyclerView_casted.setAdapter(meRecyclerViewAdapter_constructed);
    }



    private void resetUsersRecyclerViewData(ArrayList<User> list, List<User> list_new, UsersRecyclerViewAdapter usersRecyclerViewAdapter) {

        if (usersRecyclerViewAdapter == mUsersRecyclerViewAdapter_favorites) {
            list.clear();
            list.addAll(list_new);
            usersRecyclerViewAdapter.notifyDataSetChanged();
            if (list.size() > 0) {
                mLinearLayout_favorites_text.setVisibility(View.VISIBLE);
            } else {
                mLinearLayout_favorites_text.setVisibility(View.GONE);
            }

        } else {
            list.clear();
            list.addAll(list_new);
            usersRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    private void loadMoreUsersRecyclerViewData(ArrayList<User> list_users, List<User> list_users1, UsersRecyclerViewAdapter usersRecyclerViewAdapter_users) {
        list_users.addAll(list_users1);
        usersRecyclerViewAdapter_users.notifyDataSetChanged();
    }

    private void resetMeRecyclerViewData(ArrayList<Me> list, List<Me> list_new, MeRecyclerViewAdapter meRecyclerViewAdapter) {
        list.clear();
        list.addAll(list_new);
        meRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void startService(String meId){

        if(!isMyServiceRunning(ChatService.class)){
            ChatService.start(this, meId);
        } else {

        }

    }


    //서비스 러닝 확인
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
