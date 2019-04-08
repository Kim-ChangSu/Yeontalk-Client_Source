package com.changsune.changsu.yeontalk.screens.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changsune.changsu.yeontalk.file.UploadFileUseCase;
import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionNickNameDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionUserDataDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.file.DeleteFileInGalleryUseCase;
import com.changsune.changsu.yeontalk.file.UploadFileInGalleryUseCase;
import com.changsune.changsu.yeontalk.file.UploadFileUseCase;
import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.screens.common.dialogs.DialogsManager;
import com.changsune.changsu.yeontalk.screens.common.dialogs.RequestSelectionDeleteImageDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionImageDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionNickNameDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.SelectionUserDataDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.dialogs.ServerErrorDialogFragment;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.instroduction.InstroductionActivity;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity implements SelectionUserDataDialogFragment.Listener,
        SelectionNickNameDialogFragment.Listener, UpdateMeDataUseCase.Listener, UploadFileUseCase.Listener,
        SelectionImageDialogFragment.Listener, ImagesProfileRecyclerViewAdapter.OnItemClickListener,
        UploadFileInGalleryUseCase.Listener, RequestSelectionDeleteImageDialogFragment.Listener,
        DeleteFileInGalleryUseCase.Listener{

    // Static Variable (a~z)------------------------------------------------------------------------

    private static final String TAG = "ProfileActivity";
    private static final int REQUEST_CAPTURE_PROFILE_IMAGE = 100;
    private static final int REQUEST_PICK_PHOTO_FILE_PROFILE_CODE = 200;
    private static final int REQUEST_CAPTURE_IMAGES_IMAGE = 300;
    private static final int REQUEST_PICK_PHOTO_FILE_IMAGES_CODE = 400;

    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // CircleImageView(a~z)
    private CircleImageView mCircleImageView;

    // CrystalRangeSeekBar(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // LinearLayout(a~z)
    private LinearLayout mLayout_profile_birthyear;
    private LinearLayout mLayout_profile_gender;
    private LinearLayout mLayout_profile_introduction;
    private LinearLayout mLayout_profile_nation;
    private LinearLayout mLayout_profile_region;

    // NestedScrollView(a~z)

    // RecyclerView(a~z)
    private RecyclerView mRecyclerView;

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TextView(a~z)
    private TextView mTextView_profile_birthyear;
    private TextView mTextView_profile_gender;
    private TextView mTextView_profile_introduction;
    private TextView mTextView_profile_nation;
    private TextView mTextView_profile_nickname;
    private TextView mTextView_profile_region;
    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)
    ArrayList<Image> mImages;

    // Boolean(a~z)

    // Dialog(a~z)
    Dialog mDialog;

    // DialogManager(a~z)
    DialogsManager mDialogsManager;

    // ImageLoader(a~z)
    ImageLoader mImageLoader;

    // LinearLayoutManager(a~z)
    LinearLayoutManager mLinearLayoutManager;

    // Networking(a~z)
    UpdateMeDataUseCase mUpdateMeDataUseCase;
    UploadFileUseCase mUploadFileUseCase;
    UploadFileInGalleryUseCase mUploadFileInGalleryUseCase;
    DeleteFileInGalleryUseCase mDeleteFileInGalleryUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)
    ImagesProfileRecyclerViewAdapter mImagesProfileRecyclerViewAdapter;

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences;
    SharedPreferences mSharedPreferences_images;

    // Socket(a~z)

    // String(a~z)
    private String mUserId;
    private String mNickName;
    private String mGender;
    private String mBirthYear;
    private String mNation;
    private String mRegion;
    private String mProfileImage;
    private String mIntroduction;

    private String mImagePathCaptured;

    // Thread(a~z)

    // VideoKit(a~z)

    // Extra Class(a~z)

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // CircleImageView(a~z)
        mCircleImageView = findViewById(R.id.imageview_profile_user_image_id);

        // CrystalRangeSeekBar(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)
        mLayout_profile_introduction = findViewById(R.id.linearlayout_introduction_profile_id);
        mLayout_profile_gender = findViewById(R.id.layout_profile_gender_id);
        mLayout_profile_birthyear = findViewById(R.id.layout_profile_birth_year_id);
        mLayout_profile_nation = findViewById(R.id.layout_profile_nation_id);
        mLayout_profile_region = findViewById(R.id.layout_profile_region_id);

        // NestedScrollView(a~z)

        // RecyclerView(a~z)
        mRecyclerView = findViewById(R.id.recyclerview_profile_images_list_id);

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TextView(a~z)
        mTextView_profile_nickname = findViewById(R.id.textview_profile_nickname_id);
        mTextView_profile_gender = findViewById(R.id.textview_profile_gender_id);
        mTextView_profile_birthyear = findViewById(R.id.textview_profile_birthyear_id);
        mTextView_profile_nation = findViewById(R.id.textview_profile_nation_id);
        mTextView_profile_region = findViewById(R.id.textview_profile_region_id);
        mTextView_profile_introduction = findViewById(R.id.textview_profile_introduction_id);

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        mImages = new ArrayList<>();

        // Dialog(a~z)
        setDialog();

        // DialogManager (a~z)
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

        // ImageLoader (a~z)
        mImageLoader = new ImageLoader(this);

        // LayoutManager (a~z)
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Networking (a~z)
        mUpdateMeDataUseCase = new UpdateMeDataUseCase();
        mUploadFileUseCase = new UploadFileUseCase();
        mUploadFileInGalleryUseCase = new UploadFileInGalleryUseCase();
        mDeleteFileInGalleryUseCase = new DeleteFileInGalleryUseCase();

        // RecyclerViewAdapter (a~z)

        mImagesProfileRecyclerViewAdapter = new ImagesProfileRecyclerViewAdapter(this, mImages, this, mImageLoader);

        // SharedPreference(a~z)
        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mSharedPreferences_images = getSharedPreferences(Constants.SHAREDPREF_KEY_IMAGES, MODE_PRIVATE);

        // String (a~z)

        // Thread(a~z)

        // Extra Class(a~z)

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------


        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------
        mUserId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, null);
        mNickName = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_NICKNAME, null);
        mGender = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_GENDER, null);
        mBirthYear = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_BIRTHYEAR, null);
        mNation = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_NATION, null);
        mRegion = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_REGION, null);
        mProfileImage = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_IMAGE, null);
        mIntroduction = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_INSTRODUCTION, null);


        for (int i = 0; i < mSharedPreferences_images.getAll().size(); i++) {
            String[] split = mSharedPreferences_images.getString(String.valueOf(i), null).split(">", 2);
            String imageId = split[0];
            String imageUrl = split[1];

            mImages.add(new Image(imageId, imageUrl));
        }

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------------

        // BottomNavView

        // Button

        // CircleImageView(a~z)
        if (mProfileImage != null && !mProfileImage.equals("")) {
            mImageLoader.loadImage(mProfileImage, mCircleImageView);
        }
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionImageDialog();
            }
        });
        // CrystalRangeSeekBar

        // LinearLayout(a~z)
        mLayout_profile_birthyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("생년", mBirthYear);

            }
        });

        mLayout_profile_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("성별", mGender);
            }
        });

        mLayout_profile_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("국가", mNation);

            }
        });

        mLayout_profile_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionUserDataDialog("지역", mRegion);
            }
        });
        mLayout_profile_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIntroductionLayoutClicked();
            }
        });

        // RecyclerView(a~z)
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mImagesProfileRecyclerViewAdapter);
        mRecyclerView.scrollToPosition(mImagesProfileRecyclerViewAdapter.getItemCount()-1);

        // TabHost

        // TextView(a~z)
        mTextView_profile_nickname.setText(mNickName);
        mTextView_profile_birthyear.setText(mBirthYear);
        mTextView_profile_gender.setText(mGender);
        mTextView_profile_nation.setText(mNation);
        if (mRegion == null) {
            mTextView_profile_region.setText(R.string.registration_default);
        } else {
            mTextView_profile_region.setText(mRegion);
        }
        mTextView_profile_introduction.setText(mIntroduction);
        mTextView_profile_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionNickNameDialog();
            }
        });
        // Toolbar
        setUpToolbar();

        //------------------------------------------------------------------------------------------
    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mUpdateMeDataUseCase.registerListener(this);
        mUploadFileUseCase.registerListener(this);
        mUploadFileInGalleryUseCase.registerListener(this);
        mDeleteFileInGalleryUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUpdateMeDataUseCase.unregisterListener(this);
        mUploadFileUseCase.unregisterListener(this);
        mUploadFileInGalleryUseCase.unregisterListener(this);
        mDeleteFileInGalleryUseCase.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mIntroduction = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_INSTRODUCTION, null);
        mTextView_profile_introduction.setText(mIntroduction);

    }

    // Setting Listener implemented ----------------------------------------------------------------

    // SelectionUserDataDialogFragment.Listener
    @Override
    public void onPositiveButtonClicked(String key, String value) {
        if (key.equals("성별")) {
            if (!mGender.equals(value)) {
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.GENDER, value);
            }
        } else if (key.equals("생년")) {
            if (!mBirthYear.equals(value)) {
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.BIRTHYEAR, value);
            }
        } else if (key.equals("국가")) {
            if (!mNation.equals(value)) {
                Log.e(TAG, "onPositiveButtonClicked: ");
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.NATION, value);
            }
        } else if (key.equals("지역")) {
            if (mRegion != null) {
                if (!mRegion.equals(value)) {
                    mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.REGION, value);
                }
            } else {
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.REGION, value);
            }

        }
    }

    // SelectionNickNameDialogFragment.Listener
    @Override
    public void onPositiveButtonClicked(String nickName) {
        if (!mNickName.equals(nickName)) {
            mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.NICKNAME, nickName);
        }
    }

    @Override
    public void onUpdateMeDataUseCaseSucceeded(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (key.equals(Constants.NICKNAME)) {
            mNickName = value;
            mTextView_profile_nickname.setText(mNickName);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NICKNAME, mNickName);
        } else if (key.equals(Constants.GENDER)) {
            mGender = value;
            mTextView_profile_gender.setText(mGender);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_GENDER, mGender);
        } else if (key.equals(Constants.BIRTHYEAR)) {
            mBirthYear = value;
            mTextView_profile_birthyear.setText(mBirthYear);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_BIRTHYEAR, mBirthYear);
        } else if (key.equals(Constants.NATION)) {
            mNation = value;
            mTextView_profile_nation.setText(mNation);
            mRegion = null;
            mTextView_profile_region.setText(R.string.registration_default);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NATION, mNation);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_REGION, mRegion);
        } else if (key.equals(Constants.REGION)) {
            mRegion = value;
            mTextView_profile_region.setText(mRegion);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_REGION, mRegion);
        } else if (key.equals(Constants.IMAGE)) {
            mProfileImage = value;
            mCircleImageView.setImageResource(R.drawable.ic_person_black_24dp);
            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_IMAGE, value);
        }

        editor.commit();
    }

    @Override
    public void onUpdateMeDataUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // UploadFileUseCase.Listener
    @Override
    public void onUploadFileUseCaseSucceeded(String fimeName) {
        mProfileImage = Constants.BASE_FILE_URL + fimeName;
        mImageLoader.loadImage(mProfileImage, mCircleImageView);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_IMAGE, mProfileImage);
        editor.commit();


    }

    @Override
    public void onUploadFileUseCaseFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    // SelectionImageDialogFragment.Listener

    @Override
    public void onButtonProfileImageClicked(int i) {

        if (i == 0) {
            // open Camera Intent
            openCameraIntent(REQUEST_CAPTURE_PROFILE_IMAGE);


        } else if (i == 1) {
            // open Album Intent
            openAlbumIntent(REQUEST_PICK_PHOTO_FILE_PROFILE_CODE);

        } else {
            if (mProfileImage != null && !mProfileImage.equals("")) {
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.IMAGE, "");
            } else {
                Log.e(TAG, "onButtonProfileImageClicked: ");
            }
        }

    }

    @Override
    public void onButtonImagesImageClicked(int i) {
        if (i == 0) {
            // open Camera Intent
            openCameraIntent(REQUEST_CAPTURE_IMAGES_IMAGE);
        } else {
            // open Album Intent
            openAlbumIntentForGallery(REQUEST_PICK_PHOTO_FILE_IMAGES_CODE);
        }

    }

    // ImagesProfileRecyclerViewAdapter.Listener
    @Override
    public void onClearButtonClicked(int position) {
        mDialogsManager.showDialogWithId(RequestSelectionDeleteImageDialogFragment.newInstance(this, position), "");

    }

    @Override
    public void onPlusButtonClicked(int position) {
        mDialogsManager.showDialogWithId(SelectionImageDialogFragment.newInstance(this), Constants.GALLERY);
    }

    // UploadFileInGallery.Listener
    @Override
    public void onUploadFileInGalleryUseCaseSucceeded(List<Image> ex_images, List<Image> new_images) {
        Log.e(TAG, "onUploadFileInGalleryUseCaseSucceeded: ");

        mImages.clear();
        mImages.addAll(new_images);

        mDialog.dismiss();

        mImagesProfileRecyclerViewAdapter.notifyDataSetChanged();
        SharedPreferences.Editor editor = mSharedPreferences_images.edit();
        for (Image i : new_images) {
            editor.putString(String.valueOf(mSharedPreferences_images.getAll().size()), i.getImageId() + ">" + i.getImageUrl());
            editor.commit();
        }

    }

    @Override
    public void onUploadFileInGalleryUseCaseFailed() {
        mDialog.dismiss();
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }


    // RequestSelectionDeleteImageDialogFragment.Listener
    @Override
    public void onPositiveButtonClicked(int position) {
        mDeleteFileInGalleryUseCase.deleteImageInGallery(mUserId, mImages.get(position).getImageId());
    }

    // DeleteFileInGalleryUseCase.Listener
    @Override
    public void onDeleteImageSucceeded(String image_id) {
        int num = 0;
        for (int i = 0; i < mImages.size(); i++) {
            if (mImages.get(i).getImageId().equals(image_id)) {
                num = i;
            }
        }

        mImages.remove(num);
        SharedPreferences.Editor editor1 = mSharedPreferences_images.edit();
        editor1.clear();
        for (int i = 0; i < mImages.size(); i++) {
            editor1.putString(String.valueOf(i), mImages.get(i).getImageId() + ">" + mImages.get(i).getImageUrl());
        }
        editor1.commit();
        mImagesProfileRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDeleteImageFailed() {
        mDialogsManager.showDialogWithId(ServerErrorDialogFragment.newInstance(),"");

    }


    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    private void onIntroductionLayoutClicked() {
        InstroductionActivity.start(this, mUserId, mIntroduction);
    }
    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("내 프로필");

    }

    private void openSelectionNickNameDialog() {
        mDialogsManager.showDialogWithId(SelectionNickNameDialogFragment.newInstance(this), mNickName);
    }

    private void openSelectionUserDataDialog(String key, String value) {

        if (key.equals("지역")) {
            mDialogsManager.showDialogWithId(SelectionUserDataDialogFragment.newInstance(this, value, mNation), key);
        } else {
            mDialogsManager.showDialogWithId(SelectionUserDataDialogFragment.newInstance(this, value), key);
        }
    }

    private void openSelectionImageDialog() {
        mDialogsManager.showDialogWithId(SelectionImageDialogFragment.newInstance(this), Constants.IMAGE);
    }

    private void openCameraIntent(int code) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent,
                        code);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mImagePathCaptured = image.getAbsolutePath();
        return image;
    }

    private void openAlbumIntent(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), code);
    }

    private void openAlbumIntentForGallery(int code) {
        Intent intent = new Intent(this, AlbumSelectActivity.class);
        intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, 5-mImages.size());
        startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAPTURE_PROFILE_IMAGE && resultCode == RESULT_OK && null != data) {
            File file = new File(mImagePathCaptured);
            String filename = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));

            Log.e(TAG, "onActivityResult: ");
            mUploadFileUseCase.uploadFileAndNotify(fileToUpload, filename, mUserId);
        }
        // When an Image is picked
        else if (requestCode == REQUEST_PICK_PHOTO_FILE_PROFILE_CODE && resultCode == RESULT_OK && null != data) {

            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String mediaPath = cursor.getString(columnIndex);
            cursor.close();

            File file = new File(mediaPath);
            String filename = file.getName();
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));

            mUploadFileUseCase.uploadFileAndNotify(fileToUpload, filename, mUserId);

        } else if (requestCode == REQUEST_CAPTURE_IMAGES_IMAGE && resultCode == RESULT_OK && null != data) {
            ArrayList<Image> imagelist_from_gallery = new ArrayList<>();
            ArrayList<com.darsh.multipleimageselect.models.Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);

            Log.e(TAG, "onActivityResult: " + images.size());
            for (int i = 0; i < 1; i++) {

                imagelist_from_gallery.add(new Image(null, mImagePathCaptured));
            }

            mImages.addAll(imagelist_from_gallery);
            mImagesProfileRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mImagesProfileRecyclerViewAdapter.getItemCount()-1);

            for (int i = 0; i < 4; i++) {
                imagelist_from_gallery.add(null);
            }

            Log.e(TAG, "onActivityResult: ");
            mUploadFileInGalleryUseCase.uploadFileInGalleryAndNotify(imagelist_from_gallery, mUserId);
        }
        // When an Image is picked
        else if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            ArrayList<Image> imagelist_from_gallery = new ArrayList<>();
            ArrayList<com.darsh.multipleimageselect.models.Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);

            Log.e(TAG, "onActivityResult: " + images.size());
            for (int i = 0; i < images.size(); i++) {

                imagelist_from_gallery.add(new Image(null, images.get(i).path));
            }

            mImages.addAll(imagelist_from_gallery);
            mImagesProfileRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mImagesProfileRecyclerViewAdapter.getItemCount()-1);

            for (int i = 0; i < 5-images.size(); i++) {
                imagelist_from_gallery.add(null);
            }

            mDialog.show();
            mUploadFileInGalleryUseCase.uploadFileInGalleryAndNotify(imagelist_from_gallery, mUserId);

        } else {
            Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
        }

    }

    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress);
        mDialog = builder.create();
    }

    // ---------------------------------------------------------------------------------------------
}
