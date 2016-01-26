package widget.amberweather.com.news_card;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private static String URL =
            "http://rss.gem.is/partner/amber.xml";
    private NewsAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycle_view);
    }

    private void initData() {
        new NewsAsyncTask().execute(URL);
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<News> newsBeanList) {
            super.onPostExecute(newsBeanList);

            Log.e("newsBeanListSize" , newsBeanList.size()+"");
            mAdapter = new NewsAdapter(MainActivity.this ,newsBeanList , mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            //设置布局管理
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        private List<News> getJsonData(String url) {
            List<News> newsBeanList = new ArrayList<News>();

            try {
                InputStream inputStream = new URL(url).
                        openConnection().getInputStream();
                NewsParser parser = new PullNewsParser();
                try {
                    newsBeanList = parser.parser(inputStream);
                    Log.e("newsBeanListSize" , newsBeanList.size()+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsBeanList;
        }
    }
}
