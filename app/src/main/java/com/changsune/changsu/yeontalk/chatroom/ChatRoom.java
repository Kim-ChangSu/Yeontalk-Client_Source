package com.changsune.changsu.yeontalk.chatroom;

import com.google.gson.annotations.SerializedName;

public class ChatRoom {

    @SerializedName("chat_room_last_user_id")
    public String mLastChatUserId;
    @SerializedName("chat_room_last_date")
    public String mLastChatDate;
    @SerializedName("chat_room_last_message")
    public String mLastChatMessage;
    @SerializedName("chat_room_last_type")
    public String mLastChatType;
    @SerializedName("chat_room_id")
    public String mRoomId;
    @SerializedName("chat_room_user_id")
    public String mUserId;
    @SerializedName("chat_room_user_device_id")
    public String mUserDeviceId;
    @SerializedName("chat_room_user_image")
    public String mUserImage;
    @SerializedName("chat_room_user_nickname")
    public String mUserNickName;
    @SerializedName("chat_room_unshown_num")
    public String mUnshown_num;

    public ChatRoom(String lastChatUserId, String lastChatDate, String lastChatMessage, String lastChatType, String roomId, String userId, String userDeviceId, String userImage, String userNickName, String unshown_num) {
        mLastChatUserId = lastChatUserId;
        mLastChatDate = lastChatDate;
        mLastChatMessage = lastChatMessage;
        mLastChatType = lastChatType;
        mRoomId = roomId;
        mUserId = userId;
        mUserDeviceId = userDeviceId;
        mUserImage = userImage;
        mUserNickName = userNickName;
        mUnshown_num = unshown_num;
    }

    public String getLastChatUserId() {
        return mLastChatUserId;
    }

    public String getLastChatDate() {
        return mLastChatDate;
    }

    public String getLastChatMessage() {
        return mLastChatMessage;
    }

    public String getLastChatType() {
        return mLastChatType;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUserDeviceId() {
        return mUserDeviceId;
    }

    public String getUserImage() {
        return mUserImage;
    }

    public String getUserNickName() {
        return mUserNickName;
    }

    public String getUnshown_num() {
        return mUnshown_num;
    }
}
