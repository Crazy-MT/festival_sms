package com.maomao.recycleviewtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout ;

    private List<String> mDatas;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
        mAdapter = new com.maomao.recycleviewtest.SimpleAdapter(this, mDatas);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this , position+" " , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this , position+" " , Toast.LENGTH_LONG).show();
            }
        });

        //设置布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置item分隔线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL_LIST));
    }

    private void initViews() {
        mRecyclerView = (MyRecyclerView) findViewById(R.id.id_recycleview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.addData(1 , mDatas.get(1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:

                mAdapter.addData(1 , mDatas.get(1));

                mAdapter.addData(2 , mDatas.get(2));

                mAdapter.addData(3 , mDatas.get(3));

                for (String data : mDatas){
                    Log.e("data" ,data);
                }

                // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_delete:
                mAdapter.deleteDate(1);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                //shareText();
                break;

            case R.id.action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;

            case R.id.action_listview:

                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_hor_gridview:

                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_staggered:
                Intent intent = new Intent(this, StaggeredGridLayoutActivity.class);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    //分享文字
    public void shareText() {

        File sdCardDir = Environment.getExternalStorageDirectory();
        File targetFile = null;
        try {
            targetFile = new File(sdCardDir.getCanonicalFile(), "AmberWeatherCity.docx");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(targetFile));
        shareIntent.setType("*/*");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
