package com.maomao.imageloader;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.maomao.imageloader.bean.FolderBean;
import com.maomao.imageloader.util.Imageloader;

import java.text.FieldPosition;
import java.util.List;

import static com.maomao.imageloader.R.id.id_dir_item_count;

/**
 * Created by Mao on 2015/9/22.
 */
public class ListImageDirPopupWindow extends PopupWindow {
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;
    public interface OnDirSelectedListenr{
        void onSeleted(FolderBean folderBean);
    }
    public OnDirSelectedListenr mListener ;

    public void setOnDirSelectedListenr(OnDirSelectedListenr mListener) {
        this.mListener = mListener;
    }

    public ListImageDirPopupWindow(Context context, List<FolderBean> datas) {
        calWidthAndHeight(context);

        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_main, null);
        mDatas = datas;
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initViews(context);
        initEvents();
    }

    private void initEvents() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null){
                    mListener.onSeleted(mDatas.get(position));
                }
            }
        });
    }

    private void initViews(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
        mListView.setAdapter(new ListDirAdapter(context,mDatas));
    }

    /**
     * 计算popupWindow的宽高
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mWidth = outMetrics.widthPixels;
        mHeight = (int) (outMetrics.heightPixels * 0.7);
    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean>{

        private LayoutInflater mInflater ;
        private List<FolderBean> mDatas ;

        public ListDirAdapter(Context context,   List<FolderBean> objects) {
            super(context, 0, objects);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null ;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_popup_main,parent,false);
                holder.mImg = (ImageView) convertView.findViewById(R.id.id_id_dir_item_image);
                holder.mDirName = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.mDirCount = (TextView) convertView.findViewById(id_dir_item_count);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            FolderBean bean = getItem(position);
            //重置
            holder.mImg.setImageResource(R.drawable.pictures_no);
            Imageloader.getInstance(3, Imageloader.Type.LIFO).loadImage(bean.getFirstImgPath(),holder.mImg);

            holder.mDirCount.setText(bean.getCount()+"");
            holder.mDirName.setText(bean.getName());

            return convertView;
        }

        private class ViewHolder{
            ImageView mImg ;
            TextView mDirName ;
            TextView mDirCount ;
        }
    }
}
