package com.wts.bharatsamachar.model;

public class MenuModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, String id,boolean isGroup, boolean hasChildren) {
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}