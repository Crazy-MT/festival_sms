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
public class PullCityIdParser implements NewsParser {
    @Override
    public CityBean parser(InputStream inputStream) throws Exception {

        CityBean cityBean = null ;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream,"UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){

            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    //Log.e("MT" , "START_DOCUMENT");
                    break;
                case XmlPullParser.START_TAG:

                    //Log.e("MT" , "START_TAG");
                    if (parser.getName().equals("citylist")){
                        cityBean = new CityBean() ;
                        //Log.e("MT" , "citylist");
                    } else if (parser.getName().equals("location")){
                        eventType = parser.next();
                        cityBean.setCityId(parser.getAttributeValue(null,"location"));
                        //Log.e("MT" , cityBean.getCityId());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    //Log.e("MT" , "END_TAG");
                    break;
            }
            eventType = parser.next();
        }
        return cityBean;
    }
}
