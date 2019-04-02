package com.changsune.changsu.yeontalk.screens.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.image.Image;
import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesProfileRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ImagesProfileRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<Image> list;
    private OnItemClickListener onItemClickListener;
    private ImageLoader mImageLoader;

    public ImagesProfileRecyclerViewAdapter(Context context, List<Image> list,
                                            OnItemClickListener onItemClickListener,
                                            ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        mImageLoader = imageLoader;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        @BindView(R.id.progress_circular_gallery_profile_id)
        public ProgressBar mProgressBar;
        @BindView(R.id.imageview_profile_imagelist_item_id)
        public ImageView mImageView;
        @BindView(R.id.imageview_profile_imagelist_clear_item_id)
        public ImageView mImageView_clear;
        @BindView(R.id.frame_layout_in_gallery_id)
        public FrameLayout mFrameLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Image model,
                         final OnItemClickListener listener) {
            mFrameLayout.setAlpha(0.5f);
            mImageLoader.loadImage(model.getImageUrl(), mImageView);

            if (model.getImageId() != null) {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                mImageView_clear.setVisibility(View.VISIBLE);
                mImageView_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClearButtonClicked(getLayoutPosition());

                    }
                });
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
                mImageView_clear.setVisibility(View.GONE);
            }

        }
    }

    public class AddImageViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        public AddImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPlusButtonClicked(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder;
        if (viewType == 0) {
            View view = inflater.inflate(R.layout.layout_images_list_profile_item, parent, false);
            ButterKnife.bind(this, view);
            viewHolder = new ImageViewHolder(view);

        } else {
            View view = inflater.inflate(R.layout.layout_images_list_add_icon_profile_item, parent, false);
            ButterKnife.bind(this, view);
            viewHolder = new AddImageViewHolder(view);
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //Todo: Setup viewholder for item

        if (holder.getItemViewType() == 0) {
            Image item = list.get(position);
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            ((ImageViewHolder) holder).bind(item, onItemClickListener);
        } else {
            AddImageViewHolder addImageViewHolder = (AddImageViewHolder) holder;
            ((AddImageViewHolder) holder).bind(onItemClickListener);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        int size;
        if (list.size() < 5) {
            size = list.size() + 1;
        } else {
            size = list.size();
        }
        return size;
    }

    public interface OnItemClickListener {
        void onClearButtonClicked(int position);
        void onPlusButtonClicked(int position);
    }

}