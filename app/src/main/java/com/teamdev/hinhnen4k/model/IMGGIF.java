package com.teamdev.hinhnen4k.model;

import java.io.Serializable;

public class IMGGIF implements Serializable {
    private String video_ur;
    private String img_url;
    private String video_load;

    public IMGGIF(String video_ur, String img_url, String video_load) {
        this.video_ur = video_ur;
        this.img_url = img_url;
        this.video_load = video_load;
    }

    public String getVideo_ur() {
        return video_ur;
    }

    public void setVideo_ur(String video_ur) {
        this.video_ur = video_ur;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVideo_load() {
        return video_load;
    }

    public void setVideo_load(String video_load) {
        this.video_load = video_load;
    }
}
