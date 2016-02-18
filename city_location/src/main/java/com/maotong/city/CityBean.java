package com.maotong.city;

import java.util.Comparator;

/**
 * Created by Mao on 2016/2/17.
 */
public class CityBean {

    private String cityName;
    private String cityId ;
    private String cityLat;
    private String cityLon;
    private String cityTimeZone;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getCityLon() {
        return cityLon;
    }

    public void setCityLon(String cityLon) {
        this.cityLon = cityLon;
    }

    public String getCityTimeZone() {
        return cityTimeZone;
    }

    public void setCityTimeZone(String cityTimeZone) {
        this.cityTimeZone = cityTimeZone;
    }

    @Override
    public String toString() {
        return "city id:" + cityId + " city name:" + cityName + " lat" + cityLat + " lon" + cityLon + " time zone" + cityTimeZone;
    }


}
