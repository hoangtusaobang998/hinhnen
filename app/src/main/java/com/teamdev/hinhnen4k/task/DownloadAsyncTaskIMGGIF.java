package com.teamdev.hinhnen4k.task;

import android.os.AsyncTask;
import android.util.Log;

import com.teamdev.hinhnen4k.listen.Listen;
import com.teamdev.hinhnen4k.listen.ListenVideo;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGGIF;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadAsyncTaskIMGGIF extends AsyncTask<String, Void, List<IMGGIF>> {
    private static final String TAG = "DownloadTask";

    private ListenVideo listen;

    public DownloadAsyncTaskIMGGIF(ListenVideo listen) {
        this.listen = listen;
    }

    @Override
    protected List<IMGGIF> doInBackground(String... strings) {
        Document document = null;
        List<IMGGIF> listArticle = new ArrayList<>();
        try {
            document = (Document) Jsoup.connect(strings[0]).get();
            Elements sub = document.select("div.photos__column > div.hide-featured-badge.hide-favorite-badge > article");
            Log.e("SIZEZZZ", sub.size() + "");
            for (Element element : sub) {

                Log.e("url-video", element.attr("data-photo-modal-download-url"));
                Element a = element.getElementsByTag("a").first();
                Log.e("url-img", a.getElementsByTag("img").attr("srcset"));
                Element video = element.getElementsByTag("video").first();
                Element source = video.getElementsByTag("source").first();
                Log.e("url-video-source", source.attr("src"));
                listArticle.add(new IMGGIF(element.attr("data-photo-modal-download-url"),
                        a.getElementsByTag("img").attr("srcset"),
                        source.attr("src")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return listArticle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<IMGGIF> imggifs) {
        super.onPostExecute(imggifs);
        if (imggifs != null) {
            listen.Sussce(imggifs);
        } else {
            listen.Falied();
        }
    }

}
