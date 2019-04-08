package com.changsune.changsu.yeontalk.screens.chatbot;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.SenderPyThread;
import com.changsune.changsu.yeontalk.SenderThread;
import com.changsune.changsu.yeontalk.chat.Chat;
import com.changsune.changsu.yeontalk.chat.DeleteChatUnShownUseCase;
import com.changsune.changsu.yeontalk.chat.FetchChatListUseCase;
import com.changsune.changsu.yeontalk.chat.InsertChatUseCase;
import com.changsune.changsu.yeontalk.chatroom.DeleteUsersChatRoomUseCase;
import com.changsune.changsu.yeontalk.file.UploadFileWithChatUseCase;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;
import com.changsune.changsu.yeontalk.screens.billing.BillingActivity;
import com.changsune.changsu.yeontalk.screens.chat.ChatRecyclerViewAdapter;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionExitChatRoomDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionGoToBillingDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionSendChatDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.common.videokit.VideoKit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatBotActivity extends AppCompatActivity implements
        FetchChatListUseCase.Listener, InsertChatUseCase.Listener, UploadFileWithChatUseCase.Listener, VideoKit.Listener,
        DeleteChatUnShownUseCase.Listener, RequestSelectionSendChatDialogFragment.Listener, UpdateMeDataUseCase.Listener, RequestSelectionGoToBillingDialogFragment.Listener
        , DeleteUsersChatRoomUseCase.Listener, RequestSelectionExitChatRoomDialogFragment.Listener {

    // Static Variable (a~z)------------------------------------------------------------------------
    private static final String TAG = "ChatBotActivity";

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

    // AsyncTask(a~z)
    NaverTranslateTask mNaverTranslateTask;

    // Boolean(a~z)

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

    // RecyclerViewAdapter (a~z)
    ChatBotRecyclerViewAdapter mChatBotRecyclerViewAdapter;

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences;

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

    // Socket
    Socket mSocket;

    // Thread
    SenderPyThread mSenderPyThread;

    // VideoKit
    VideoKit mVideoKit;

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context, String userId, String userNickName, String roomId, String userImage) {
        Intent intent =  new Intent(context, ChatBotActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_CHAT_ROOM_ID, roomId);
        intent.putExtra(EXTRA_USER_NICKNAME, userNickName);
        intent.putExtra(EXTRA_USER_IMAGE, userImage);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

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

        // AsyncTask(a~z)

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
        mChatBotRecyclerViewAdapter = new ChatBotRecyclerViewAdapter(this, mChatArrayList, mImageLoader, mMeId);

        // String (a~z)
        mChatTimeLastLoaded = "";

        // Thread (a~z)
        mSenderPyThread = new SenderPyThread();
        mSenderPyThread.start();

        // Extra

        // -----------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        Log.e(TAG, "onCreate: " + mChatRoomId);
        mFetchChatListUseCase.fetchChatListUseCaseAndNotify(mChatRoomId, mMeId, mUserId, mChatTimeLastLoaded, Constants.LOAD_LIMIT, Constants.ON_CREATE);

        // -----------------------------------------------------------------------------------------

        // Setting Up Toolbar ----------------------------------------------------------------------
        setUpToolbar();
        //------------------------------------------------------------------------------------------

        // Setting RecyclerView --------------------------------------------------------------------

        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mChatBotRecyclerViewAdapter);

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

                String message = intent.getStringExtra(Constants.CHATBOT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY);

                String string_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

                String message_translated = null;

                Log.e(TAG, "onInsertChatUseCaseSucceeded: ");
                try {
                    message_translated = new NaverTranslateTask("en", "ko").execute(message).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mInsertChatUseCase.insertChatUseCaseAndNotify(mChatRoomId, mUserId, mMeId, message_translated, string_date, Constants.CHAT_TYPE, Constants.UPLOADED, null);


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
//        mImageButton_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertDialog.show();
//
//            }
//        });
//
//        mImageButton_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertDialog.dismiss();
//            }
//        });

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

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.CHATBOT_ACTIVITY_BROADCAST_RECEIVER_KEY));

    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchChatListUseCase.unregisterListener(this);
        mInsertChatUseCase.unregisterListener(this);
        mUploadFileWithChatUseCase.unregisterListener(this);
        mDeleteChatUnShownUseCase.unregisterListener(this);
        mUpdateMeDataUseCase.unregisterListener(this);
        mDeleteUsersChatRoomUseCase.unregisterListener(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);

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
    public void onFetchChatListUseCaseSucceeded(List<Chat> list, String life_cycle) {
        if (list.get(0).getRoomId().equals("No")) {
            mChatRoomId = null;
        } else {
            mChatRoomId = list.get(0).getRoomId();
            Log.e(TAG, "onFetchChatListUseCaseSucceeded: " + mChatRoomId);
            Log.e(TAG, "onFetchChatListUseCaseSucceeded: " + list.size() );
            mChatArrayList.addAll(list);
            mChatBotRecyclerViewAdapter.notifyDataSetChanged();

            if (mChatArrayList.size() != 0) {
                mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount()-1);
            }
        }
    }

    @Override
    public void onFetchChatListUseCaseFailed(String life_cycle) {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // InsertChatUseCase.Listener

    @Override
    public void onInsertChatUseCaseSucceeded(String room_id, String from_user_id, String to_user_id, String message, String date, String type, String status, Chat chat) {

        mChatRoomId = room_id;

        if (from_user_id == mMeId) {
            for (Chat c : mChatArrayList) {
                if (c.equals(chat)) {
                    c.setStatus(Constants.UPLOADED);
                }
            }
            mChatBotRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount() - 1);

            String message_translated = null;

            Log.e(TAG, "onInsertChatUseCaseSucceeded: ");
            try {
                message_translated = new NaverTranslateTask("ko", "en").execute(message).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mSenderPyThread.sendMessage(message_translated);

        } else {
            mDeleteChatUnShownUseCase.deleteChatUnShownAndNotify(mChatRoomId, mMeId);
            addChatToList(message, mUserId, date, Constants.CHAT_TYPE, "0", Constants.UPLOADED);
        }
    }

    @Override
    public void onInsertChatUseCaseFailed(Chat chat) {

        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    // UploadFileWithChatUseCase.Listener

    @Override
    public void onUploadFileWithChatUseCaseSucceeded(String room_id, String from_user_id, String to_user_id, String file_name, String date, String type, String status, final Chat chat, String size) {

        for (Chat c : mChatArrayList) {
            if (c.equals(chat)) {
                c.setStatus(Constants.UPLOADED);
                mChatBotRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        mChatBotRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount() - 1);
        //여기에 딜레이 후 시작할 작업들을 입력

        String file_path;
        file_path = "http://52.79.51.149/yeontalk/uploads/" + file_name;
        mSenderPyThread.sendMessage(to_user_id + ">" + from_user_id + ">" + room_id + ">" + date + ">" + type + ">" + file_path);
    }

    @Override
    public void onUploadFileWithChatUseCaseFailed(Chat chat) {

        Log.e(TAG, "onUploadFileWithChatUseCaseFailed");

    }

    // VideoKit.Listener

    @Override
    public void onVideoKitSuccessed(final String mediaPath, final Chat chat) {

        Log.e(TAG, "onVideoKitSuccessed: ");
        File file = new File(mediaPath);
        final String size = getFolderSizeLabel(file);

        final Chat chat1 = chat;

        for (int i = 0; i < mChatArrayList.size(); i++) {
            if (mChatArrayList.get(i).getUserId().equals(chat.mUserId)) {

            }
        }

        for (Chat c : mChatArrayList) {
            if (c.equals(chat1)) {
                c.setSize(size);
                c.setStatus(Constants.AFTER_COMPRSSING);
                mChatBotRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        mChatBotRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount() - 1);
                //여기에 딜레이 후 시작할 작업들을 입력

        for (Chat c : mChatArrayList) {
            if (c.equals(chat1)) {
                uploadFileWithChat(mediaPath, Constants.VIDEO_TYPE, chat1);
            }
        }
    }

    @Override
    public void onVideoKitFailed() {

        Log.e(TAG, "onVideoKitFailed: ");

    }

    @Override
    public void onDeleteChatUnShownUseCaseSucceeded() {

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
        finish();
    }

    @Override
    public void onDeleteUsersChatRoomUseCaseFailed() {
        Log.e(TAG, "onDeleteUsersChatRoomUseCaseFailed: " );

    }

    // RequestSelectionExitChatRoomDialogFragment.Listener

    @Override
    public void onButtonExitClicked() {
        mDeleteUsersChatRoomUseCase.deleteUsersChatRoomAndNotify(mChatRoomId, mMeId);
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
        mChatArrayList.add(chat);
        mChatBotRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount() - 1);

        return chat;
    }

    private Chat addVideoToList(String message, String userId, String string_date, String chatType, String seenNum, String status, String size) {

        Chat chat = new Chat(string_date, mUserImage, message, mUserNickName, seenNum, chatType, userId, status, size);

        mChatArrayList.add(chat);
        mChatBotRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mChatBotRecyclerViewAdapter.getItemCount() - 1);

        return chat;
    }

    private AlertDialog setUpDialog() {

        String[] calls = new String[]{"영상 통화", "사진", "동영상", "카메라"};
        AlertDialog.Builder callSelect = new AlertDialog.Builder(this);
        callSelect.setItems(calls, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

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

    private void uploadFileWithChat(String mediaPath, String file_type, Chat chat) {

        if (file_type.equals(Constants.IMAGE_TYPE)) {
            mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());
            addChatToList(mediaPath, mMeId, mString_date, Constants.IMAGE_TYPE, "1", Constants.UPLOADED);
            File file = new File(mediaPath);
            String file_name = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
//            mUploadFileWithChatUseCase.uploadFileWithChatUseCaseAndNotify(fileToUpload, file_name, mChatRoomId, mMeId, mUserId, file_type, mString_date,  Constants.UPLOADED, chat);
        } else {
            File file = new File(mediaPath);
            String file_name = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
//            mUploadFileWithChatUseCase.uploadFileWithChatUseCaseAndNotify(fileToUpload, file_name, mChatRoomId, mMeId, mUserId, file_type, mString_date,  Constants.UPLOADED, chat);
        }


    }

    private void compressVideoFile(final String mediaPath) {

        File file = new File(mediaPath);
        String size = getFolderSizeLabel(file);

        mString_date = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA).format(new Date());

        final Chat chat = addVideoToList( mediaPath, mMeId, mString_date, Constants.VIDEO_TYPE, "1", Constants.BEFORE_COMPRSSING, size);

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
                mChatBotRecyclerViewAdapter.notifyDataSetChanged();
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

                Chat chat = new Chat("","", "", "", "", "", "", "", "");

                uploadFileWithChat(mediaPath, Constants.IMAGE_TYPE, chat);

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


    // ---------------------------------------------------------------------------------------------

    //ASYNCTASK
    public class NaverTranslateTask extends AsyncTask<String, Void, String> {

        public String resultText;
        //Naver
        String clientId = "msRBEOH06cK6oC2VDo5P";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "3v7ThmTtk6";//애플리케이션 클라이언트 시크릿값";
        //언어선택도 나중에 사용자가 선택할 수 있게 옵션 처리해 주면 된다.
        String sourceLang;
        String targetLang;

        public NaverTranslateTask(String sourceLang, String targetLang) {
            this.sourceLang = sourceLang;
            this.targetLang = targetLang;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //AsyncTask 메인처리
        @Override
        protected String doInBackground(String... strings) {
            //네이버제공 예제 복사해 넣자.
            //Log.d("AsyncTask:", "1.Background");

            String sourceText = strings[0];

            try {
                //String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8");
                String text = URLEncoder.encode(sourceText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source="+sourceLang+"&target="+targetLang+"&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                Log.e(TAG, "doInBackground: " + response.toString());

                //최종 결과 처리부
                //Log.d("background result", s.toString()); //네이버에 보내주는 응답결과가 JSON 데이터이다.

                //JSON데이터를 자바객체로 변환해야 한다.
                //Gson을 사용할 것이다.

                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObj = parser.parse(response.toString())
                        //원하는 데이터 까지 찾아 들어간다.
                        .getAsJsonObject().get("message")
                        .getAsJsonObject().get("result");
                //안드로이드 객체에 담기
                TranslatedItem items = gson.fromJson(rootObj.toString(), TranslatedItem.class);
                //Log.d("result", items.getTranslatedText());
                //번역결과를 텍스트뷰에 넣는다.

                return items.getTranslatedText();

            } catch (Exception e) {
                //System.out.println(e);
                Log.d("error", e.getMessage());
                return null;
            }
        }

        //번역된 결과를 받아서 처리
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        //자바용 그릇
        private class TranslatedItem {
            String translatedText;

            public String getTranslatedText() {
                return translatedText;
            }
        }
    }

}
