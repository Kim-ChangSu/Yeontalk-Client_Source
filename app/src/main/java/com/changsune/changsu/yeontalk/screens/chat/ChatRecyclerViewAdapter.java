package com.changsune.changsu.yeontalk.screens.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.chat.Chat;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.screens.gallery.GalleryActivity;
import com.changsune.changsu.yeontalk.screens.video.VideoActivity;
import com.changsune.changsu.yeontalk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ChatRecyclerViewAdapter";

    public interface Listener {
        void onErrorButtonClicked(Chat chat);
    }

    // Making ViewHolder (a~z) ----------------------------------------------------------------------------------
    
    public class MeChatViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView_date;
        public TextView mTextView_message;
        public TextView mTextView_unshown;
        public LinearLayout mLinearLayout_error;
        ProgressBar mProgressBar;

        public MeChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_message = (TextView) itemView.findViewById(R.id.textview_message_me_item_id);
            mTextView_date = (TextView) itemView.findViewById(R.id.textview_date_me_item_id);
            mTextView_unshown = itemView.findViewById(R.id.textview_unshown_me_item_id);
            mLinearLayout_error = itemView.findViewById(R.id.linearlayout_chat_error_id);
            mProgressBar = itemView.findViewById(R.id.progress_circular_layout_chat_me_item_id);
        }
    }

    public class MeImageViewHolder extends RecyclerView.ViewHolder {
       
        public ImageView mImageView;
        public ImageView mImageView_play;
        public TextView mTextView_date;
        TextView mTextView_unshown;
        public ProgressBar mProgressBar_uploading;
        public ProgressBar mProgressBar;
        public TextView mTextView_size;
        public TextView mTextView_explanation;
        public LinearLayout mLinearLayout_error;

        public MeImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview_chat_me_item_id);
            mTextView_date = (TextView) itemView.findViewById(R.id.textview_date_me_item_id);
            mTextView_unshown = itemView.findViewById(R.id.textview_unshown_me_item_id);
            mImageView_play = (ImageView) itemView.findViewById(R.id.imageview_play_chat_me_item_id);
            mProgressBar_uploading = itemView.findViewById(R.id.progress_circular_layout_chat_me_item_id);
            mProgressBar = itemView.findViewById(R.id.progress_circular);
            mTextView_size = itemView.findViewById(R.id.textview_size);
            mTextView_explanation = itemView.findViewById(R.id.textview_explaination);
            mLinearLayout_error = itemView.findViewById(R.id.linearlayout_chat_error_id);
        }
    }



    public class YouChatViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mCircleImageView;
        public TextView mTextView_date_you;
        public TextView mTextView_message_you;
        public TextView mTextView_nickName_you;

        public YouChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.imageview_userimage_you_item_id);
            mTextView_nickName_you = (TextView) itemView.findViewById(R.id.textview_nickname_you_item_id);
            mTextView_message_you = (TextView) itemView.findViewById(R.id.textview_message_you_item_id);
            mTextView_date_you = (TextView) itemView.findViewById(R.id.textview_date_you_item_id);
        }

    }

    public class YouImageViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mCircleImageView;
        public ImageView mImageView;
        public ImageView mImageView_play;
        public TextView mTextView_date_you;
        public TextView mTextView_nickName_you;

        public YouImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.imageview_userimage_you_item_id);
            mTextView_nickName_you = (TextView) itemView.findViewById(R.id.textview_nickname_you_item_id);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview_chat_you_item_id);
            mImageView_play = (ImageView) itemView.findViewById(R.id.imageview_play_chat_you_item_id);
            mTextView_date_you = (TextView) itemView.findViewById(R.id.textview_date_you_item_id);
        }

    }

    public class ExitChatViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView_exit;

        public ExitChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_exit = itemView.findViewById(R.id.textview_exit_id);
        }

    }

    // ---------------------------------------------------------------------------------------------
    
    Context mContext;
    ArrayList<Chat> mData;
    ImageLoader mImageLoader;
    String mMeId;
    Listener mListener;


    public ChatRecyclerViewAdapter(Context context, ArrayList<Chat> data, ImageLoader imageLoader, String meId, Listener listener) {
        mContext = context;
        mData = data;
        mImageLoader = imageLoader;
        mMeId = meId;
        mListener = listener;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_me_item, viewGroup, false);
            return new MeChatViewHolder(view);
        } else if (i == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_you_item, viewGroup, false);
            return new YouChatViewHolder(view);
        } else if (i == 2) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_me_image_item, viewGroup, false);
            return new MeImageViewHolder(view);
        } else if (i == 3) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_you_image_item, viewGroup, false);
            return new YouImageViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_exit_item, viewGroup, false);
            return new ExitChatViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (!mData.get(position).getType().equals(Constants.EXIT_TYPE)) {
            if ((mData.get(position)).getUserId().equals(mMeId)) {
                if (((Chat) mData.get(position)).getType().equals(Constants.CHAT_TYPE) || ((Chat) mData.get(position)).getType().equals(Constants.VIDEO_CALL_TYPE)) {
                    return 0;
                } else {
                    return 2;
                }

            } else {
                if (((Chat) mData.get(position)).getType().equals(Constants.CHAT_TYPE) || ((Chat) mData.get(position)).getType().equals(Constants.VIDEO_CALL_TYPE)) {
                    return 1;
                } else {
                    return 3;
                }

            }
        } else {
            return 4;
        }


        
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final int position = i;
        final Chat object = mData.get(position);

        if (!object.getType().equals(Constants.EXIT_TYPE)) {

            String date_str;
            String date_item;
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA);
            Date currentTime = new Date();
            date_str = mSimpleDateFormat.format(currentTime);
            date_str = date_str.substring(0,9);

            if(object != null) {



                final String mDate;
                if (object.getDate() == null || object.getDate().length() < 17) {
                    mDate = object.getDate();
                } else {
                    //오늘과 비교하여, 시간, 날짜 표기법 달리함
                    date_item =object.getDate().substring(0,9);
                    if(date_item.equals(date_str)){
                        mDate = object.getDate().substring(17);
                    } else {
                        mDate = object.getDate().substring(2,9);
                    }
                }

                if (object.getUserId().equals(mMeId)) {

                    if (object.getType().equals(Constants.CHAT_TYPE) || object.getType().equals(Constants.VIDEO_CALL_TYPE)) {
                        ((MeChatViewHolder) viewHolder).mTextView_message.setText(object.getMessage());
                        ((MeChatViewHolder) viewHolder).mTextView_date.setVisibility(View.GONE);
                        ((MeChatViewHolder) viewHolder).mTextView_date.setText(mDate);
                        ((MeChatViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.GONE);
                        if (object.getStatus().equals(Constants.UPLOADING)) {
                            ((MeChatViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeChatViewHolder) viewHolder).mProgressBar.setVisibility(View.VISIBLE);
                        } else if (object.getStatus().equals(Constants.UPLOADINGFAIL)) {
                            ((MeChatViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeChatViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                            ((MeChatViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.VISIBLE);
                            ((MeChatViewHolder) viewHolder).mLinearLayout_error.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mListener.onErrorButtonClicked(object);
                                }
                            });

                        } else {
                            ((MeChatViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                            ((MeChatViewHolder) viewHolder).mTextView_date.setVisibility(View.VISIBLE);
                            if (object.getSeen().equals("0")) {
                                ((MeChatViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeChatViewHolder) viewHolder).mTextView_unshown.setText("");
                            } else {
                                ((MeChatViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeChatViewHolder) viewHolder).mTextView_unshown.setText(object.getSeen());
                            }
                        }

                    } else if (object.getType().equals(Constants.IMAGE_TYPE)) {

                        ((MeImageViewHolder) viewHolder).mImageView_play.setVisibility(View.GONE);
                        String[] split = object.getSize().split(">", 3);
                        String width = split[0];
                        String height = split[1];
                        settingImageView(((MeImageViewHolder) viewHolder).mImageView, width, height);

                        mImageLoader.loadChatImage((mData.get(position)).getMessage(), ((MeImageViewHolder) viewHolder).mImageView);

                        ((MeImageViewHolder) viewHolder).mTextView_size.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mImageView_play.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.GONE);

                        ((MeImageViewHolder) viewHolder).mTextView_date.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_date.setText(mDate);

                        ((MeImageViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.GONE);

                        if (object.getStatus().equals(Constants.UPLOADING)) {
                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.VISIBLE);
                        } else if (object.getStatus().equals(Constants.UPLOADINGFAIL)){
                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                            ((MeImageViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mLinearLayout_error.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mListener.onErrorButtonClicked(object);
                                }
                            });

                        } else {
                            ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.GONE);
                            ((MeImageViewHolder) viewHolder).mTextView_date.setVisibility(View.VISIBLE);
                            if (object.getSeen().equals("0")) {
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setText("");
                            } else {
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setText(object.getSeen());
                            }
                        }
                        ((MeImageViewHolder) viewHolder).mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> images = new ArrayList<>();
                                images.add(mData.get(i).getMessage());
                                GalleryActivity.start(mContext, images, 0);
                            }
                        });
                    } else  {

                        String[] split = object.getSize().split(">", 3);
                        String width = split[0];
                        String height = split[1];
                        String size = split[2];

                        Log.e(TAG, "onBindViewHolder: " + width + height + size);

                        settingImageView(((MeImageViewHolder) viewHolder).mImageView, width, height);

                        mImageLoader.loadChatVideoImage(((Chat) this.mData.get(position)).getMessage(), ((MeImageViewHolder) viewHolder).mImageView);

                        ((MeImageViewHolder) viewHolder).mTextView_date.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_date.setText(mDate);

                        if (object.getSeen().equals("0")) {
                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setText("");
                        } else {
                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setText(object.getSeen());
                        }

                        ((MeImageViewHolder) viewHolder).mTextView_size.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mImageView_play.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.GONE);
                        ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.GONE);

                        ((MeImageViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.GONE);

                        if (mData.get(position).getStatus().equals(Constants.BEFORE_COMPRSSING)) {

                            ((MeImageViewHolder) viewHolder).mTextView_size.setText(size);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setText("압축 대기 중...");
                            ((MeImageViewHolder) viewHolder).mTextView_size.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.VISIBLE);

                        } else if (mData.get(position).getStatus().equals(Constants.COMPRESSING)) {


                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setText("동영상 압축 중...");
                            ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.VISIBLE);

                        } else if (mData.get(position).getStatus().equals(Constants.AFTER_COMPRSSING)) {

                            ((MeImageViewHolder) viewHolder).mTextView_size.setText(size);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setText("전송 대기 중...");
                            ((MeImageViewHolder) viewHolder).mTextView_size.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.VISIBLE);


                        } else if (mData.get(position).getStatus().equals(Constants.UPLOADING)) {

                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setText("전송 중...");
                            ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.VISIBLE);

                        } else if (mData.get(position).getStatus().equals(Constants.UPLOADED)) {

                            ((MeImageViewHolder) viewHolder).mTextView_date.setVisibility(View.VISIBLE);

                            ((MeImageViewHolder) viewHolder).mImageView_play.setVisibility(View.VISIBLE);

                            ((MeImageViewHolder) viewHolder).mImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    VideoActivity.start(mContext, mData.get(position).getMessage());
                                }
                            });

                            ((MeImageViewHolder) viewHolder).mProgressBar_uploading.setVisibility(View.GONE);
                            if (object.getSeen().equals("0")) {
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setText("");
                            } else {
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.VISIBLE);
                                ((MeImageViewHolder) viewHolder).mTextView_unshown.setText(object.getSeen());
                            }

                        } else if (object.getStatus().equals(Constants.UPLOADINGFAIL)){

                            ((MeImageViewHolder) viewHolder).mTextView_date.setVisibility(View.GONE);

                            ((MeImageViewHolder) viewHolder).mTextView_size.setText(size);
//                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setText("전송 대기 중...");
                            ((MeImageViewHolder) viewHolder).mTextView_size.setVisibility(View.VISIBLE);
//                            ((MeImageViewHolder) viewHolder).mTextView_explanation.setVisibility(View.VISIBLE);

                            ((MeImageViewHolder) viewHolder).mTextView_unshown.setVisibility(View.INVISIBLE);
                            ((MeImageViewHolder) viewHolder).mProgressBar.setVisibility(View.GONE);
                            ((MeImageViewHolder) viewHolder).mLinearLayout_error.setVisibility(View.VISIBLE);
                            ((MeImageViewHolder) viewHolder).mLinearLayout_error.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mListener.onErrorButtonClicked(object);
                                }
                            });

                        } else {

                        }

                    }
                } else {

                    if (object.getType().equals(Constants.CHAT_TYPE) || object.getType().equals(Constants.VIDEO_CALL_TYPE)) {

                        ((YouChatViewHolder) viewHolder).mTextView_message_you.setText(object.getMessage());
                        ((YouChatViewHolder) viewHolder).mTextView_date_you.setText(mDate);
                        ((YouChatViewHolder) viewHolder).mTextView_nickName_you.setText(object.getNickname());
                        if (mData.get(i).getImage() == null || (mData.get(i)).getImage().equals("") ) {
                            ((YouChatViewHolder) viewHolder).mCircleImageView.setImageResource(R.drawable.ic_person_black_24dp);
                        } else {
                            mImageLoader.loadImage((mData.get(i)).getImage(), ((YouChatViewHolder) viewHolder).mCircleImageView);

                        }
                    } else if (object.getType().equals(Constants.IMAGE_TYPE)) {

                        ((YouImageViewHolder) viewHolder).mImageView_play.setVisibility(View.GONE);

                        String[] split = object.getSize().split(">", 3);
                        String width = split[0];
                        String height = split[1];
                        settingImageView(((YouImageViewHolder) viewHolder).mImageView, width, height);

                        mImageLoader.loadChatImage((mData.get(i)).getMessage(), ((YouImageViewHolder) viewHolder).mImageView);
                        ((YouImageViewHolder) viewHolder).mTextView_date_you.setText(mDate);
                        ((YouImageViewHolder) viewHolder).mTextView_nickName_you.setText(object.getNickname());
                        if ((mData.get(i)).getImage().equals("")) {
                            ((YouImageViewHolder) viewHolder).mCircleImageView.setImageResource(R.drawable.ic_person_black_24dp);
                        } else {
                            mImageLoader.loadImage((mData.get(i)).getImage(), ((YouImageViewHolder) viewHolder).mCircleImageView);

                        }
                        ((YouImageViewHolder) viewHolder).mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> images = new ArrayList<>();
                                images.add(mData.get(i).getMessage());
                                GalleryActivity.start(mContext, images, 0);
                            }
                        });

                    } else {

                        String[] split = object.getSize().split(">", 3);
                        String width = split[0];
                        String height = split[1];
                        String size = split[2];

                        Log.e(TAG, "onBindViewHolder: " + width + height + size);

                        settingImageView(((YouImageViewHolder) viewHolder).mImageView, width, height);

                        this.mImageLoader.loadChatVideoImage(((Chat) this.mData.get(i)).getMessage(), ((YouImageViewHolder) viewHolder).mImageView);
                        ((YouImageViewHolder) viewHolder).mTextView_date_you.setText(mDate);
                        ((YouImageViewHolder) viewHolder).mTextView_nickName_you.setText(object.getNickname());
                        if ((mData.get(i)).getImage().equals("")) {
                            ((YouImageViewHolder) viewHolder).mCircleImageView.setImageResource(R.drawable.ic_person_black_24dp);
                        } else {
                            mImageLoader.loadImage((mData.get(i)).getImage(), ((YouImageViewHolder) viewHolder).mCircleImageView);

                        }
                        ((YouImageViewHolder) viewHolder).mImageView_play.setVisibility(View.VISIBLE);
                        ((YouImageViewHolder) viewHolder).mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                VideoActivity.start(mContext, mData.get(i).getMessage());

                            }
                        });
                    }

                }
            }

        } else {

            ((ExitChatViewHolder) viewHolder).mTextView_exit.setText(object.getNickname() + "님이 채팅방에서 나가셨습니다.");

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void settingImageView(ImageView imageView, String width, String height) {

        int x = Integer.valueOf(width);
        int y = Integer.valueOf(height);

        if (x > y) {

            int imageWidth = mContext.getResources().getDisplayMetrics().widthPixels * 6 / 10;
            int imageHeight = imageWidth * y / x;

            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(imageWidth,imageHeight);
            imageView.setLayoutParams(parms);

        } else {

            int imageWidth = mContext.getResources().getDisplayMetrics().widthPixels * 4 / 10;
            int imageHeight = imageWidth * y / x;

            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(imageWidth,imageHeight);
            imageView.setLayoutParams(parms);

        }
    }
}
