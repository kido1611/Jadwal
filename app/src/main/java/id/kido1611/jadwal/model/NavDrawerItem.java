package id.kido1611.jadwal.model;

import android.support.v4.app.Fragment;

/**
 * Created by Kido1611 on 22-Apr-16.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int resIcon;

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    private Fragment fragment;

    public NavDrawerItem() {
        fragment = null;
    }

    public NavDrawerItem(String title, Fragment fragment) {
        this.showNotify = false;
        this.title = title;
        this.fragment = fragment;
    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
        fragment = null;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getFragment(){
        return this.fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
