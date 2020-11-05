package com.amanahgithawisata.aga.Model;

public class ModelWebView {

    public int img;
    public String title;
    public String desc;
    public String url;

    public ModelWebView(int img, String title, String desc, String url){
        this.img = img;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
