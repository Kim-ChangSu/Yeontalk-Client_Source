package com.changsune.changsu.yeontalk.networking;

import com.changsune.changsu.yeontalk.userdetails.UserDetails;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class UserDetailsResponseSchema {

    @SerializedName("users")
    private final List<UserDetails> mUserDetails;

    public UserDetailsResponseSchema(UserDetails userDetails) {
        mUserDetails = Collections.singletonList(userDetails);
    }


    public UserDetails getUser() {
        return mUserDetails.get(0);
    }
}
