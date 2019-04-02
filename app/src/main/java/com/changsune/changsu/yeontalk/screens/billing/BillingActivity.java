package com.changsune.changsu.yeontalk.screens.billing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.billingclient.util.BillingHelper;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.billing.Billing;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.me.UpdateMeDataUseCase;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity implements PurchasesUpdatedListener
        , BillingRecyclerViewAdapter.OnItemClickListener, UpdateMeDataUseCase.Listener {

    // Static Variable (a~z)------------------------------------------------------------------------
    private static final String TAG = "BillingActivity";

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
    RecyclerView mRecyclerView;

    // SwipeRefreshLayout(a~z)

    // TabHost(a~z)

    // TabLayout(a~z)

    // TextView(a~z)
    TextView mTextView_point;

    // ViewPager(a~z)

    // ---------------------------------------------------------------------------------------------

    // Class Member Variable (a~z) -----------------------------------------------------------------

    // AlertDialog

    // ArrayList(a~z)
    ArrayList<Billing> mBillings;
    ArrayList<SkuDetails> mSkuDetailsArrayList;

    // BillingClient(a~z)
    private BillingClient mBillingClient;

    // Boolean(a~z)

    // Context(a~z)
    Context mContext;

    // Dialog(a~z)

    // DialogManager(a~z)

    // int(a~z)

    // ImageLoader(a~z)

    // LinearLayoutManager(a~z)
    LinearLayoutManager mLinearLayoutManager;

    // Networking(a~z)
    UpdateMeDataUseCase mUpdateMeDataUseCase;

    // Receiver(a~z)

    // RecyclerViewAdapter(a~z)
    BillingRecyclerViewAdapter mBillingRecyclerViewAdapter;

    // SharedPreference(a~z)
    SharedPreferences mSharedPreferences;

    // SkuDetails(a~z)
    SkuDetails mSkuDetails;

    // Socket(a~z)

    // String(a~z)
    String mUserId;
    String mPoint;
    String mPoint_purchased;
    String skuID = "android.test.purchased";

    // Thread(a~z)

    // VideoKit(a~z)

    // ViewPagerAdapter(a~z)

    // Extra Class(a~z)

    // ---------------------------------------------------------------------------------------------

    // Setting BroadCastReceiver -------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Starting Activity with Data -----------------------------------------------------------------

    public static void start(Context context) {
        Intent intent =  new Intent(context, BillingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        // Casting Layout --------------------------------------------------------------------------

        // Button(a~z)

        // CircleImageView(a~z)

        // CrystalRangeSeekBar(a~z)

        // EditText(a~z)

        // ImageButton(a~z)

        // LinearLayout(a~z)

        // NestedScrollView(a~z)

        // RecyclerView(a~z)
        mRecyclerView = findViewById(R.id.recycler_view_billing);

        // SwipeRefreshLayout(a~z)

        // TabHost(a~z)

        // TabLayout(a~z)

        // TextView(a~z)
        mTextView_point = findViewById(R.id.textview_point_billing_id);

        // ViewPager(a~z)

        // -----------------------------------------------------------------------------------------

        // Constructing Class ----------------------------------------------------------------------

        // ArrayList(a~z)
        mBillings = new ArrayList<>();
        mSkuDetailsArrayList = new ArrayList<>();

        // Context(a~z)
        mContext = BillingActivity.this;

        // Dialog(a~z)

        // DialogManager (a~z)

        // ImageLoader (a~z)

        // LayoutManager (a~z)
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Networking (a~z)
        mUpdateMeDataUseCase = new UpdateMeDataUseCase();

        // RecyclerViewAdapter (a~z)

        // SharedPreference(a~z)

        // String (a~z)

        // Thread(a~z)

        // VideoKit(a~z)

        // ViewPagerAdapter(a~z)

        // Extra Class(a~z)

        // -----------------------------------------------------------------------------------------

        // Receiving Data from Intent --------------------------------------------------------------

        //------------------------------------------------------------------------------------------

        // Receiving Data from SharedPreference ----------------------------------------------------

        mSharedPreferences = getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, MODE_PRIVATE);
        mPoint = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_POINT, "0");
        mUserId = mSharedPreferences.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, null);

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
        mBillingRecyclerViewAdapter = new BillingRecyclerViewAdapter(this, mBillings, this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mBillingRecyclerViewAdapter);

        // TabHost

        // TabLayout(a~z)

        // TextView(a~z)
        mTextView_point.setText(mPoint + "개");

        // Toolbar
        setUpToolbar();

        // ViewPager(a~z)

        //------------------------------------------------------------------------------------------

        // Setting RecyclerView --------------------------------------------------------------------

        mBillings.add(new Billing("별풍선","￦1000", "100"));
        mBillings.add(new Billing("별풍선", "￦5000", "500"));
        mBillings.add(new Billing("별풍선", "￦9000", "1000"));
        mBillings.add(new Billing("별풍선", "￦18000", "2000"));
        mBillings.add(new Billing("별풍선", "￦40000", "5000"));
        mBillings.add(new Billing("별풍선", "￦70000", "10000"));

        Log.e(TAG, "onSkuDetailsResponse: ");
        mBillingRecyclerViewAdapter.notifyDataSetChanged();

        // Setting BillingClient -------------------------------------------------------------------

        mBillingClient = BillingClient.newBuilder(mContext).setListener(this).build();

        Log.e(TAG, "onCreate: ");

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.

                    Log.e(TAG, "onBillingSetupFinished: ");

                    List<String> skuList = new ArrayList<> ();
                    skuList.add(skuID);
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                            // Process the result.
                            if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {

                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();

                                    mSkuDetails = skuDetails;

                                    for (int i = 0; i < mBillings.size(); i++) {
                                        mSkuDetailsArrayList.add(mSkuDetails);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.

                Log.e(TAG, "onBillingServiceDisconnected: ");
            }
        });

        // -----------------------------------------------------------------------------------------

    }

    // Setting Activity Life Cycle -----------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        mUpdateMeDataUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUpdateMeDataUseCase.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // Setting Listener implemented ----------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------

    // Setting OnClickListener ---------------------------------------------------------------------

    // BillingRecyclerViewAdapter.Listener
    @Override
    public void onItemClick(int position) {
        doBillingFlow(mSkuDetailsArrayList.get(position));
        mPoint_purchased = mBillings.get(position).getPoint();
    }

    // UpdateMeDataUseCase.Listener

    @Override
    public void onUpdateMeDataUseCaseSucceeded(String data_key, String data_value) {
        mPoint = data_value;
        mTextView_point.setText(mPoint + "개");
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.SHAREDPREF_KEY_PROFILE_POINT, mPoint);
        editor.commit();

    }

    @Override
    public void onUpdateMeDataUseCaseFailed() {

    }

    // ---------------------------------------------------------------------------------------------

    // Extra Methods -------------------------------------------------------------------------------

    private void doBillingFlow(SkuDetails skuDetails) {
        BillingFlowParams flowParams;
        int responseCode;

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        responseCode = mBillingClient.launchBillingFlow(BillingActivity.this, flowParams);

        if(responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
            onPurchasesUpdated(BillingClient.BillingResponse.OK, purchasesResult.getPurchasesList());
        }
    }

    private void handlePurchase(Purchase purchase) {
        String purchaseToken;
        purchaseToken = purchase.getPurchaseToken();
        mBillingClient.consumeAsync(purchaseToken, consumeListener);
    }

    ConsumeResponseListener consumeListener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
            if (responseCode == BillingClient.BillingResponse.OK) {
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.
                mUpdateMeDataUseCase.updateUserDataAndNotify(mUserId, Constants.POINT, String.valueOf(Integer.valueOf(mPoint)+Integer.valueOf(mPoint_purchased)));
            }
        }
    };

    /**
     * Handle a callback that purchases were updated from the Billing library
     */

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    private void setUpToolbar() {
        TextView textView_toolbar = findViewById(R.id.txt_toolbar_title);
        textView_toolbar.setText("별풍선 충전");

    }

    // ---------------------------------------------------------------------------------------------

}
