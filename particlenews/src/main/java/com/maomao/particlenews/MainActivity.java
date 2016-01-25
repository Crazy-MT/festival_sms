package com.maomao.particlenews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "PARTICLE";
    public List<Bean> mDatas;
    private RecyclerView mRecyclerView;
    private SimpleAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);

    }

    private void initData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlAdress.getOpenApiUrl(UrlAdress.ChannelId.Android.getValue()))
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Log.e(TAG , response.body().string());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonParse(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mAdapter = new SimpleAdapter(getApplicationContext() , mDatas);
                        mRecyclerView.setAdapter(mAdapter);

                        //设置布局管理
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    }
                });


            }
        });
    }

    private void jsonParse(String json) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        Log.e(TAG , json);
        try {
            status = jsonObject.getString("status");
            Log.e(TAG , jsonObject.getString("status"));
            if ("success" == status) {
                JSONObject rs = null;
                JSONArray objectArr = jsonObject.getJSONArray("result");
                Bean bean = null;
                for (int i = 0; i < objectArr.length(); i++) {
                    rs = objectArr.getJSONObject(i) ;
                    if (rs != null){
                        bean.setImage(rs.getString("image"));
                        bean.setTitle(rs.getString("title"));
                        bean.setSummary(rs.getString("summary"));
                    }
                    if (bean != null){
                        mDatas.add(bean);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
