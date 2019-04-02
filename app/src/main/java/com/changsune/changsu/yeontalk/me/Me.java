package com.changsune.changsu.yeontalk.me;

import com.changsune.changsu.yeontalk.image.Image;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Me {

    @SerializedName("me_birthyear")
    private final String mMeBirthYear;
    @SerializedName("me_device_id")
    private final String mMeDeviceId;
    @SerializedName("me_gender")
    private final String mMeGender;
    @SerializedName("me_id")
    private final String mMeId;
    @SerializedName("me_introduction")
    private final String mMeIntroduction;
    @SerializedName("me_login_time")
    private final String mMeLogInTime;
    @SerializedName("me_nation")
    private final String mMeNation;
    @SerializedName("me_nickname")
    private final String mMeNickname;
    @SerializedName("me_profile_image")
    private final String mMeProfileImage;
    @SerializedName("me_region")
    private final String mMeRegion;
    @SerializedName("me_point")
    private final String mMePoint;
    @SerializedName("me_images")
    private final ArrayList<Image> mMeImages;

    public Me(String meBirthYear, String meDeviceId, String meGender, String meId, String meIntroduction, String meLogInTime, String meNation, String meNickname, String meProfileImage,String meRegion, String mePoint, ArrayList<Image> meImages) {
        mMeBirthYear = meBirthYear;
        mMeDeviceId = meDeviceId;
        mMeGender = meGender;
        mMeId = meId;
        mMeIntroduction = meIntroduction;
        mMeLogInTime = meLogInTime;
        mMeNation = meNation;
        mMeNickname = meNickname;
        mMeProfileImage = meProfileImage;
        mMeRegion = meRegion;
        mMePoint = mePoint;
        mMeImages = meImages;
    }

    public String getMeBirthYear() {
        return mMeBirthYear;
    }

    public String getMeDeviceId() {
        return mMeDeviceId;
    }

    public String getMeGender() {
        return mMeGender;
    }

    public String getMeId() {
        return mMeId;
    }

    public String getMeProfileImage() {
        return mMeProfileImage;
    }

    public String getMeIntroduction() {
        return mMeIntroduction;
    }

    public String getMeLogInTime() {
        return mMeLogInTime;
    }

    public String getMeNation() {
        return mMeNation;
    }

    public String getMeNickname() {
        return mMeNickname;
    }

    public String getMeRegion() {
        return mMeRegion;
    }

    public String getMePoint() {
        return mMePoint;
    }

    public ArrayList<Image> getMeImages() {
        return mMeImages;
    }
}
