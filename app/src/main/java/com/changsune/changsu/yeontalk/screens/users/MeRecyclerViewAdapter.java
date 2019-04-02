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
import com.changsune.changsu.yeontalk.me.Me;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.user.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeRecyclerViewAdapter extends
        RecyclerView.Adapter<MeRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MeRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<Me> list;
    private OnMeItemClickListener onMeItemClickListener;
    private ImageLoader mImageLoader;

    public MeRecyclerViewAdapter(Context context, List<Me> list,
                                 OnMeItemClickListener onItemClickListener,
                                 ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.onMeItemClickListener = onItemClickListener;
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

        public void bind(final Me model,
                         final OnMeItemClickListener listener,
                         final ImageLoader imageLoader) {
            textview_nickname.setText(model.getMeNickname());
            textview_gender.setText(model.getMeGender());
            if (model.getMeGender().equals("여자")) {
                textview_gender.setTextColor(Color.parseColor("#ff69b4"));
            } else {
                textview_gender.setTextColor(Color.parseColor("#0000ff"));
            }
            textview_age.setText(String.valueOf(2020 - Integer.valueOf(model.getMeBirthYear())) + Constants.AGE);
            if (!model.getMeIntroduction().equals("")) {
                textview_introduction.setVisibility(View.VISIBLE);
                textview_introduction.setText(Html.fromHtml(model.getMeIntroduction()));
            }
            if (!model.getMeProfileImage().equals("")) {
                imageLoader.loadImage(model.getMeProfileImage(), mImageView_image);
            } else {
                mImageView_image.setImageResource(R.drawable.ic_person_black_24dp);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMeItemClick(getLayoutPosition(), model.getMeId());

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
        Me item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onMeItemClickListener, mImageLoader);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnMeItemClickListener {
        void onMeItemClick(int position, String userId);
    }

}