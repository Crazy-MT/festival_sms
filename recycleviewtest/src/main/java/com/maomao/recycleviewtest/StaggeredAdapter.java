package com.maomao.recycleviewtest;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2015/12/22.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyViewHolder>{

    private LayoutInflater mInfalter ;
    private Context mContext ;
    private List<String> mDatas ;

    private List<Integer> mHeights ;
    public StaggeredAdapter(Context context, List<String> datas){
        this.mContext  = context ;
        this.mDatas = datas ;
        mInfalter = LayoutInflater.from(context);

        mHeights = new ArrayList<Integer>();
        for (int i = 0 ;  i < mDatas.size() ; i++){
            mHeights.add((int) (100+ Math.random()*300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInfalter.inflate(R.layout.item_single_textview , parent , false) ;
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    /*

     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);

        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv ;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_tv);
        }
    }
}

