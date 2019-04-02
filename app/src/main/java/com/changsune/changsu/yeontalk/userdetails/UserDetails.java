package com.changsune.changsu.yeontalk.userdetails;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDetails {

    @SerializedName("user_birthyear")
    private final String mUserBirthYear;
    @SerializedName("user_device_id")
    private final String mUserDeviceId;
    @SerializedName("user_gender")
    private final String mUserGender;
    @SerializedName("user_id")
    private final String mUserId;
    @SerializedName("user_images")
    private final ArrayList<String> mUserImages;
    @SerializedName("user_introduction")
    private final String mUserIntroduction;
    @SerializedName("user_login_time")
    private final String mUserLoginTime;
    @SerializedName("user_nation")
    private final String mUserNation;
    @SerializedName("user_nickname")
    private final String mUserNickName;
    @SerializedName("user_profile_image")
    private final String mUserProfileImage;
    @SerializedName("user_region")
    private final String mUserRegion;

    public UserDetails(String userBirthYear, String userDeviceId, String userGender, String userId, ArrayList<String> userImages, String userIntroduction, String userLoginTime, String userNation, String userNickName, String userProfileImage, String userRegion) {
        mUserBirthYear = userBirthYear;
        mUserDeviceId = userDeviceId;
        mUserGender = userGender;
        mUserId = userId;
        mUserImages = userImages;
        mUserIntroduction = userIntroduction;
        mUserLoginTime = userLoginTime;
        mUserNation = userNation;
        mUserNickName = userNickName;
        mUserProfileImage = userProfileImage;
        mUserRegion = userRegion;
    }

    public String getUserBirthYear() {
        return mUserBirthYear;
    }

    public String getUserDeviceId() {
        return mUserDeviceId;
    }

    public String getUserGender() {
        return mUserGender;
    }

    public String getUserId() {
        return mUserId;
    }

    public ArrayList<String> getUserImages() {
        return mUserImages;
    }

    public String getUserIntroduction() {
        return mUserIntroduction;
    }

    public String getUserLoginTime() {
        return mUserLoginTime;
    }

    public String getUserNation() {
        return mUserNation;
    }

    public String getUserNickName() {
        return mUserNickName;
    }

    public String getUserProfileImage() {
        return mUserProfileImage;
    }

    public String getUserRegion() {
        return mUserRegion;
    }
}
