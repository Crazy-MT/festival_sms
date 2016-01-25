package com.maomao.xml;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Mao on 2016/1/25.
 */
public interface NewsParser {
    public List<News> parser(InputStream inputStream)throws Exception;
}
