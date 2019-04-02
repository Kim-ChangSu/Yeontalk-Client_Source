package com.changsune.changsu.yeontalk.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @SerializedName("user_birthyear")
    private final String mUserBirthYear;
    @SerializedName("user_device_id")
    private final String mUserDeviceId;
    @SerializedName("user_gender")
    private final String mUserGender;
    @SerializedName("user_id")
    private final String mUserId;
    @SerializedName("user_introduction")
    private final String mUserIntroduction;
    @SerializedName("user_login_time")
    private final String mUserLogInTime;
    @SerializedName("user_nation")
    private final String mUserNation;
    @SerializedName("user_nickname")
    private final String mUserNickname;
    @SerializedName("user_profile_image")
    private final String mUserProfileImage;
    @SerializedName("user_region")
    private final String mUserRegion;

    public User(String userBirthYear, String userDeviceId, String userGender, String userId, String userIntroduction, String userLogInTime, String userNation, String userNickname, String userProfileImage,String userRegion) {
        mUserBirthYear = userBirthYear;
        mUserDeviceId = userDeviceId;
        mUserGender = userGender;
        mUserId = userId;
        mUserIntroduction = userIntroduction;
        mUserLogInTime = userLogInTime;
        mUserNation = userNation;
        mUserNickname = userNickname;
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

    public String getUserProfileImage() {
        return mUserProfileImage;
    }

    public String getUserIntroduction() {
        return mUserIntroduction;
    }

    public String getUserLogInTime() {
        return mUserLogInTime;
    }

    public String getUserNation() {
        return mUserNation;
    }

    public String getUserNickname() {
        return mUserNickname;
    }

    public String getUserRegion() {
        return mUserRegion;
    }
}
