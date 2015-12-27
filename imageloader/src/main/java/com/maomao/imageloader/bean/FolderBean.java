package com.maomao.imageloader.bean;

/**
 * Created by Mao on 2015/9/22.
 */
public class FolderBean {

    /**
     * 当前文件夹路径
     */
    private String dir ;
    private String firstImgPath ;
    private String name ;
    private int count ;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
