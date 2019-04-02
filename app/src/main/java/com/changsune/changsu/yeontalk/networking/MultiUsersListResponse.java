package com.changsune.changsu.yeontalk.networking;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultiUsersListResponse {

    @SerializedName("fetch_multiple_userlist")
    List<UserListResponseSchema> mList_multiple_userlist;

    public MultiUsersListResponse(List<UserListResponseSchema> list_multiple_userlist) {
        mList_multiple_userlist = list_multiple_userlist;
    }

    public List<UserListResponseSchema> getList_multiple_userlist() {
        return mList_multiple_userlist;
    }

    public void setList_multiple_userlist(List<UserListResponseSchema> list_multiple_userlist) {
        mList_multiple_userlist = list_multiple_userlist;
    }
}
