package com.changsune.changsu.yeontalk.networking;

import com.changsune.changsu.yeontalk.chat.Chat;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatListResponseSchema {
    ;
    @SerializedName("chat_list")
    private final List<Chat> mChatList;

    public ChatListResponseSchema(List<Chat> chatList) {
        mChatList = chatList;
    }

    public List<Chat> getChatList() {
        return mChatList;
    }
}
