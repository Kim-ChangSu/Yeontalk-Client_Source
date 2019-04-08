package com.changsune.changsu.yeontalk.screens.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    // Static Variable (a~z)------------------------------------------------------------------------
    private static final String TAG = "GalleryActivity";

    public static final String EXTRA_USER_IMAGES = "EXTRA_USER_IMAGES";
    public static final String EXTRA_GALLERY_POSITION = "EXTRA_GALLERY_POSITION";


    // ---------------------------------------------------------------------------------------------

    // View Member Variable (a~z)-------------------------------------------------------------------

    // Button(a~z)

    // CircleImageView(a~z)

    // CrystalRangeSeekBar(a~z)

    // EditText(a~z)

    // ImageButton(a~z)

    // LinearLayout(a~z)

    // NestedScrollView(a~z)

    // RecyclerView(a~z)

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TabLayout(a~z)
    TabLayout mTabLayout;

    // TextView(a~z)

    // ViewPager(a~z)
    ViewPager mViewPager;

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)
    ArrayList<String> mImages;

    // Boolean(a~z)

    // Dialog(a~z)

    // DialogManager(a~z)

    // int(a~z)
    int mGalleryPosition;

    // ImageLoader(a~z)
    ImageLoader mImageLoader;

    // LinearLayoutManager(a~z)

    // Networking(a~z)

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)

    // SharedPreference(a~z)

    // Socket(a~z)

    // String(a~z)

    // Thread(a~z)

    // VideoKit(a~z)

    // ViewPagerAdapter(a~z)
    GalleryViewPagerAdapter mGalleryViewPagerAdapter;

    // Extra Class(a~z)

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context, ArrayList<String> images, int postion) {
        Intent intent =  new Intent(context, GalleryActivity.class);
        intent.putExtra(EXTRA_USER_IMAGES, images);
        intent.putExtra(EXTRA_GALLERY_POSITION, postion);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // CircleImageView(a~z)

        // CrystalRangeSeekBar(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)

        // NestedScrollView(a~z)

        // RecyclerView(a~z)

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TabLayout(a~z)
        mTabLayout = findViewById(R.id.tablayout_images);

        // TextView(a~z)

        // ViewPager(a~z)
        mViewPager= findViewById(R.id.viewpager_images_id);

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList (a~z)
        mImages = new ArrayList<>();

        // Dialog(a~z)

        // DialogManager (a~z)

        // ImageLoader (a~z)
        mImageLoader = new ImageLoader(this);

        // LayoutManager (a~z)

        // Networking (a~z)

        // RecyclerViewAdapter (a~z)

        // SharedPreference(a~z)

        // String (a~z)

        // Thread(a~z)

        // VideoKit(a~z)

        // ViewPagerAdapter(a~z)

        // Extra Class(a~z)

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------
        mGalleryPosition = getIntent().getExtras().getInt(EXTRA_GALLERY_POSITION);
        mImages = getIntent().getExtras().getStringArrayList(EXTRA_USER_IMAGES);

        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from DataBase ------------------------------------------------------------

        // -----------------------------------------------------------------------------------------

        // Setting Up View -------------------------------------------------------------------------

        // BottomNavView

        // Button

        // CircleImageView(a~z)

        // CrystalRangeSeekBar

        // LinearLayout(a~z)

        // RecyclerView(a~z)

        // TabHost

        // TabLayout(a~z)

        // TextView(a~z)

        // Toolbar

        // ViewPager(a~z)
        mGalleryViewPagerAdapter = new GalleryViewPagerAdapter(this, mImages, mImageLoader);
        mViewPager.setAdapter(mGalleryViewPagerAdapter);
        if (mImages.size() != 1) {
            mTabLayout.setupWithViewPager(mViewPager, true);
            mViewPager.setCurrentItem(mGalleryPosition);

        }

        //------------------------------------------------------------------------------------------
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
}
