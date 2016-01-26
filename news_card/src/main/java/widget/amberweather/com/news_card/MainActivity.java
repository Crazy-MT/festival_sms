package widget.amberweather.com.news_card;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private Button more_btn ;
    private boolean haveData = false ;
    private List<News> newsBeanList  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycle_view);
        more_btn = (Button) findViewById(R.id.btn_more);
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveData){
                    mAdapter.addData(3 , newsBeanList.get(3));
                } else {
                    Toast.makeText(getApplicationContext() , "没有数据" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initData() {
        newsBeanList = new ArrayList<News>();
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
            haveData = true ;
            mAdapter = new NewsAdapter(MainActivity.this ,newsBeanList.subList(0, 3) , mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            //设置布局管理
            MyLayoutManager linearLayoutManager = new MyLayoutManager(MainActivity.this );
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        public class MyLayoutManager extends LinearLayoutManager {

            public MyLayoutManager(Context context) {
                super(context);
                // TODO Auto-generated constructor stub
            }


            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                View view = recycler.getViewForPosition(0);
                if(view != null){
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measuredHeight = view.getMeasuredHeight();
                    setMeasuredDimension(measuredWidth, measuredHeight);
                }
            }
        }

        private List<News> getJsonData(String url) {


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
