package com.changsune.changsu.yeontalk.screens.userdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.changsune.changsu.yeontalk.screens.common.imageloader.ImageLoader;
import com.changsune.changsu.yeontalk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryImageRecyclerViewAdapter extends
        RecyclerView.Adapter<GalleryImageRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = GalleryImageRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<String> list;
    private OnItemClickListener onItemClickListener;
    private RecyclerView mRecyclerView;
    private ImageLoader mImageLoader;

    public GalleryImageRecyclerViewAdapter(Context context, List<String> list,
                                           OnItemClickListener onItemClickListener,
                                           ImageLoader imageLoader,
                                           RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        mImageLoader = imageLoader;
        mRecyclerView = recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.imageview_user_gallery_images_item_id)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final String model,
                         final OnItemClickListener listener,
                         final ImageLoader imageLoader,
                         final RecyclerView recyclerView) {

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    int width_recyclerview = recyclerView.getWidth();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width_recyclerview * 1/5, width_recyclerview * 1/5);
                    mImageView.setLayoutParams(layoutParams);
                    imageLoader.loadImage(model, mImageView);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_user_gallery_image_list_item, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener, mImageLoader, mRecyclerView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}