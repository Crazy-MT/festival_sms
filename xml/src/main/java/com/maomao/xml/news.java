package com.maomao.xml;

/**
 * Created by Mao on 2016/1/25.
 */
public class News {
    private String title ;
    private String image ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return title+image;
    }
}
