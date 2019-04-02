package com.changsune.changsu.yeontalk.screens.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.user.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersImageRecyclerViewAdapter extends
        RecyclerView.Adapter<UsersImageRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = UsersImageRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<User> list;
    private OnItemClickListener onItemClickListener;
    private ImageLoader mImageLoader;

    public UsersImageRecyclerViewAdapter(Context context, List<User> list,
                                         OnItemClickListener onItemClickListener,
                                         ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        mImageLoader = imageLoader;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.imageview_users_image_item_id)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final User model,
                         final OnItemClickListener listener,
                         Context context,
                         ImageLoader imageLoader) {
            int imageWidth = context.getResources().getDisplayMetrics().widthPixels / 3;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageWidth, imageWidth);
            mImageView.setLayoutParams(layoutParams);
            imageLoader.loadImage(model.getUserProfileImage(), mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition(), model.getUserId());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_users_image_list_item, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener, context, mImageLoader);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String userId);
    }

}