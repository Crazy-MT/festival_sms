package com.maotong.city;

import android.text.TextUtils;
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
public class PullCityIdParser implements NewsParser {
    @Override
    public CityBean parser(InputStream inputStream)  {

        CityBean cityBean = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");

        } catch (XmlPullParserException e) {
            Log.e("MT",e.toString());
            e.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = parser.getEventType();
        } catch (XmlPullParserException e) {
            Log.e("MT",e.toString());
            e.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT) {

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Log.e("MT", "START_DOCUMENT");
                    break;
                case XmlPullParser.START_TAG:

                    Log.e("MT", "START_TAG");
                    if (parser.getName().equals("citylist")) {
                        cityBean = new CityBean();
                        Log.e("MT", "citylist");
                    } else if (parser.getName().equals("location")) {
                        try {
                            eventType = parser.next();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cityBean.setCityId(parser.getAttributeValue(null, "location"));
                        Log.e("MT", "location");
                    }
                    break;
                case XmlPullParser.END_TAG:
                    Log.e("MT", "END_TAG");
                    break;
            }
            if (cityBean == null){
                try {
                    eventType = parser.next();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                Log.e("MT", "citybean null");
                if (!TextUtils.isEmpty(cityBean.getCityId())) {
                    eventType = XmlPullParser.END_DOCUMENT;
                }else{
                    try {
                        eventType = parser.next();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

/*
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                Log.e("MT",e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("MT", e.toString());
                e.printStackTrace();
            }*/
        }
        return cityBean;
    }
}
