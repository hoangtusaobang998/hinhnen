package com.teamdev.hinhnen4k.task;

import android.os.AsyncTask;
import android.util.Log;

import com.teamdev.hinhnen4k.listen.Listen;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.teamdev.hinhnen4k.SearchActivity.TYPE_SEARCH;

public class DownloadAsynTaskIMG extends AsyncTask<String, Void, List<IMG>> {
    private static final String TAG = "DownloadTask";

    private Listen listen;
    private int type;
    private List<IMGSearch> imgSearches;

    public DownloadAsynTaskIMG(Listen listen, int type) {
        this.listen = listen;
        this.type = type;
    }

    @Override
    protected List<IMG> doInBackground(String... strings) {
        Document document = null;
        List<IMG> listArticle = new ArrayList<>();
        imgSearches = new ArrayList<>();
        try {
            document = (Document) Jsoup.connect(strings[0]).get();
            if (document != null) {
                //js-search-suggestions search__suggestions
                if (type == TYPE_SEARCH) {
                    Elements sub = document.select("div.js-search-suggestions.search__suggestions > a");
                    for (Element element : sub) {
                        String title = element.attr("data-track-label");
                        String href = element.attr("href");
                        String img = element.getElementsByTag("img").attr("src");
                        imgSearches.add(new IMGSearch(title, href, img));
                    }
                }
                Elements sub = document.select("div.hide-featured-badge.hide-favorite-badge > article");
                for (Element element : sub) {
                    String[] listimg = element.attr("data-photo-modal-image-srcset").split(",");
                    String[] is = listimg[listimg.length - 1].trim().split("\\s");
                    listArticle.add(new IMG(is[0] + is[1], Arrays.asList(listimg)));
                }
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
    protected void onPostExecute(List<IMG> strings) {
        super.onPostExecute(strings);
        if (strings == null) {
            listen.onFaied(type);
        } else {
            if (type == TYPE_SEARCH) {
                if (!imgSearches.isEmpty()) {
                    listen.onSussceSearch(imgSearches);
                }
            }
            listen.onSussce(strings, type);
        }
    }
}
