package com.changsune.changsu.yeontalk.screens.userdetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.screens.chat.ChatActivity;
import com.changsune.changsu.yeontalk.screens.chatbot.ChatBotActivity;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.gallery.GalleryActivity;
import com.changsune.changsu.yeontalk.userdetails.DeleteFavoriteUseCase;
import com.changsune.changsu.yeontalk.userdetails.FetchUserDetailsUseCase;
import com.changsune.changsu.yeontalk.userdetails.InsertFavoriteUseCase;
import com.changsune.changsu.yeontalk.userdetails.UserDetails;
import com.changsune.changsu.yeontalk.R;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity implements FetchUserDetailsUseCase.Listener
        , InsertFavoriteUseCase.Listener, DeleteFavoriteUseCase.Listener, GalleryImageRecyclerViewAdapter.OnItemClickListener {

    // Static Variable (a~z)------------------------------------------------------------------------

    private static final String TAG = "UserDetailsActivity";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_USER_ID_ME = "EXTRA_USER_ID_ME";
    public static final String EXTRA_IS_FAVORITE = "EXTRA_IS_FAVORITE";

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)
    private Button mButton_insert_favorite;
    private Button mButton_delete_favorite;
    private Button mButton_chat;

    // CircleImageView(a~z)
    private CircleImageView mCircleImageView;

    // CrystalRangeSeekBar(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // LinearLayout(a~z)

    // NestedScrollView(a~z)

    // RecyclerView(a~z)
    RecyclerView mRecyclerView;

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TextView(a~z)
    private TextView mTextView_toolbar;
    private TextView mTextView_user_age;
    private TextView mTextView_user_gender;
    private TextView mTextView_user_introduction;
    private TextView mTextView_user_login_time;
    private TextView mTextView_user_nation;
    private TextView mTextView_user_nickname;
    private TextView mTextView_user_region;

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)
    ArrayList<String> mUserImages;

    // Boolean(a~z)
    Boolean mIsFavorite;

    // DialogManager(a~z)
    DialogsManager mDialogsManager;

    // ImageLoader(a~z)
    ImageLoader mImageLoader;

    // LinearLayoutManager(a~z)
    LinearLayoutManager mLinearLayoutManager;

    // Networking(a~z)
    FetchUserDetailsUseCase mFetchUserDetailsUseCase;
    InsertFavoriteUseCase mInsertFavoriteUseCase;
    DeleteFavoriteUseCase mDeleteFavoriteUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)
    GalleryImageRecyclerViewAdapter mGalleryImageRecyclerViewAdapter;

    // SharedPreference(a~z)

    // Socket(a~z)

    // String(a~z)
    private String mUserId;
    private String mChatBotId;
    private String mUserIdMe;
    private String mUserProfileImage;

    // Thread(a~z)

    // VideoKit(a~z)

    // Extra Class(a~z)
    private UserDetails mUserDetails;


    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context, String userId, String userIdMe, boolean isFavorite) {
        Intent intent =  new Intent(context, UserDetailsActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_USER_ID_ME, userIdMe);
        intent.putExtra(EXTRA_IS_FAVORITE, isFavorite);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)
        mButton_insert_favorite = findViewById(R.id.button_insert_favorites_id);
        mButton_delete_favorite = findViewById(R.id.button_delete_favorites_id);
        mButton_chat = findViewById(R.id.button_chat_id);

        // CircleImageView(a~z)
        mCircleImageView = findViewById(R.id.imageview_userdetails_user_image_id);

        // CrystalRangeSeekBar(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)

        // NestedScrollView(a~z)

        // RecyclerView(a~z)
        mRecyclerView = findViewById(R.id.recyclerview_gallery_image_list_userdetails_id);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int width_recyclerview = mRecyclerView.getWidth();
                ViewGroup.LayoutParams params= mRecyclerView.getLayoutParams();
                params.height=width_recyclerview * 1/5;
                mRecyclerView.setLayoutParams(params);
            }
        });

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TextView(a~z)

        mTextView_user_nickname = (TextView) findViewById(R.id.textview_userdetails_nickname_id);
        mTextView_user_age = (TextView) findViewById(R.id.textview_userdetails_age_id);
        mTextView_user_gender = (TextView) findViewById(R.id.textview_userdetails_gender_id);
        mTextView_user_login_time = (TextView) findViewById(R.id.textview_userdetails_login_time_id);
        mTextView_user_region = (TextView) findViewById(R.id.textview_userdetails_region_id);
        mTextView_user_nation = (TextView) findViewById(R.id.textview_userdetails_nation_id);
        mTextView_user_introduction = (TextView) findViewById(R.id.textview_userdetails_introduction_id);

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        mUserImages = new ArrayList<>();

        // DialogManager (a~z)
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        // ImageLoader (a~z)
        mImageLoader = new ImageLoader(this);

        // LayoutManager (a~z)
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Networking (a~z)
        mFetchUserDetailsUseCase = new FetchUserDetailsUseCase();
        mInsertFavoriteUseCase = new InsertFavoriteUseCase();
        mDeleteFavoriteUseCase = new DeleteFavoriteUseCase();

        // RecyclerViewAdapter (a~z)
        mGalleryImageRecyclerViewAdapter = new GalleryImageRecyclerViewAdapter(this, mUserImages, this, mImageLoader, mRecyclerView);

        // SharedPreference(a~z)

        // String (a~z)

        // Thread(a~z)

        // Extra Class(a~z)

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        mUserId = getIntent().getExtras().getString(EXTRA_USER_ID);
        mUserIdMe = getIntent().getExtras().getString(EXTRA_USER_ID_ME);
        mIsFavorite = getIntent().getExtras().getBoolean(EXTRA_IS_FAVORITE);
        
        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        mFetchUserDetailsUseCase.fetchUserDetailsAndNotify(mUserId);
        
        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------

        // BottomNavView

        // Button

        setUpButtons();

        if (mUserId.equals("2814")) {
            mButton_insert_favorite.setVisibility(View.GONE);
            mButton_delete_favorite.setVisibility(View.GONE);
        }

        mButton_insert_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInsertFavoriteButtonClicked();
            }
        });

        mButton_delete_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteFavoriteButtonClicked();
            }
        });

        mButton_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChatButtonClicked();
            }
        });

        // CircleImageView()
        //*** Setting up in FetchUserDetailUserCase.Listener ***

        // CrystalRangeSeekBar

        // LinearLayout(a~z)

        // RecyclerView(a~z)
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mGalleryImageRecyclerViewAdapter);

        // TabHost

        // TextView(a~z)
        // *** Setting up in FetchUserDetailUserCase.Listener ***

        // Toolbar

        setUpToolbar();

        //------------------------------------------------------------------------------------------
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mFetchUserDetailsUseCase.registerListener(this);
        mInsertFavoriteUseCase.registerListener(this);
        mDeleteFavoriteUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchUserDetailsUseCase.unregisterListener(this);
        mInsertFavoriteUseCase.unregisterListener(this);
        mDeleteFavoriteUseCase.unregisterListener(this);
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // FetchUserDetailsUseCase.Listener
    @Override
    public void onFetchUserDetailsUseCaseSucceeded(UserDetails userDetails) {

        mUserDetails = userDetails;

        mTextView_user_nickname.setText(userDetails.getUserNickName());
        mTextView_user_age.setText(String.valueOf(Constants.CURRENT_YEAR_PLUS_ONE-Integer.valueOf(userDetails.getUserBirthYear())) + Constants.AGE);
        mTextView_user_gender.setText(userDetails.getUserGender());
        mTextView_user_login_time.setText(userDetails.getUserLoginTime());
        mTextView_user_region.setText(userDetails.getUserRegion());
        mTextView_user_nation.setText(userDetails.getUserNation());
        mTextView_user_introduction.setText(userDetails.getUserIntroduction());
        mTextView_toolbar.setText(userDetails.getUserNickName());

        mUserProfileImage = userDetails.getUserProfileImage();
        if (mUserProfileImage.equals("")) {
            mCircleImageView.setImageResource(R.drawable.ic_person_black_24dp);
        } else {
            mImageLoader.loadImage(userDetails.getUserProfileImage(), mCircleImageView);
            mCircleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCircleImageViewClicked();
                }
            });
        }

        mUserImages.clear();
        mUserImages.addAll(userDetails.getUserImages());
        mGalleryImageRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchUserDetailsUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // InsertFavoriteUserCase.Listener

    @Override
    public void onInsertFavoriteUseCaseSucceeded(String string) {
        if (string.equals("Success")) {
            mIsFavorite = true;
            setUpButtons();
        } else {
            mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

        }
    }

    @Override
    public void onInsertFavoriteUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // DeleteFavoriteUseCase.Listener
    @Override
    public void onDeleteFavoriteUseCaseSucceeded(String string) {
        if (string.equals("Success")) {
            mIsFavorite = false;
            setUpButtons();
        } else {
            mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

        }
    }

    @Override
    public void onDeleteFavoriteUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // GalleryImageRecyclerViewAdapter.Listener
    @Override
    public void onItemClick(int position) {
        ArrayList<String> imageListForGallery = new ArrayList<>();
        if (mUserProfileImage.equals("")) {
            GalleryActivity.start(this, mUserImages, position);
        } else {
            imageListForGallery.add(mUserProfileImage);
            imageListForGallery.addAll(mUserImages);
            GalleryActivity.start(this, imageListForGallery, position+1);
        }
    }
    
    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    private void onInsertFavoriteButtonClicked() {
        mInsertFavoriteUseCase.insertFavoriteUseCaseAndNotify(mUserIdMe, mUserId);
    }

    private void onDeleteFavoriteButtonClicked() {
        mDeleteFavoriteUseCase.deleteFavoriteAndNotify(mUserIdMe, mUserId);
    }

    private void onChatButtonClicked() {
        if (mUserDetails.getUserDeviceId().equals("chatbot")) {
            ChatBotActivity.start(this, mUserId, mUserDetails.getUserNickName(), null, mUserDetails.getUserProfileImage());
        } else {
            ChatActivity.start(this, mUserId, mUserDetails.getUserNickName(), null, mUserDetails.getUserProfileImage());
        }

    }

    private void onCircleImageViewClicked() {
        ArrayList<String> imageListForGallery = new ArrayList<>();
        imageListForGallery.add(mUserProfileImage);
        imageListForGallery.addAll(mUserImages);
        GalleryActivity.start(this, imageListForGallery, 0);
    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        mTextView_toolbar = findViewById(R.id.txt_toolbar_title);
        mTextView_toolbar.setText("");

    }

    private void setUpButtons() {
        if(mIsFavorite) {
            mButton_insert_favorite.setVisibility(View.GONE);
            mButton_delete_favorite.setVisibility(View.VISIBLE);
        } else {
            mButton_insert_favorite.setVisibility(View.VISIBLE);
            mButton_delete_favorite.setVisibility(View.GONE);
        }
    }

    // ---------------------------------------------------------------------------------------------
}
