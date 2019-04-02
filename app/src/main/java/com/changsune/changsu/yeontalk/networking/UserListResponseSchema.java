package com.changsune.changsu.yeontalk.networking;

import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.user.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserListResponseSchema {

    @SerializedName("fetch_me")
    List<Me> mUserList_me;
    @SerializedName("fetch_chatbot")
    List<User> mUserList_chatbot;
    @SerializedName("fetch_favorites")
    List<User> mUserList_favorite;
    @SerializedName("fetch_users")
    List<User> mUserList_users;

    public UserListResponseSchema(List<Me> userList_me, List<User> userList_chatbot, List<User> userList_favorite, List<User> userList_users) {
        mUserList_me = userList_me;
        mUserList_chatbot = userList_chatbot;
        mUserList_favorite = userList_favorite;
        mUserList_users = userList_users;
    }

    public List<Me> getUserList_me() {
        return mUserList_me;
    }

    public List<User> getUserList_chatbot() {
        return mUserList_chatbot;
    }

    public List<User> getUserList_favorite() {
        return mUserList_favorite;
    }

    public List<User> getUserList_users() {
        return mUserList_users;
    }
}
