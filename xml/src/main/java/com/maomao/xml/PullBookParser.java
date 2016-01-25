package com.maomao.xml;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public class PullBookParser implements NewsParser {
    @Override
    public List<News> parser(InputStream inputStream) throws Exception {
        List<News> newses = null ;
        News news = null ;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream,"UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    newses = new ArrayList<News>();

                    break;
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("channel")){
                        news = new News();
                        Log.e("pull","test");
                    }

                    if (parser.getName().equals("item")){
                        news = new News() ;
                        Log.e("pull1","test");
                    } else if (parser.getName().equals("title")){
                        Log.e("pull2","test");

                        eventType = parser.next();
                        Log.e("pull3",parser.getText());
                        news.setTitle(parser.getText());
                    } else if (parser.getName().equals("image")){
                        eventType = parser.next();
                        Log.e("pull4",parser.getText());
                        news.setImage(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")){
                        newses.add(news);
                        news = null ;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return newses;
    }
}
