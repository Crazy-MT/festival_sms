package widget.amberweather.com.news_card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Mao on 2015/12/22.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private LayoutInflater mInfalter;
    private Context mContext;
    private List<News> mDatas;
    private RecyclerView mRecyclerView ;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public NewsAdapter(Context context, List<News> datas , RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mContext = context;
        this.mDatas = datas;
        mInfalter = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInfalter.inflate(R.layout.item_news, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    /*

     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.news_title_text.setText(mDatas.get(position).getTitle());
        holder.news_title_date.setText(mDatas.get(position).getDate());
        String url = mDatas.get(position).getImage() ;
        holder.news_title_img.setTag(url);
        new ImageLoader(mRecyclerView).showImageByThread(holder.news_title_img , url);
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

    public void addData(int pos , News news) {
        mDatas.add(pos, news);
        notifyItemInserted(pos);
    }

    public void deleteDate(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView news_title_text;
        TextView news_title_date ;
        ImageView news_title_img ;

        public MyViewHolder(View itemView) {
            super(itemView);
            news_title_text = (TextView) itemView.findViewById(R.id.tv_news_title);
            news_title_date = (TextView) itemView.findViewById(R.id.tv_news_date);
            news_title_img = (ImageView) itemView.findViewById(R.id.img_news_pic);
        }
    }
}


