package com.maomao.particlenews;

/**
 * Created by infolife on 2016/1/25.
 */
public class UrlAdress {

    private static String BASE_URL = "http://openapi.particlenews.com/Website/openapi/news-list-for-channel?";
    private static String URL_APP = "app=amber";
    private static String URL_TOKEN = "&token=ba0ec50d6782";

    public enum ChannelId {
        //通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
        //赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
        Sports("c1"), Android("k81");

        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        ChannelId(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static String getOpenApiUrl(String channel_id) {
        return BASE_URL + URL_APP + URL_TOKEN + "&channel_id=" + channel_id;
    }

    public static String getImg(String type, String imgUrl) {
        return "http://img.particlenews.com/image.php?type=" + type + "&url=" + imgUrl ;
    }
}
