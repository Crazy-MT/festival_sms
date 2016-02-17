package com.maotong.city;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public class PullCityBeanParser implements NewsParser {
    @Override
    public CityBean parser(InputStream inputStream) throws Exception {
        List<CityBean> cityBeanList = null ;
        CityBean cityBean = null ;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream,"UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    cityBeanList = new ArrayList<CityBean>();

                    Log.e("MT" , "START_DOCUMENT");
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("local")){
                        cityBean = new CityBean() ;
                        Log.e("MT" , "local");
                    } else if (parser.getName().equals("lat")){
                        eventType = parser.next();
                        Log.e("MT" , "lat");
                        cityBean.setCityLat(parser.getText());
                    } else if (parser.getName().equals("lon")){
                        eventType = parser.next();
                        Log.e("MT" , "lon");
                        cityBean.setCityLon(parser.getText());
                    } else if (parser.getName().equals("timeZone")){
                        eventType = parser.next();
                        cityBean.setCityTimeZone(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("local")){
                        cityBeanList.add(cityBean);
                        //cityBean = null ;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return cityBean;
    }
}
