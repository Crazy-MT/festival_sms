package com.maotong.city;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CITY_ID_URL = "http://accuwxturbo.accu-weather.com/widget/accuwxturbo/city-find.asp?location=";
    private static final String CITY_BEAN_URL_FRONT = "http://accuwxturbo.accu-weather.com/widget/accuwxturbo/weather-data.asp?location=cityId:";
    private static final String CITY_BEAN_URL_BACK = "&metric=1&langid=13&partner=androidflagshipA";

    private TextView mCityText ;
    private List<CityBean> mCityBeanList ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    CityBean cityBean = (CityBean) msg.obj;
                    new CityBeanAsyncTask(cityBean).execute(CITY_BEAN_URL_FRONT+cityBean.getCityId()+CITY_BEAN_URL_BACK);
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {
        mCityBeanList = new ArrayList<>();
        new CityIdAsyncTask().execute(CITY_ID_URL,"Kyiv");
        new CityIdAsyncTask().execute(CITY_ID_URL,"NewDelhi");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCityText = (TextView) findViewById(R.id.text_city);
    }

    class CityIdAsyncTask extends AsyncTask<String,Void,CityBean>{


        @Override
        protected CityBean doInBackground(String... params) {
            return getCityId(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(CityBean cityBeen) {
            mCityBeanList.add(cityBeen);
            mCityText.setText(cityBeen.getCityId());
            Message message = new Message();
            message.what = 0 ;
            message.obj = cityBeen;
            mHandler.sendMessage(message);
        }

        private CityBean getCityId(String param , String cityName) {
            CityBean cityBean = null;
            try {
                InputStream inputStream = new URL(param+cityName).
                        openConnection().getInputStream();

                NewsParser parser = new PullCityIdParser();

                try {
                    cityBean = parser.parser(inputStream);
                    cityBean.setCityName(cityName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cityBean;
        }
    }

    class CityBeanAsyncTask extends AsyncTask<String,Void,CityBean>{

        private CityBean cityBean = null ;
        public CityBeanAsyncTask(CityBean cityBean){
            this.cityBean = cityBean;
            Log.e("MT bean" , cityBean.toString());
        }

        @Override
        protected CityBean doInBackground(String... params) {
            return getCityBean(params[0]);
        }

        @Override
        protected void onPostExecute(CityBean cityBeen) {
            mCityBeanList.add(cityBeen);
            mCityText.setText(cityBeen.getCityTimeZone());
            Log.e("MT bean" , cityBeen.toString());
        }

        private CityBean getCityBean(String param) {
            Log.e("MT" ,param);
            CityBean cityBeanParser = null ;
            try {
                InputStream inputStream = new URL(param).
                        openConnection().getInputStream();

                NewsParser parser = new PullCityBeanParser();
                try {
                    cityBeanParser = parser.parser(inputStream);
                    cityBean.setCityLat(cityBeanParser.getCityLat());
                    cityBean.setCityLon(cityBeanParser.getCityLon());
                    cityBean.setCityTimeZone(cityBeanParser.getCityTimeZone());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cityBean;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
