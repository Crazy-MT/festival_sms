package com.example.newslist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
public class MainActivity extends Activity {
	
	private ListView3d mListView;
	private HorizontalListView mHListView ;
	private static String URL = 
			"http://www.imooc.com/api/teacher?type=4&num=30";

	HorizontalScrollView horizontalScrollView;
	MyGridView gridView;
	DisplayMetrics dm;
	private int NUM = 4; // 每行显示个数

	private int LIEWIDTH;//每列宽度
	private int LIE = 12;//列数



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView3d) findViewById(R.id.lv_main);

		new NewsAsyncTask().execute(URL);

		/*mHListView = (HorizontalListView) findViewById(R.id.lv_main_horizontal);
		mHListView.setAdapter(new HorizontalListViewAdapter(this));*/
//
//		gridView = (MyGridView) findViewById(R.id.gridview);
//
//
//		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
//
//
//		horizontalScrollView.setHorizontalScrollBarEnabled(false);// 隐藏滚动条
//		getScreenDen();
//		LIEWIDTH = dm.widthPixels / NUM;
//		setValue();

//		String str = "" ;
//		for (int i = 0 ; i < 100000000 ; i++){
//			str += "xxxxxx";
//		}
//		Log.e("a" , str);
	}
	private void setValue() {
		MyGridViewAdapter adapter = new MyGridViewAdapter(this, LIE);
		gridView.setAdapter(adapter);
		LayoutParams params = new LayoutParams(adapter.getCount() * LIEWIDTH,
				LayoutParams.WRAP_CONTENT);
		gridView.setLayoutParams(params);
		gridView.setColumnWidth(dm.widthPixels / NUM);
		gridView.setStretchMode(GridView.NO_STRETCH);
		int count = adapter.getCount();
		gridView.setNumColumns(count);
	}
	private void getScreenDen() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	}
	class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>>
	{

		@Override
		protected List<NewsBean> doInBackground(String... params) {
			return getJosnData(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<NewsBean> newsbean) {
			// TODO Auto-generated method stub
			super.onPostExecute(newsbean);
			NewsAdapter adapter = new NewsAdapter(newsbean, MainActivity.this, mListView);
			mListView.setAdapter(adapter);
		}

		private String readString(InputStream is)
		{
			InputStreamReader isr;
			String result = "";
			try {
				String line = "";
				isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				while((line = br.readLine()) != null)
				{
					result += line;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}

		private List<NewsBean> getJosnData(String url) {
			List<NewsBean> newsBeanList = new ArrayList<NewsBean>();
			try {
				String jsonString = readString(new URL(url).
						openConnection().getInputStream());
				JSONObject jsonObject;			
				try {	
					jsonObject = new JSONObject(jsonString);
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i ++)
					{
						jsonObject = jsonArray.getJSONObject(i);
						NewsBean newsBean = new NewsBean();
						newsBean.setNewsIconUrl(jsonObject.getString("picSmall"));
						newsBean.setNewsTitle(jsonObject.getString("name"));
						newsBean.setNewsContent(jsonObject.getString("description"));	
						newsBeanList.add(newsBean);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();	
			}
			return newsBeanList;
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				// if it's not recycled, initialize some attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(mThumbIds[position]);
			return imageView;
		}

		// references to our images
		private Integer[] mThumbIds = {
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7,
				R.drawable.sample_0, R.drawable.sample_1,
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7,
				R.drawable.sample_0, R.drawable.sample_1,
				R.drawable.sample_2, R.drawable.sample_3,
				R.drawable.sample_4, R.drawable.sample_5,
				R.drawable.sample_6, R.drawable.sample_7
		};
	}
}
