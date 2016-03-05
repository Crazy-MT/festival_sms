package com.maomao.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.maomao.imageloader.util.Imageloader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class  ImageAdapter extends BaseAdapter {

        private  String mDirPath ;
        private List<String> mImgPaths ;
        private LayoutInflater mInflater ;
        private static Set<String> mSelectImg = new HashSet<String>();

        public ImageAdapter(Context context , List<String> mDatas , String dirPath){

            mImgPaths = mDatas ;
            mDirPath = dirPath;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mImgPaths.size();
        }

        @Override
        public Object getItem(int position) {
            return mImgPaths.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final  ViewHolder viewHolder   ;
            if (convertView == null){
                convertView  = mInflater.inflate(R.layout.item_gridview , parent , false);
                viewHolder = new ViewHolder() ;
                viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_item_image);
                viewHolder.mSelect = (ImageButton) convertView.findViewById(R.id.id_item_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mImg.setImageResource(R.drawable.pictures_no);
            viewHolder.mSelect.setImageResource(R.drawable.picture_unselected);
            Imageloader.getInstance(3, Imageloader.Type.LIFO).loadImage(mDirPath + "/" + mImgPaths.get(position), viewHolder.mImg);
            viewHolder.mImg.setColorFilter(null);
            final String filePath = mDirPath + "/" + mImgPaths.get(position) ;
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectImg.contains(filePath)){
                        mSelectImg.remove(filePath);
                        viewHolder.mImg.setColorFilter(null);
                        viewHolder.mSelect.setImageResource(R.drawable.picture_unselected);
                    } else {
                        mSelectImg.add(filePath);
                        viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
                        viewHolder.mSelect.setImageResource(R.drawable.picture_selected);
                    }
                   // notifyDataSetChanged();

                }
            });

            if (mSelectImg.contains(filePath)){
                viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
                viewHolder.mSelect.setImageResource(R.drawable.picture_selected);
            }

            return convertView;
        }

        private class ViewHolder{
            ImageView mImg ;
            ImageButton mSelect ;
        }

    }
