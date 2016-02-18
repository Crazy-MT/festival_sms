package com.maotong.city;

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

    private List<CityBean> cityBeenList = new ArrayList<>();
    private List<CityBean> cityBeenListTimeZone = new ArrayList<>();
    private static String TAG = "CityBeanListToJson";
    private String[] timeZoneArray = {"12", "11", "10", "9.5", "9", "8.5", "8", "7", "6.5", "6", "5.75", "5.5", "5", "4.5", "4", "3.5", "3", "2", "1", "0", "-3", "-4", "-4.5", "-5", "-6", "-7", "-8", "-9", "null",};
    private StringBuilder json = new StringBuilder();

    public CityBeanListToJson(List<CityBean> cityBeanList) {
        this.cityBeenList = cityBeanList;
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
        for (CityBean cityBean : cityBeenList) {
            if (timeZone.equals(cityBean.getCityTimeZone())) {
                cityBeenListTimeZone.add(cityBean);
            }/* else if (TextUtils.isEmpty(cityBean.getCityTimeZone())) {
                cityBeenListTimeZone.add(cityBean);
                timeZone = "null";
            }*/
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
