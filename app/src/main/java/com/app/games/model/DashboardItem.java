package com.app.games.model;

public class DashboardItem {
        private String value;
        private int logo;
        private int index;

    public DashboardItem(String value, int logo, int index) {
        this.value = value;
        this.logo = logo;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}