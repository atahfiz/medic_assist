package com.example.tahfiz.medicassist.Drawer;

/**
 * Created by tahfiz on 28/4/2016.
 */
public class NavDrawerItem {

    public NavDrawerItem(){}

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private boolean showNotify;
    private String title;
}
