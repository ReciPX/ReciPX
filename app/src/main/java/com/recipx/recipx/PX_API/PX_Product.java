package com.recipx.recipx.PX_API;

public class PX_Product {
    private String title;
    private String standard;
    private String src;
    private String year;
    private String month;

    public PX_Product(){
        this.title = "";
        this.standard = "";
        this.src = "";
        this.year = "";
        this.month = "";
    }
    public PX_Product(String title, String standard, String src, String year, String month) {
        this.title = title;
        this.standard = standard;
        this.src = src;
        this.year = year;
        this.month = month;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
