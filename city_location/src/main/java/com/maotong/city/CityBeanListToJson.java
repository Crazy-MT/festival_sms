package com.maotong.city;

import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaoTong on 2016/2/18.
 */
public class CityBeanListToJson {

    private List<CityBean> cityBeanList = new ArrayList<>();
    private List<CityBean> cityBeenListTimeZone = new ArrayList<>();
    private static String TAG = "CityBeanListToJson";
    private String[] timeZoneArray = null ;//{"12", "11", "10", "9.5", "9", "8.5", "8", "7", "6.5", "6", "5.75", "5.5", "5", "4.5", "4", "3.5", "3", "2", "1", "0", "-3", "-4", "-4.5", "-5", "-6", "-7", "-8", "-9", "",};
    private StringBuilder json = new StringBuilder();

    public CityBeanListToJson(List<CityBean> cityBeanList) {
        this.cityBeanList = cityBeanList;
        setTimeZoneArray();
    }

    public void setTimeZoneArray() {
        String[] timeZoneArray = new String[cityBeanList.size()] ;
        for (int i = 0; i < cityBeanList.size(); i++){
            CityBean cityBean = cityBeanList.get(i);
            timeZoneArray[i] = cityBean.getCityTimeZone();
        }

        List<String> list = new ArrayList<>();
        for (int i = 0 ; i < timeZoneArray.length; i++){
            if (!list.contains(timeZoneArray[i])){
                list.add(timeZoneArray[i]);
            }
        }

        this.timeZoneArray = list.toArray(new String[list.size()]);

        //Log.e(TAG,"time" + this.timeZoneArray.toString());
    }

    public String getJson() {
        for (String timeZone : timeZoneArray) {
            json.append("\n\n");
            json.append(createTimeZoneList(timeZone));
            json.append("\n\n");
        }
        return json.toString();
    }

    public String createTimeZoneList(String timeZone) {

        cityBeenListTimeZone = new ArrayList<>();
        for (CityBean cityBean : cityBeanList) {
            Log.e(TAG,cityBean.toString());
            Log.e(TAG,timeZone);
            Log.e(TAG,cityBean.getCityTimeZone());
            if (timeZone.equals(cityBean.getCityTimeZone())) {
                cityBeenListTimeZone.add(cityBean);
            }
        }
        json.append("time zone:" + timeZone + " city count:" + cityBeenListTimeZone.size() + " ");
        Log.e(TAG, cityBeenListTimeZone.size() + "");
        return createJsonTimeZone(timeZone);
    }

    private String createJsonTimeZone(String timeZone) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (CityBean cityBean : cityBeenListTimeZone) {
                JSONObject cityBeanJsonObject = new JSONObject();
                cityBeanJsonObject.put("cityname", cityBean.getCityName());
                cityBeanJsonObject.put("longitude", cityBean.getCityLon());
                cityBeanJsonObject.put("latitude", cityBean.getCityLat());
                cityBeanJsonObject.put("ishotcity",false);
                jsonArray.put(cityBeanJsonObject);
            }
            jsonObject.put("timezone" + timeZone, jsonArray);
            cityBeenListTimeZone = null;
            Log.e(TAG, jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
