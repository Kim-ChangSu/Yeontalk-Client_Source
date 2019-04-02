package com.changsune.changsu.yeontalk.billing;

import com.android.billingclient.api.SkuDetails;

public class Billing {
    String mTitle;
    String mPrice;
    String mPoint;

    public Billing(String title, String price, String point) {
        mTitle = title;
        mPrice = price;
        mPoint = point;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPoint() {
        return mPoint;
    }
}
