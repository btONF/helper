package com.bt.helper.helper.Model;

import java.io.Serializable;

/**
 * Created by 18030693 on 2018/8/13.
 */

public class HomeGridItem implements Serializable{
    private String title;
    private int picRes;
    private int type;

    public HomeGridItem(String title, int picRes, int type) {
        this.title = title;
        this.picRes = picRes;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}
