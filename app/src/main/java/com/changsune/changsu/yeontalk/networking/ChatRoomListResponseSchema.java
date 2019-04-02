package com.changsune.changsu.yeontalk.networking;

import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRoomListResponseSchema {

    @SerializedName("chat_room_list")
    private final List<ChatRoom> mChatRoomLists;

    public ChatRoomListResponseSchema(List<ChatRoom> chatRoomLists) {
        mChatRoomLists = chatRoomLists;
    }

    public List<ChatRoom> getChatRoomLists() {
        return mChatRoomLists;
    }
}
