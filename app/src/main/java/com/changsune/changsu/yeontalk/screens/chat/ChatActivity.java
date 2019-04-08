package com.changsune.changsu.yeontalk.screens.chat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.changsune.changsu.yeontalk.ChatService;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.SenderThread;
import com.changsune.changsu.yeontalk.chat.Chat;
import com.changsune.changsu.yeontalk.chat.DeleteChatUnShownUseCase;
import com.changsune.changsu.yeontalk.chat.FetchChatListUseCase;
import com.changsune.changsu.yeontalk.chat.InsertChatUseCase;
import com.changsune.changsu.yeontalk.chatroom.DeleteUsersChatRoomUseCase;
import com.changsune.changsu.yeontalk.file.UploadFileWithChatUseCase;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;
import com.changsune.changsu.yeontalk.screens.billing.BillingActivity;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionExitChatRoomDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionGoToBillingDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionRefreshChatDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionSendChatDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.common.videokit.VideoKit;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.videocall.CallActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Type;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatActivity extends AppCompatActivity implements
        FetchChatListUseCase.Listener, InsertChatUseCase.Listener, UploadFileWithChatUseCase.Listener, VideoKit.Listener,
        DeleteChatUnShownUseCase.Listener, RequestSelectionSendChatDialogFragment.Listener, UpdateMeDataUseCase.Listener, RequestSelectionGoToBillingDialogFragment.Listener
        , DeleteUsersChatRoomUseCase.Listener, RequestSelectionExitChatRoomDialogFragment.Listener, ChatRecyclerViewAdapter.Listener,
        RequestSelectionRefreshChatDialogFragment.Listener {

    // Static Variable (a~z)------------------------------------------------------------------------
    private static final String TAG = "ChatActivity";

    private static final int CONNECTION_REQUEST = 1;
    private static final int REMOVE_FAVORITE_INDEX = 0;
    private static boolean commandLineRun = false;

    public static final String EXTRA_CHAT_ROOM_ID = "EXTRA_ROOM_ID";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_USER_IMAGE = "EXTRA_USER_IMAGE";
    public static final String EXTRA_USER_NICKNAME = "EXTRA_USER_NICKNAME";

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)
    Button mButton_send;

    // DrawerLayout(a~z)
    DrawerLayout mDrawerLayout;

    // EditText(a~z)
    EditText mEditText_message;

    // ImageButton(a~z)
    ImageButton mImageButton_clear;
    ImageButton mImageButton_plus;

    // ImageView(a~z)
    ImageView mImageView_more_ver;


    // NavigationView(a~z)
    NavigationView mNavigationView;

    // RecyclerView(a~z)
    RecyclerView mRecyclerView;

    // SwipeRefreshLayout(a~z)

    // TextView(a~z)

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog
    AlertDialog mAlertDialog;

    // ArrayList (a~z)
    ArrayList<Chat> mChatArrayList;
    ArrayList<Chat> mChatArrayList_uploading;
    ArrayList<Chat> mChatArrayList_uploaded;

    // Boolean(a~z)
    Boolean mBoolean_Recyclerview_canScrollBottom;

    // DialogManager (a~z)
    DialogsManager mDialogsManager;
    // ImageLoader (a~z)
    ImageLoader mImageLoader;

    // LayoutManager (a~z)
    LinearLayoutManager mLinearLayoutManager;

    // Networking (a~z)
    FetchChatListUseCase mFetchChatListUseCase;
    InsertChatUseCase mInsertChatUseCase;
    UploadFileWithChatUseCase mUploadFileWithChatUseCase;
    DeleteChatUnShownUseCase mDeleteChatUnShownUseCase;
    UpdateMeDataUseCase mUpdateMeDataUseCase;
    DeleteUsersChatRoomUseCase mDeleteUsersChatRoomUseCase;

    // Receiver
    BroadcastReceiver mBroadcastReceiver;
    BroadcastReceiver mBroadcastReceiver_from_networkReceiver;


    // RecyclerViewAdapter (a~z)
    ChatRecyclerViewAdapter mChatRecyclerViewAdapter;

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences;
    SharedPreferences mSharedPreferences_uploading;

    // String (a~z)
    String mChatRoomId;
    String mChatTimeLastLoaded;
    String mLoadLimit;
    String mMessage;
    String mString_date;
    String mUserId;
    String mUserNickName;
    String mUserImage;
    String mMeId;
    String mPoint;
    String mMeNickName;

    // Socket
    Socket mSocket;

    // Thread
    SenderThread mSenderThread;

    // VideoKit
    VideoKit mVideoKit;

    // ---------------------------------------------------------------------------------------------

    private SharedPreferences sharedPref;
    private String keyprefVideoCallEnabled;
    private String keyprefScreencapture;
    private String keyprefCamera2;
    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefCaptureQualitySlider;
    private String keyprefVideoBitrateType;
    private String keyprefVideoBitrateValue;
    private String keyprefVideoCodec;
    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefAudioCodec;
    private String keyprefHwCodecAcceleration;
    private String keyprefCaptureToTexture;
    private String keyprefFlexfec;
    private String keyprefNoAudioProcessingPipeline;
    private String keyprefAecDump;
    private String keyprefOpenSLES;
    private String keyprefDisableBuiltInAec;
    private String keyprefDisableBuiltInAgc;
    private String keyprefDisableBuiltInNs;
    private String keyprefEnableLevelControl;
    private String keyprefDisableWebRtcAGCAndHPF;
    private String keyprefDisplayHud;
    private String keyprefTracing;
    private String keyprefRoomServerUrl;
    private String keyprefRoom;
    private String keyprefRoomList;
    private ArrayList<String> roomList;
    private ArrayAdapter<String> adapter;
    private String keyprefEnableDataChannel;
    private String keyprefOrdered;
    private String keyprefMaxRetransmitTimeMs;
    private String keyprefMaxRetransmits;
    private String keyprefDataProtocol;
    private String keyprefNegotiated;
    private String keyprefDataId;

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context, String userId, String userNickName, String roomId, String userImage) {
        Intent intent =  new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_CHAT_ROOM_ID, roomId);
        intent.putExtra(EXTRA_USER_NICKNAME, userNickName);
        intent.putExtra(EXTRA_USER_IMAGE, userImage);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get video call setting keys -------------------------------------------------------------

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        keyprefVideoCallEnabled = getString(R.string.pref_videocall_key);
        keyprefScreencapture = getString(R.string.pref_screencapture_key);
        keyprefCamera2 = getString(R.string.pref_camera2_key);
        keyprefResolution = getString(R.string.pref_resolution_key);
        keyprefFps = getString(R.string.pref_fps_key);
        keyprefCaptureQualitySlider = getString(R.string.pref_capturequalityslider_key);
        keyprefVideoBitrateType = getString(R.string.pref_maxvideobitrate_key);
        keyprefVideoBitrateValue = getString(R.string.pref_maxvideobitratevalue_key);
        keyprefVideoCodec = getString(R.string.pref_videocodec_key);
        keyprefHwCodecAcceleration = getString(R.string.pref_hwcodec_key);
        keyprefCaptureToTexture = getString(R.string.pref_capturetotexture_key);
        keyprefFlexfec = getString(R.string.pref_flexfec_key);
        keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
        keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
        keyprefAudioCodec = getString(R.string.pref_audiocodec_key);
        keyprefNoAudioProcessingPipeline = getString(R.string.pref_noaudioprocessing_key);
        keyprefAecDump = getString(R.string.pref_aecdump_key);
        keyprefOpenSLES = getString(R.string.pref_opensles_key);
        keyprefDisableBuiltInAec = getString(R.string.pref_disable_built_in_aec_key);
        keyprefDisableBuiltInAgc = getString(R.string.pref_disable_built_in_agc_key);
        keyprefDisableBuiltInNs = getString(R.string.pref_disable_built_in_ns_key);
        keyprefEnableLevelControl = getString(R.string.pref_enable_level_control_key);
        keyprefDisableWebRtcAGCAndHPF = getString(R.string.pref_disable_webrtc_agc_and_hpf_key);
        keyprefDisplayHud = getString(R.string.pref_displayhud_key);
        keyprefTracing = getString(R.string.pref_tracing_key);
        keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
        keyprefRoom = getString(R.string.pref_room_key);
        keyprefRoomList = getString(R.string.pref_room_list_key);
        keyprefEnableDataChannel = getString(R.string.pref_enable_datachannel_key);
        keyprefOrdered = getString(R.string.pref_ordered_key);
        keyprefMaxRetransmitTimeMs = getString(R.string.pref_max_retransmit_time_ms_key);
        keyprefMaxRetransmits = getString(R.string.pref_max_retransmits_key);
        keyprefDataProtocol = getString(R.string.pref_data_protocol_key);
        keyprefNegotiated = getString(R.string.pref_negotiated_key);
        keyprefDataId = getString(R.string.pref_data_id_key);

        // -----------------------------------------------------------------------------------------

        setContentView(R.layout.activity_chat);

        // Receiving Data from Intent --------------------------------------------------------------

        mChatRoomId = getIntent().getExtras().getString(EXTRA_CHAT_ROOM_ID);
        mUserId = getIntent().getExtras().getString(EXTRA_USER_ID);
        mUserNickName = getIntent().getExtras().getString(EXTRA_USER_NICKNAME);
        mUserImage = getIntent().getExtras().getString(EXTRA_USER_IMAGE);

        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        // SharedPreference(a~z)
        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mMeId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, null);
        mPoint = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_POINT, null);
        mMeNickName = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_NICKNAME, null);

        //------------------------------------------------------------------------------------------

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)
        mButton_send = findViewById(R.id.button_send_chat_id);

        // DrawerLayout(a~z)
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // EditText(a~z)
        mEditText_message = findViewById(R.id.edittext_message_chat_id);

        // ImageButton(a~z)
        mImageButton_clear = findViewById(R.id.image_button_clear);
        mImageButton_plus = findViewById(R.id.image_button_plus);

        // NavigationView(a~z)
        mNavigationView = findViewById(R.id.nav_view);

        // RecyclerView(a~z)
        mRecyclerView = findViewById(R.id.recycler_view_chat_id);

        // SwipeRefreshLayout(a~z)

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        mChatArrayList = new ArrayList<>();
        mChatArrayList_uploaded = new ArrayList<>();
        mChatArrayList_uploading = new ArrayList<>();

        // DialogsManager (a~z)
        mDialogsManager = new DialogsManager(getSupportFragmentManager());
        // Image (a~z)
        mImageLoader = new ImageLoader(this);
        // LayoutManager (a~z)
        mLinearLayoutManager = new LinearLayoutManager(this);
        // Networking (a~z)
        mFetchChatListUseCase = new FetchChatListUseCase();
        mInsertChatUseCase = new InsertChatUseCase();
        mUploadFileWithChatUseCase = new UploadFileWithChatUseCase();
        mDeleteChatUnShownUseCase = new DeleteChatUnShownUseCase();
        mUpdateMeDataUseCase = new UpdateMeDataUseCase();
        mDeleteUsersChatRoomUseCase = new DeleteUsersChatRoomUseCase();

        // RecyclerViewAdapter (a~z)
        mChatRecyclerViewAdapter = new ChatRecyclerViewAdapter(this, mChatArrayList, mImageLoader, mMeId, this);

        // String (a~z)
        mChatTimeLastLoaded = "";

        // Thread (a~z)
//        mSenderThread = new SenderThread();
//        mSenderThread.start();

        // Extra

        // -----------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        Log.e(TAG, "onCreate: " + mChatRoomId);
        mFetchChatListUseCase.fetchChatListUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mChatTimeLastLoaded, Constants.LOAD_LIMIT);

        // -----------------------------------------------------------------------------------------

        // Setting Up Toolbar ----------------------------------------------------------------------
        setUpToolbar();
        //------------------------------------------------------------------------------------------

        // Setting RecyclerView --------------------------------------------------------------------

        mBoolean_Recyclerview_canScrollBottom = false;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mChatRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mRecyclerView.canScrollVertically(1)) {
                    Log.e(TAG, "onScrollStateChanged: ");
                    mBoolean_Recyclerview_canScrollBottom = true;
                } else {
                    Log.e(TAG, "onScrollStateChanged: 1");
                    mBoolean_Recyclerview_canScrollBottom = false;
                }
            }
        });
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mBoolean_Recyclerview_canScrollBottom == false) {
                    if (bottom < oldBottom) {
                        final int lastAdapterItem = mChatRecyclerViewAdapter.getItemCount() - 1;
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                int recyclerViewPositionOffset = -1000000;
                                View bottomView = mLinearLayoutManager.findViewByPosition(lastAdapterItem);
                                if (bottomView != null) {
                                    recyclerViewPositionOffset = 0 - bottomView.getHeight();
                                }
                                mLinearLayoutManager.scrollToPositionWithOffset(lastAdapterItem, recyclerViewPositionOffset);
                            }
                        });
                    }
                }
            }
        });

        // -----------------------------------------------------------------------------------------

        // Setting NavDrawerView

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.drawer_menu_exit_chat_room_id) {
                    onDrawerItemClicked();
                }
                return false;
            }
        });


        // Setting Receiver ------------------------------------------------------------------------
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String userId = intent.getStringExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_USER_ID_KEY);
                String roomId = intent.getStringExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_ROOM_ID_KEY);
                String date = intent.getStringExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_DATE_KEY);
                String message = intent.getStringExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY);
                String type = intent.getStringExtra(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_TYPE_KEY);

                if (roomId.equals(mChatRoomId)) {

                    if (type.equals(Constants.DELETED_UNSHOWN)) {

                        for (int i = 0; i < mChatArrayList.size(); i++) {

                            mChatArrayList.get(i).setSeen("0");
                            mChatRecyclerViewAdapter.notifyDataSetChanged();

                        }

                    } else if (type.equals(Constants.CHAT_TYPE)) {
                        addChatToList(message, userId, date, type, "0", Constants.UPLOADED);
                        mDeleteChatUnShownUseCase.deleteChatUnShownAndNotify(roomId, mMeId);
                    } else if (type.equals(Constants.IMAGE_TYPE)){
                        String[] split = message.split(">", 2);
                        String file_path = split[0];
                        String size = split[1];
                        addImageToList(file_path, userId, date, type, "0", Constants.UPLOADED, size);
                        mDeleteChatUnShownUseCase.deleteChatUnShownAndNotify(roomId, mMeId);
                    } else if (type.equals(Constants.VIDEO_TYPE)) {
                        String[] split = message.split(">", 2);
                        String file_path = split[0];
                        String size = split[1];
                        addVideoToList(file_path, userId, date, type, "0", Constants.UPLOADED, size);
                        mDeleteChatUnShownUseCase.deleteChatUnShownAndNotify(roomId, mMeId);
                    }

                }

            }
        };

        mBroadcastReceiver_from_networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mFetchChatListUseCase.fetchChatListUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mChatTimeLastLoaded, Constants.LOAD_LIMIT);

            }
        };

        // Setting Dialog --------------------------------------------------------------------------

        mAlertDialog = setUpDialog();

        // -----------------------------------------------------------------------------------------
        // Setting OnClickListener in Button -------------------------------------------------------

        mButton_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendButtonClicked();
            }
        });
        mImageButton_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();

            }
        });

        mImageButton_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

        // -----------------------------------------------------------------------------------------

    }


    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        mFetchChatListUseCase.registerListener(this);
        mInsertChatUseCase.registerListener(this);
        mUploadFileWithChatUseCase.registerListener(this);
        mDeleteChatUnShownUseCase.registerListener(this);
        mUpdateMeDataUseCase.registerListener(this);
        mDeleteUsersChatRoomUseCase.registerListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_KEY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver_from_networkReceiver, new IntentFilter(Constants.CHAT_ACTIVITY_BROADCAST_RECEIVER_FOR_CHANGING_VIEW_KEY));

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mChatRoomId != null) {
            setArrayPref(mChatRoomId);
        }

        mFetchChatListUseCase.unregisterListener(this);
        mInsertChatUseCase.unregisterListener(this);
        mUploadFileWithChatUseCase.unregisterListener(this);
        mDeleteChatUnShownUseCase.unregisterListener(this);
        mUpdateMeDataUseCase.unregisterListener(this);
        mDeleteUsersChatRoomUseCase.unregisterListener(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver_from_networkReceiver);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Receiving Data from SharedPreference ----------------------------------------------------

        // SharedPreference(a~z)
        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mMeId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, null);
        mPoint = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_POINT, null);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // UploadFileWithChatUseCase.Listener
    @Override
    public void onFetchChatListUseCaseSucceeded(List<Chat> list) {
        if (list.get(0).getRoomId().equals("No")) {
            mChatRoomId = null;
        } else {
            mChatRoomId = list.get(0).getRoomId();
            mUserImage = list.get(0).getImage();

            Log.e(TAG, "onFetchChatListUseCaseSucceeded: " + mChatRoomId);
            Log.e(TAG, "onFetchChatListUseCaseSucceeded: " + list.size() );

            getArrayPref(mChatRoomId);

            mChatArrayList_uploaded.clear();
            mChatArrayList_uploaded.addAll(list);

            mChatArrayList.clear();
            mChatArrayList.addAll(mChatArrayList_uploaded);
            mChatArrayList.addAll(mChatArrayList_uploading);

            mChatRecyclerViewAdapter.notifyDataSetChanged();
            ChatService.sendMessage(mUserId + ">" + mMeId + ">" + mChatRoomId + "" + ">" + "" + ">" + Constants.DELETED_UNSHOWN + ">" + "");

            if (mChatArrayList.size() != 0) {
                mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount()-1);
            }

        }


    }

    @Override
    public void onFetchChatListUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // InsertChatUseCase.Listener

    @Override
    public void onInsertChatUseCaseSucceeded(String room_id, String from_user_id, String to_user_id, String message, String date, String type, String status, Chat chat) {

        mChatRoomId = room_id;

        mChatArrayList.removeAll(mChatArrayList_uploading);
        mChatArrayList_uploading.remove(chat);
        chat.setStatus(Constants.UPLOADED);
        mChatArrayList.add(chat);
        mChatArrayList.addAll(mChatArrayList_uploading);

        mChatRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);

        if (!type.equals(Constants.VIDEO_CALL_TYPE)) {
            ChatService.sendMessage(to_user_id + ">" + from_user_id + ">" + room_id + ">" + date + ">" + type + ">" + message);
        }
    }

    @Override
    public void onInsertChatUseCaseFailed(Chat chat) {
//        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
        for (Chat c : mChatArrayList) {
            if (c.equals(chat)) {
                c.setStatus(Constants.UPLOADINGFAIL);
            }
        }
        mChatRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        setArrayPref(mChatRoomId);
    }

    // UploadFileWithChatUseCase.Listener

    @Override
    public void onUploadFileWithChatUseCaseSucceeded(String room_id, String from_user_id, String to_user_id, String file_name, String date, String type, String status, final Chat chat, String size) {

        mChatArrayList.removeAll(mChatArrayList_uploading);
        mChatArrayList_uploading.remove(chat);
        chat.setStatus(Constants.UPLOADED);
        mChatArrayList.add(chat);
        mChatArrayList.addAll(mChatArrayList_uploading);

        mChatRecyclerViewAdapter.notifyDataSetChanged();
//        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        //여기에 딜레이 후 시작할 작업들을 입력

        String file_path;
        file_path = "http://52.79.51.149/yeontalk/uploads/" + file_name;
        ChatService.sendMessage(to_user_id + ">" + from_user_id + ">" + room_id + ">" + date + ">" + type + ">" + file_path + ">" + size);
    }

    @Override
    public void onUploadFileWithChatUseCaseFailed(Chat chat) {

        for (Chat c : mChatArrayList) {
            if (c.equals(chat)) {
                c.setStatus(Constants.UPLOADINGFAIL);
            }
        }

        mChatRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        setArrayPref(mChatRoomId);

        Log.e(TAG, "onUploadFileWithChatUseCaseFailed");

    }

    // VideoKit.Listener

    @Override
    public void onVideoKitSuccessed(final String mediaPath, final Chat chat) {

        Log.e(TAG, "onVideoKitSuccessed: ");
        File file = new File(mediaPath);
        final String size = getFolderSizeLabel(file);

        int rotation = 0;
        String width;
        String height;

        if (file.exists()) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(file.getAbsolutePath());
            String metaRotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            rotation = metaRotation == null ? 0 : Integer.parseInt(metaRotation);
            Log.i("Test", "Rotation = " + rotation);
        }

        if (rotation == 90 || rotation == 270) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(mediaPath);
            width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            Log.e(TAG, "compressVideoFile: height : "+ width );
            height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            Log.e(TAG, "compressVideoFile: width : " + height);

        } else {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(mediaPath);
            width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            Log.e(TAG, "compressVideoFile: width : " + width);
            height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            Log.e(TAG, "compressVideoFile: height : "+ height );
        }

        final Chat chat1 = chat;

        for (Chat c : mChatArrayList) {
            if (c.equals(chat1)) {
                c.setStatus(Constants.AFTER_COMPRSSING);
            }
        }

        mChatRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
                //여기에 딜레이 후 시작할 작업들을 입력

        uploadFileWithChat(mediaPath, Constants.VIDEO_TYPE, chat1, width+">"+height+">"+size);

    }

    @Override
    public void onVideoKitFailed() {

        Log.e(TAG, "onVideoKitFailed: ");

    }

    @Override
    public void onDeleteChatUnShownUseCaseSucceeded() {
        ChatService.sendMessage(mUserId + ">" + mMeId + ">" + mChatRoomId + ">" + "" + ">" + Constants.DELETED_UNSHOWN + ">" + "");

    }

    @Override
    public void onDeleteChatUnShownUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // RequestSelectionSendChatDialogFragment.Listener
    @Override
    public void onButtonSendInDialogClicked() {

        if (Integer.valueOf(mPoint) >= 50)  {
            int point = Integer.valueOf(mPoint) - 50;
            Log.e(TAG, "onButtonSendInDialogClicked: ");
            mUpdateMeDataUseCase.updateUserDataAndNotify(mMeId, Constants.POINT, String.valueOf(point));
        } else {
            mDialogsManager.showDialogWithId(RequestSelectionGoToBillingDialogFragment.newInstance(this), "");
        }

    }

    // UpdateMeDataUseCase.Listener
    @Override
    public void onUpdateMeDataUseCaseSucceeded(String data_key, String data_value) {
        mMessage = mEditText_message.getText().toString().trim();
        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());
        Chat chat = addChatToList(mMessage, mMeId, mString_date, Constants.CHAT_TYPE, "1", Constants.UPLOADING);
        mInsertChatUseCase.insertChatUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mMessage, mString_date, Constants.CHAT_TYPE, Constants.UPLOADED, chat);
        mEditText_message.setText("");

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_POINT, data_value);
        editor.commit();

    }

    @Override
    public void onUpdateMeDataUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // RequestSelectionGoToBillingDialogFragment.Listener

    @Override
    public void onButtonGoToBillingClicked() {
        BillingActivity.start(this);
    }

    // DeleteUsersChatRoomUseCase.Listener
    @Override
    public void onDeleteUsersChatRoomUseCaseSucceeded() {
        Log.e(TAG, "onDeleteUsersChatRoomUseCaseSucceeded: ");
        ChatService.sendMessage(mUserId + ">" + mMeId + ">" + mChatRoomId + ">" + ">" + Constants.EXIT_TYPE + ">" );

        finish();
    }

    @Override
    public void onDeleteUsersChatRoomUseCaseFailed() {
        Log.e(TAG, "onDeleteUsersChatRoomUseCaseFailed: " );
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // RequestSelectionExitChatRoomDialogFragment.Listener

    @Override
    public void onButtonExitClicked() {
        mDeleteUsersChatRoomUseCase.deleteUsersChatRoomAndNotify(mChatRoomId, mMeId);
    }

    // ChatRecyclerViewAdapter.Listener
    @Override
    public void onErrorButtonClicked(Chat chat) {
        mDialogsManager.showDialogWithId(RequestSelectionRefreshChatDialogFragment.newInstance(this, chat), "");
    }

    // RequestSelectionRefreshChatDialogFragment.Listener
    @Override
    public void onRefreshButtonClicked(Chat chat) {

        mChatArrayList_uploading.remove(chat);
        mChatArrayList.remove(chat);
        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

        if (chat.getType().equals(Constants.CHAT_TYPE)) {

            Chat chat_new = addChatToList(chat.mMessage, chat.mUserId, mString_date, chat.mType, "1", Constants.UPLOADING);
            mInsertChatUseCase.insertChatUseCaseAndNotify(mChatRoomId, mMeId, mUserId, chat.mMessage, mString_date, Constants.CHAT_TYPE, Constants.UPLOADED, chat_new);

        } else if (chat.getType().equals(Constants.IMAGE_TYPE)){

            Chat chat_new = addImageToList(chat.mMessage, mMeId, mString_date, Constants.IMAGE_TYPE, "1", Constants.UPLOADING, chat.mSize);
            uploadFileWithChat(chat.mMessage, Constants.IMAGE_TYPE, chat_new, chat.mSize);


        } else if (chat.getType().equals(Constants.VIDEO_TYPE)) {

        }



    }

    @Override
    public void onDeleteButtonClicked(Chat chat) {
        mChatArrayList_uploading.remove(chat);
        mChatArrayList.remove(chat);
        mChatRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);

    }

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // Button
    private void onSendButtonClicked() {

        mMessage = mEditText_message.getText().toString().trim();
        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());
        if (mMessage.equals("")) {
            StyleableToast.makeText(this, "메시지가 비었습니다", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else {
            if (mChatRoomId == null) {
                mDialogsManager.showDialogWithId(RequestSelectionSendChatDialogFragment.newInstance(this), "");
            } else {

                Chat chat = addChatToList(mMessage, mMeId, mString_date, Constants.CHAT_TYPE, "1", Constants.UPLOADING);

                mInsertChatUseCase.insertChatUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mMessage, mString_date, Constants.CHAT_TYPE, Constants.UPLOADED, chat);
                mEditText_message.setText("");
            }

        }
    }

    private void onDrawerItemClicked() {
        mDialogsManager.showDialogWithId(RequestSelectionExitChatRoomDialogFragment.newInstance(this), "");

    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------
    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText(mUserNickName);
        mImageView_more_ver = findViewById(R.id.image_view_more_ver_id);
        mImageView_more_ver.setVisibility(View.VISIBLE);
        mImageView_more_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });

    }

    private Chat addChatToList(String message, String userId, String string_date, String chatType, String seenNum, String status) {

        Chat chat = new Chat(string_date, mUserImage, message, mUserNickName, seenNum, chatType, userId, status);
        if (status.equals(Constants.UPLOADING)) {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList_uploading.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        } else {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        }
        return chat;
    }

    private Chat addImageToList(String message, String userId, String string_date, String chatType, String seenNum, String status, String size) {

        Chat chat = new Chat(string_date, mUserImage, message, mUserNickName, seenNum, chatType, userId, status, size);

        if (status.equals(Constants.UPLOADING)) {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList_uploading.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        } else {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        }

        return chat;
    }

    private Chat addVideoToList(String message, String userId, String string_date, String chatType, String seenNum, String status, String size) {

        Chat chat = new Chat(string_date, mUserImage, message, mUserNickName, seenNum, chatType, userId, status, size);

        if (status.equals(Constants.UPLOADING)) {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList_uploading.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        } else {
            mChatArrayList.removeAll(mChatArrayList_uploading);
            mChatArrayList.add(chat);
            mChatArrayList.addAll(mChatArrayList_uploading);
            mChatRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatRecyclerViewAdapter.getItemCount() - 1);
        }

        return chat;
    }

    private AlertDialog setUpDialog() {

        String[] calls = new String[]{"영상 통화", "사진", "동영상", "카메라"};
        AlertDialog.Builder callSelect = new AlertDialog.Builder(this);
        callSelect.setItems(calls, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    onVideoCallButtonClicked();

                } else if (which == 1) {
                    onPickImageButtonClicked();

                } else if (which == 2) {
                    onPickVideoButtonClicked();

                } else {

                }
            }
        });
        return callSelect.create();
    }

    private void onVideoCallButtonClicked() {

        String webRtcRoomId = Integer.toString(new Random().nextInt(100000000));
        mMessage = "영상통화 해요";
        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

        Chat chat = addChatToList(mMessage, mMeId, mString_date, Constants.VIDEO_CALL_TYPE, "1", Constants.UPLOADING);
        mInsertChatUseCase.insertChatUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mMessage, mString_date, Constants.VIDEO_CALL_TYPE, Constants.UPLOADED, chat);
        connectToRoom(webRtcRoomId, false, false, false, 0);
    }

    private void onPickImageButtonClicked() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, 0);

    }

    private void onPickVideoButtonClicked() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");

        startActivityForResult(galleryIntent, 1);
    }

    private void uploadFileWithChat(String mediaPath, String file_type, Chat chat, String file_size) {

        if (file_type.equals(Constants.IMAGE_TYPE)) {
            File file = new File(mediaPath);
            String file_name = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
            mUploadFileWithChatUseCase.uploadFileWithChatUseCaseAndNotify(fileToUpload, file_name, mChatRoomId, mMeId, mUserId, file_type, mString_date, Constants.UPLOADED, file_size, chat);
        } else {
            File file = new File(mediaPath);
            String file_name = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
            mUploadFileWithChatUseCase.uploadFileWithChatUseCaseAndNotify(fileToUpload, file_name, mChatRoomId, mMeId, mUserId, file_type, mString_date, Constants.UPLOADED, file_size, chat);
        }


    }

    private void compressVideoFile(final String mediaPath) {

        File file = new File(mediaPath);
        String size = getFolderSizeLabel(file);

        int rotation = 0;
        String width;
        String height;

        if (file.exists()) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(file.getAbsolutePath());
            String metaRotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            rotation = metaRotation == null ? 0 : Integer.parseInt(metaRotation);
            Log.i("Test", "Rotation = " + rotation);
        }

        if (rotation == 90 || rotation == 270) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(mediaPath);
            width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            Log.e(TAG, "compressVideoFile: height : "+ width );
            height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            Log.e(TAG, "compressVideoFile: width : " + height);

        } else {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(mediaPath);
            width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            Log.e(TAG, "compressVideoFile: width : " + width);
            height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            Log.e(TAG, "compressVideoFile: height : "+ height );
        }

        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

        final Chat chat = addVideoToList(mediaPath, mMeId, mString_date, Constants.VIDEO_TYPE, "1", Constants.BEFORE_COMPRSSING, width+">"+height+">"+size);

        final String mediaPath_compressed = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + file.getName();

        Log.e(TAG, "compressVideoFile: " + mediaPath);

        mVideoKit = new VideoKit(this, this, mediaPath_compressed, chat);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // VideoKit

                for (Chat c : mChatArrayList) {
                    if (c.equals(chat)) {
                        c.setStatus(Constants.COMPRESSING);
                    }
                }
                mChatRecyclerViewAdapter.notifyDataSetChanged();
                mVideoKit.execute(
                        "ffmpeg",
                        "-y",
                        "-i",
                        mediaPath,
                        "-c:v",
                        "libx264",
                        "-crf",
                        "23",
                        "-preset",
                        "ultrafast",
                        "-c:a",
                        "aac",
                        "-b:a",
                        "128k",
                        "-movflags",
                        "+faststart",
                        "-vf",
                        "scale=-2:720",
                        mediaPath_compressed);

                //여기에 딜레이 후 시작할 작업들을 입력
            }
        }, 1000);// 0.5초 정도 딜레이를 준 후 시작

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                String mediaPath = cursor.getString(columnIndex);
                cursor.close();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mediaPath, options);
                int width = options.outWidth;
                int height = options.outHeight;

                Log.e(TAG, "onActivityResult: " + width + "/" + height);

                mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

                Chat chat = addImageToList(mediaPath, mMeId, mString_date, Constants.IMAGE_TYPE, "1", Constants.UPLOADING, width+">"+height+">");
                uploadFileWithChat(mediaPath, Constants.IMAGE_TYPE, chat, width+">"+height+">");

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                String mediaPath = cursor.getString(columnIndex);
                cursor.close();

                compressVideoFile(mediaPath);

            } else if (requestCode == CONNECTION_REQUEST && commandLineRun) {
                Log.d(TAG, "Return: " + resultCode);
                setResult(resultCode);
                commandLineRun = false;
                finish();
            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    public static String getFolderSizeLabel(File file) {

        long size = getFolderSize(file) / 1024; // Get size and convert bytes into Kb.
        if (size >= 1024) {
            return (size / 1024) + " Mb";
        } else {
            return size + " Kb";
        }
    }

    public static long getFolderSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                size += getFolderSize(child);
            }
        } else {
            size = file.length();
        }
        return size;
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.RIGHT);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    private void setArrayPref(String chatRoomId) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_CHAT_UPLOADING + chatRoomId, MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Chat>>(){}.getType();
        String json = gson.toJson(mChatArrayList_uploading, listType);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHAREDPREF_KEY_CHAT_UPLOADING_DATA, json);
        editor.commit();


    }

    private void getArrayPref(String chatRoomId) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_CHAT_UPLOADING + chatRoomId, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.SHAREDPREF_KEY_CHAT_UPLOADING_DATA, "");
        Type type = new TypeToken<ArrayList<Chat>>(){}.getType();

        if (json != null && json != "") {
            mChatArrayList_uploading = gson.fromJson(json, type);
            Log.e(TAG, "getArrayPref: 2" + mChatArrayList_uploading.size());
        } else {

        }

    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private String sharedPrefGetString(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getIntent().getStringExtra(intentName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getString(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private boolean sharedPrefGetBoolean(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private int sharedPrefGetInteger(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultString = getString(defaultId);
        int defaultValue = Integer.parseInt(defaultString);
        if (useFromIntent) {
            return getIntent().getIntExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            String value = sharedPref.getString(attributeName, defaultString);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
                return defaultValue;
            }
        }
    }

    private void connectToRoom(String roomId, boolean commandLineRun, boolean loopback,
                               boolean useValuesFromIntent, int runTimeMs) {
        this.commandLineRun = commandLineRun;

        // roomId is random for loopback.
        if (loopback) {
            roomId = Integer.toString((new Random()).nextInt(100000000));
        }

        String roomUrl = sharedPref.getString(
                keyprefRoomServerUrl, getString(R.string.pref_room_server_url_default));

        // Video call enabled flag.
        boolean videoCallEnabled = sharedPrefGetBoolean(R.string.pref_videocall_key,
                CallActivity.EXTRA_VIDEO_CALL, R.string.pref_videocall_default, useValuesFromIntent);

        // Use screencapture option.
        boolean useScreencapture = sharedPrefGetBoolean(R.string.pref_screencapture_key,
                CallActivity.EXTRA_SCREENCAPTURE, R.string.pref_screencapture_default, useValuesFromIntent);

        // Use Camera2 option.
        boolean useCamera2 = sharedPrefGetBoolean(R.string.pref_camera2_key, CallActivity.EXTRA_CAMERA2,
                R.string.pref_camera2_default, useValuesFromIntent);

        // Get default codecs.
        String videoCodec = sharedPrefGetString(R.string.pref_videocodec_key,
                CallActivity.EXTRA_VIDEOCODEC, R.string.pref_videocodec_default, useValuesFromIntent);
        String audioCodec = sharedPrefGetString(R.string.pref_audiocodec_key,
                CallActivity.EXTRA_AUDIOCODEC, R.string.pref_audiocodec_default, useValuesFromIntent);

        // Check HW codec flag.
        boolean hwCodec = sharedPrefGetBoolean(R.string.pref_hwcodec_key,
                CallActivity.EXTRA_HWCODEC_ENABLED, R.string.pref_hwcodec_default, useValuesFromIntent);

        // Check Capture to texture.
        boolean captureToTexture = sharedPrefGetBoolean(R.string.pref_capturetotexture_key,
                CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, R.string.pref_capturetotexture_default,
                useValuesFromIntent);

        // Check FlexFEC.
        boolean flexfecEnabled = sharedPrefGetBoolean(R.string.pref_flexfec_key,
                CallActivity.EXTRA_FLEXFEC_ENABLED, R.string.pref_flexfec_default, useValuesFromIntent);

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = sharedPrefGetBoolean(R.string.pref_noaudioprocessing_key,
                CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, R.string.pref_noaudioprocessing_default,
                useValuesFromIntent);

        // Check Disable Audio Processing flag.
        boolean aecDump = sharedPrefGetBoolean(R.string.pref_aecdump_key,
                CallActivity.EXTRA_AECDUMP_ENABLED, R.string.pref_aecdump_default, useValuesFromIntent);

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = sharedPrefGetBoolean(R.string.pref_opensles_key,
                CallActivity.EXTRA_OPENSLES_ENABLED, R.string.pref_opensles_default, useValuesFromIntent);

        // Check Disable built-in AEC flag.
        boolean disableBuiltInAEC = sharedPrefGetBoolean(R.string.pref_disable_built_in_aec_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, R.string.pref_disable_built_in_aec_default,
                useValuesFromIntent);

        // Check Disable built-in AGC flag.
        boolean disableBuiltInAGC = sharedPrefGetBoolean(R.string.pref_disable_built_in_agc_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, R.string.pref_disable_built_in_agc_default,
                useValuesFromIntent);

        // Check Disable built-in NS flag.
        boolean disableBuiltInNS = sharedPrefGetBoolean(R.string.pref_disable_built_in_ns_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_NS, R.string.pref_disable_built_in_ns_default,
                useValuesFromIntent);

        // Check Enable level control.
        boolean enableLevelControl = sharedPrefGetBoolean(R.string.pref_enable_level_control_key,
                CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, R.string.pref_enable_level_control_key,
                useValuesFromIntent);

        // Check Disable gain control
        boolean disableWebRtcAGCAndHPF = sharedPrefGetBoolean(
                R.string.pref_disable_webrtc_agc_and_hpf_key, CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
                R.string.pref_disable_webrtc_agc_and_hpf_key, useValuesFromIntent);

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;
        if (useValuesFromIntent) {
            videoWidth = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
            videoHeight = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
        }
        if (videoWidth == 0 && videoHeight == 0) {
            String resolution =
                    sharedPref.getString(keyprefResolution, getString(R.string.pref_resolution_default));
            String[] dimensions = resolution.split("[ x]+");
            if (dimensions.length == 2) {
                try {
                    videoWidth = Integer.parseInt(dimensions[0]);
                    videoHeight = Integer.parseInt(dimensions[1]);
                } catch (NumberFormatException e) {
                    videoWidth = 0;
                    videoHeight = 0;
                    Log.e(TAG, "Wrong video resolution setting: " + resolution);
                }
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;
        if (useValuesFromIntent) {
            cameraFps = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
        }
        if (cameraFps == 0) {
            String fps = sharedPref.getString(keyprefFps, getString(R.string.pref_fps_default));
            String[] fpsValues = fps.split("[ x]+");
            if (fpsValues.length == 2) {
                try {
                    cameraFps = Integer.parseInt(fpsValues[0]);
                } catch (NumberFormatException e) {
                    cameraFps = 0;
                    Log.e(TAG, "Wrong camera fps setting: " + fps);
                }
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = sharedPrefGetBoolean(R.string.pref_capturequalityslider_key,
                CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                R.string.pref_capturequalityslider_default, useValuesFromIntent);

        // Get video and audio start bitrate.
        int videoStartBitrate = 0;
        if (useValuesFromIntent) {
            videoStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
        }
        if (videoStartBitrate == 0) {
            String bitrateTypeDefault = getString(R.string.pref_maxvideobitrate_default);
            String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefVideoBitrateValue, getString(R.string.pref_maxvideobitratevalue_default));
                videoStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        int audioStartBitrate = 0;
        if (useValuesFromIntent) {
            audioStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
        }
        if (audioStartBitrate == 0) {
            String bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
            String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefAudioBitrateValue, getString(R.string.pref_startaudiobitratevalue_default));
                audioStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        // Check statistics display option.
        boolean displayHud = sharedPrefGetBoolean(R.string.pref_displayhud_key,
                CallActivity.EXTRA_DISPLAY_HUD, R.string.pref_displayhud_default, useValuesFromIntent);

        boolean tracing = sharedPrefGetBoolean(R.string.pref_tracing_key, CallActivity.EXTRA_TRACING,
                R.string.pref_tracing_default, useValuesFromIntent);

        // Get datachannel options
        boolean dataChannelEnabled = sharedPrefGetBoolean(R.string.pref_enable_datachannel_key,
                CallActivity.EXTRA_DATA_CHANNEL_ENABLED, R.string.pref_enable_datachannel_default,
                useValuesFromIntent);
        boolean ordered = sharedPrefGetBoolean(R.string.pref_ordered_key, CallActivity.EXTRA_ORDERED,
                R.string.pref_ordered_default, useValuesFromIntent);
        boolean negotiated = sharedPrefGetBoolean(R.string.pref_negotiated_key,
                CallActivity.EXTRA_NEGOTIATED, R.string.pref_negotiated_default, useValuesFromIntent);
        int maxRetrMs = sharedPrefGetInteger(R.string.pref_max_retransmit_time_ms_key,
                CallActivity.EXTRA_MAX_RETRANSMITS_MS, R.string.pref_max_retransmit_time_ms_default,
                useValuesFromIntent);
        int maxRetr =
                sharedPrefGetInteger(R.string.pref_max_retransmits_key, CallActivity.EXTRA_MAX_RETRANSMITS,
                        R.string.pref_max_retransmits_default, useValuesFromIntent);
        int id = sharedPrefGetInteger(R.string.pref_data_id_key, CallActivity.EXTRA_ID,
                R.string.pref_data_id_default, useValuesFromIntent);
        String protocol = sharedPrefGetString(R.string.pref_data_protocol_key,
                CallActivity.EXTRA_PROTOCOL, R.string.pref_data_protocol_default, useValuesFromIntent);

        // Start AppRTCMobile activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_FLEXFEC_ENABLED, flexfecEnabled);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
            intent.putExtra(CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, enableLevelControl);
            intent.putExtra(CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
            intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);

            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);

            if (dataChannelEnabled) {
                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
                intent.putExtra(CallActivity.EXTRA_ID, id);
            }

            if (useValuesFromIntent) {
                if (getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
                    String videoFileAsCamera =
                            getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
                    intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
                    String saveRemoteVideoToFile =
                            getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
                    int videoOutWidth =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
                    int videoOutHeight =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
                }
            }

            intent.putExtra(CallActivity.EXTRA_USER_ID, mUserId);
            intent.putExtra(CallActivity.EXTRA_USER_NICKNAME, mMeNickName);
            intent.putExtra(CallActivity.EXTRA_ROOM_ID, roomId);
            intent.putExtra(CallActivity.EXTRA_ME_ID, mMeId);

            startActivityForResult(intent, CONNECTION_REQUEST);
        }
    }

    private boolean validateUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true;
        }

        new AlertDialog.Builder(this)
                .setTitle(getText(R.string.invalid_url_title))
                .setMessage(getString(R.string.invalid_url_text, url))
                .setCancelable(false)
                .setNeutralButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
        return false;
    }

    // ---------------------------------------------------------------------------------------------




}
