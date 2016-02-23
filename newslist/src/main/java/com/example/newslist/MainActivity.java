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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private MyListView mListView;
	private static String URL = 
			"http://www.imooc.com/api/teacher?type=4&num=30";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (MyListView) findViewById(R.id.lv_main);
		new NewsAsyncTask().execute(URL);
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
}
