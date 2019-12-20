package com.teamdev.hinhnen4k.model;

public class IMGSearch {
    private String title;
    private String href;
    private String img;

    public IMGSearch(String title, String href, String img) {
        this.title = title;
        this.href = href;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
