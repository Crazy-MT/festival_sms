package com.maomao.particlenews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mao on 2015/12/22.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {

    private LayoutInflater mInfalter;
    private Context mContext;
    private List<Bean> mDatas;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public SimpleAdapter(Context context, List<Bean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInfalter = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInfalter.inflate(R.layout.item_particle, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    /*

     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_title.setText(mDatas.get(position).getTitle());
        holder.tv_summary.setText(mDatas.get(position).getSummary());
        if (mOnItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView , holder.getLayoutPosition());
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

 /*   public void addData(int pos) {
        mDatas.add(pos, "Insert One");
        notifyItemInserted(pos);
    }*/

/*    public void deleteDate(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_summary ;
        ImageView img ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.text_title);
            tv_summary = (TextView) itemView.findViewById(R.id.text_summary);
            img = (ImageView) itemView.findViewById(R.id.img_pic);
        }
    }
}


