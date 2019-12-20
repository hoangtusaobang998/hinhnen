package com.teamdev.hinhnen4k.model;

import java.io.Serializable;
import java.util.List;

public class IMG implements Serializable {

    private String url;
    private List<String> lisurl;

    public IMG(String url, List<String> lisurl) {
        this.url = url;
        this.lisurl = lisurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getLisurl() {
        return lisurl;
    }

    public void setLisurl(List<String> lisurl) {
        this.lisurl = lisurl;
    }
}
