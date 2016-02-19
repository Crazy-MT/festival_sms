package com.maotong.city;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public class PullCityBeanParser implements NewsParser {
    @Override
    public CityBean parser(InputStream inputStream)   {
         CityBean cityBean = null ;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream,"UTF-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = parser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("local")){
                        cityBean = new CityBean() ;
                        //Log.e("MT" , "local");
                    } else if (parser.getName().equals("lat")){
                        try {
                            eventType = parser.next();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //Log.e("MT" , "lat");
                        cityBean.setCityLat(parser.getText());
                    } else if (parser.getName().equals("lon")){
                        try {
                            eventType = parser.next();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //Log.e("MT" , "lon");
                        cityBean.setCityLon(parser.getText());
                    } else if (parser.getName().equals("timeZone")){
                        try {
                            eventType = parser.next();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cityBean.setCityTimeZone(parser.getText());
                        //Log.e("MT" , parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cityBean;
    }
}
