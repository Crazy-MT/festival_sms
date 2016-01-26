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
/*

                    News newsOne = newsBeanList.get(3);
                    News newsTwo = newsBeanList.get(4);
                    News newsThree = newsBeanList.get(5);
                    mAdapter.addData(3 , newsOne);
                    mAdapter.addData(4 , newsTwo);
                    mAdapter.addData(5 , newsThree);
*/


                    int count = mRecyclerView.getChildCount() ;
                    News news0 = newsBeanList.get(count+0);
                    News news1 = newsBeanList.get(count+1);
                    News news2 = newsBeanList.get(count+2);
                    mAdapter.addData(count+0 , news0);
                    mAdapter.addData(count+1 , news1);
                    mAdapter.addData(count+2 , news2);

                    /*Log.e("count" , count + "");
                    for ( int i = 0 ; i < 3 ; i++){
                        mAdapter.addData(count+i , news);
                        Log.e("count + j" , newsBeanList.get(count+i).getTitle());
                    }*/
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
            if (newsBeanList!=null){
                haveData = true ;
                mAdapter = new NewsAdapter(MainActivity.this ,newsBeanList.subList(0, 3) , mRecyclerView);
                mRecyclerView.setAdapter(mAdapter);
                //设置布局管理
                MyLayoutManager linearLayoutManager = new MyLayoutManager(MainActivity.this );
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
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
                    int measuredHeight = view.getMeasuredHeight() * getItemCount();
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
