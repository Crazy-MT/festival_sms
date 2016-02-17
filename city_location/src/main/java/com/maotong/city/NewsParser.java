package com.maotong.city;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public interface NewsParser {
    public CityBean parser(InputStream inputStream)throws Exception;
}
