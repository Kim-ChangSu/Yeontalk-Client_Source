package com.changsune.changsu.yeontalk.chat;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("chat_date")
    public String mDate;
    @SerializedName("chat_user_image")
    public String mImage;
    @SerializedName("chat_message")
    public String mMessage;
    @SerializedName("chat_user_nickname")
    public String mNickname;
    @SerializedName("chat_seen")
    public String mSeen;
    @SerializedName("chat_type")
    public String mType;
    @SerializedName("chat_user_id")
    public String mUserId;
    @SerializedName("chat_status")
    public String mStatus;
    @SerializedName("chat_file_size")
    public String mSize;
    @SerializedName("chat_room_id")
    public String mRoomId;

    public Chat(String date, String image, String message, String nickname, String seen, String type, String userId, String status) {
        mDate = date;
        mImage = image;
        mMessage = message;
        mNickname = nickname;
        mSeen = seen;
        mType = type;
        mUserId = userId;
        mStatus = status;
    }

    public Chat(String date, String image, String message, String nickname, String seen, String type, String userId, String status, String size) {
        mDate = date;
        mImage = image;
        mMessage = message;
        mNickname = nickname;
        mSeen = seen;
        mType = type;
        mUserId = userId;
        mStatus = status;
        mSize = size;
    }

    public Chat(String date, String image, String message, String nickname, String seen, String type, String userId, String status, String roomId, String size) {
        mDate = date;
        mImage = image;
        mMessage = message;
        mNickname = nickname;
        mSeen = seen;
        mType = type;
        mUserId = userId;
        mStatus = status;
        mRoomId = roomId;
        mSize = size;
    }

    public String getDate() {
        return mDate;
    }

    public String getImage() {
        return mImage;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getNickname() {
        return mNickname;
    }

    public String getSeen() {
        return mSeen;
    }

    public String getType() {
        return mType;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getRoomId() {

        return mRoomId;
    }

    public void setSeen(String seen) {
        mSeen = seen;
    }
}
