package widget.amberweather.com.news_card;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public class PullNewsParser implements NewsParser {
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
                    }

                    if (parser.getName().equals("item")){
                        news = new News() ;
                    } else if (parser.getName().equals("title")){
                        eventType = parser.next();
                        news.setTitle(parser.getText());
                    } else if (parser.getName().equals("image")){
                        eventType = parser.next();
                        news.setImage(parser.getText());
                    } else if (parser.getName().equals("description")){
                        eventType = parser.next();
                        news.setDescription(parser.getText());
                    } else if (parser.getName().equals("pubDate")){
                        eventType = parser.next();
                        news.setDate(parser.getText());
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
