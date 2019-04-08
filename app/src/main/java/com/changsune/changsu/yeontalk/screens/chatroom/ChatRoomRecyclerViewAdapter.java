package com.changsune.changsu.yeontalk.screens.chatroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.chatroom.ChatRoom;
import com.changsune.changsu.yeontalk.screens.chat.ChatActivity;
import com.changsune.changsu.yeontalk.screens.chatbot.ChatBotActivity;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {

    // Making ViewHolder (a~z) ---------------------------------------------------------------------

    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mCircleImageView_userImage;
        public TextView mTextView_last_chat;
        public TextView mTextView_last_chat_date;
        public TextView mTextView_userNickName;
        public TextView mTextView_unshown_num;
        RelativeLayout mRelativeLayout_unshown_num;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mCircleImageView_userImage = (CircleImageView) itemView.findViewById(R.id.circleimageview_image_chat_room_item_id);
            this.mTextView_userNickName = (TextView) itemView.findViewById(R.id.textview_nickname_chat_room_item_id);
            this.mTextView_last_chat = (TextView) itemView.findViewById(R.id.textview_last_chat_chat_room_item_id);
            this.mTextView_last_chat_date = (TextView) itemView.findViewById(R.id.textview_date_chat_room_item_id);
            mTextView_unshown_num = itemView.findViewById(R.id.textview_unshown_num_chat_room_item_id);
            mRelativeLayout_unshown_num = itemView.findViewById(R.id.relativeLayout_unshown_num_chat_room_item_id);
        }

    }

    // ---------------------------------------------------------------------------------------------

    private final Context mContext;
    private ArrayList<ChatRoom> mItems;
    ImageLoader mImageLoader;
    String mMeId;

    public ChatRoomRecyclerViewAdapter(Context context, ArrayList<ChatRoom> items, ImageLoader imageLoader, String meId) {
        mContext = context;
        mItems = items;
        mImageLoader = imageLoader;
        mMeId = meId;
    }

    @Override
    public ChatRoomViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chat_room_item, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomViewHolder viewHolder, final int i) {
        ChatRoom item = mItems.get(i);
        //TODO Fill in your logic for binding the view.

        String date_str;
        String date_item;
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA);
        Date currentTime = new Date();
        date_str = mSimpleDateFormat.format(currentTime);
        date_str = date_str.substring(0,9);

        if(item != null) {

            String mDate;
            if (item.getLastChatDate() == null || item.getLastChatDate().length() < 17) {
                mDate = item.getLastChatDate();
            } else {
                //오늘과 비교하여, 시간, 날짜 표기법 달리함
                date_item = item.getLastChatDate().substring(0, 9);
                if (date_item.equals(date_str)) {
                    mDate = item.getLastChatDate().substring(17);
                } else {
                    mDate = item.getLastChatDate().substring(2, 9);
                }
            }

            viewHolder.mTextView_userNickName.setText(((ChatRoom) mItems.get(i)).getUserNickName());
            viewHolder.mTextView_last_chat_date.setText(mDate);

            if (((ChatRoom) mItems.get(i)).getLastChatType().equals(Constants.CHAT_TYPE) || ((ChatRoom) mItems.get(i)).getLastChatType().equals(Constants.VIDEO_CALL_TYPE)) {
                viewHolder.mTextView_last_chat.setText(((ChatRoom) mItems.get(i)).getLastChatMessage());
            } else if (((ChatRoom) mItems.get(i)).getLastChatType().equals(Constants.IMAGE_TYPE)){
                if (mItems.get(i).getLastChatUserId().equals(mMeId)) {
                    viewHolder.mTextView_last_chat.setText("사진을 보냈습니다.");
                } else {
                    viewHolder.mTextView_last_chat.setText("사진을 보냈습니다.");
                }

            } else if (((ChatRoom) mItems.get(i)).getLastChatType().equals(Constants.VIDEO_TYPE)){
                if (mItems.get(i).getLastChatUserId().equals(mMeId)) {
                    viewHolder.mTextView_last_chat.setText("동영상을 보냈습니다.");
                } else {
                    viewHolder.mTextView_last_chat.setText("동영상을 보냈습니다.");
                }

            } else {
                viewHolder.mTextView_last_chat.setText("(알 수 없음)님이 채팅방에서 나가셨습니다.");
            }

            if(item.getUnshown_num().equals("0")) {
                viewHolder.mRelativeLayout_unshown_num.setVisibility(View.INVISIBLE);
            } else if(Integer.valueOf(item.getUnshown_num()) > 99) {
                viewHolder.mRelativeLayout_unshown_num.setVisibility(View.VISIBLE);
                viewHolder.mTextView_unshown_num.setText("99");
            } else {
                viewHolder.mRelativeLayout_unshown_num.setVisibility(View.VISIBLE);
                viewHolder.mTextView_unshown_num.setText(item.getUnshown_num());
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mItems.get(i).getUserDeviceId().equals("chatbot")) {
                        ChatBotActivity.start(mContext, mItems.get(i).getUserId(), mItems.get(i).getUserNickName(), mItems.get(i).getRoomId());
                    } else {
                        ChatActivity.start(mContext, mItems.get(i).getUserId(), mItems.get(i).getUserNickName(), mItems.get(i).getRoomId(), mItems.get(i).getUserImage());
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }
}