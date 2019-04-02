package com.changsune.changsu.yeontalk.screens.users;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.user.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerViewAdapter extends
        RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = UsersRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<User> list;
    private OnUsersItemClickListener onItemClickListener;
    private ImageLoader mImageLoader;

    public UsersRecyclerViewAdapter(Context context, List<User> list,
                                    OnUsersItemClickListener onItemClickListener,
                                    ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        mImageLoader = imageLoader;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        @BindView(R.id.imageview_userimage_userslist_id)
        public CircleImageView mImageView_image;
        @BindView(R.id.textview_userage_userslist_id)
        public TextView textview_age;
        @BindView(R.id.textview_usergender_userslist_id)
        public TextView textview_gender;
        @BindView(R.id.textview_userintroduction_userslist_id)
        public TextView textview_introduction;
        @BindView(R.id.textview_usernickname_userslist_id)
        public TextView textview_nickname;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final User model,
                         final OnUsersItemClickListener listener,
                         final ImageLoader imageLoader) {
            textview_nickname.setText(model.getUserNickname());
            textview_gender.setText(model.getUserGender());
            if (model.getUserGender().equals("여자")) {
                textview_gender.setTextColor(Color.parseColor("#ff69b4"));
            } else {
                textview_gender.setTextColor(Color.parseColor("#0000ff"));
            }
            textview_age.setText(String.valueOf(2020 - Integer.valueOf(model.getUserBirthYear())) + Constants.AGE);
            if (!model.getUserIntroduction().equals("") && model.getUserIntroduction() != null) {
                textview_introduction.setVisibility(View.VISIBLE);
                textview_introduction.setText(Html.fromHtml(model.getUserIntroduction()));
            } else  {
                textview_introduction.setVisibility(View.GONE);
            }
            if (!model.getUserProfileImage().equals("")) {
                imageLoader.loadImage(model.getUserProfileImage(), mImageView_image);
            } else {
                mImageView_image.setImageResource(R.drawable.ic_person_black_24dp);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUsersItemClick(getLayoutPosition(), model.getUserId());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_user_list_item, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener, mImageLoader);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnUsersItemClickListener {
        void onUsersItemClick(int position, String userId);
    }

}