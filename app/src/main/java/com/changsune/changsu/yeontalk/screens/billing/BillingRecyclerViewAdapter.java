package com.changsune.changsu.yeontalk.screens.billing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changsune.changsu.yeontalk.billing.Billing;
import com.changsune.changsu.yeontalk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillingRecyclerViewAdapter extends
        RecyclerView.Adapter<BillingRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = BillingRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<Billing> list;
    private OnItemClickListener onItemClickListener;

    public BillingRecyclerViewAdapter(Context context, List<Billing> list,
                                      OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.tv_label)
        TextView mTextView_title;
        @BindView(R.id.tv_point)
        TextView mTextView_point;
        @BindView(R.id.tv_price)
        TextView mTextView_price;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Billing model,
                         final OnItemClickListener listener) {
            mTextView_title.setText(model.getTitle());
            mTextView_point.setText(model.getPoint() + "개");
            mTextView_price.setText(model.getPrice());
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

        View view = inflater.inflate(R.layout.layout_billing_item_list_id, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Billing item = list.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}