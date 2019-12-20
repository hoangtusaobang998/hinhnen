package com.teamdev.hinhnen4k;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamdev.hinhnen4k.adapter.AdapterSearch;
import com.teamdev.hinhnen4k.adapter.MyAdapter;
import com.teamdev.hinhnen4k.listen.ILoadMore;
import com.teamdev.hinhnen4k.listen.Listen;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGSearch;
import com.teamdev.hinhnen4k.task.DownloadAsynTaskIMG;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class SearchActivity extends AppCompatActivity implements Listen {

    private EditText search;
    public static final String URL_SEARCH = "https://www.pexels.com/vi-vn/tim-kiem/";
    public static final String URL = "https://www.pexels.com";
    public static final int TYPE_SEARCH = 999;
    private RecyclerView recyclerview_search, recyclerview_search_v1;
    private AdapterSearch adapterSearch;
    private LinearLayoutManager linearLayoutManager;
    private String titles = "";
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager1;
    private List<IMG> list = null;
    private ProgressBar loadingview;
    private int i = 2;
    private String title;
    private ACProgressPie dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mapped();
        add_recyclerview();
        add_recyclerview_v1();
        showKeyboard();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    titles = search.getText().toString().trim();
                    title = searchIMG(search.getText().toString().trim());
                    if (!title.equals("")) {
                        if (!list.isEmpty()) {
                            list.clear();
                            myAdapter.notifyDataSetChanged();
                        }
                        if (adapterSearch.getImgSearches() != null) {
                            adapterSearch.setImgSearches(null);
                        }
                        closeKeyboard();
                        if (dialog != null) {
                            if (!dialog.isShowing()) {
                                dialog.show();
                            }
                        }
                        new DownloadAsynTaskIMG(SearchActivity.this, TYPE_SEARCH).execute(URL_SEARCH + title);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void mapped() {
        search = findViewById(R.id.search);
        recyclerview_search = findViewById(R.id.recyclerview_search);
        recyclerview_search_v1 = findViewById(R.id.recyclerview_search_v1);
        loadingview = findViewById(R.id.loadingview);
        dialog = new ACProgressPie.Builder(this)
                .ringColor(Color.WHITE)
                .pieColor(Color.WHITE)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();

    }

    private void add_recyclerview() {
        linearLayoutManager = new GridLayoutManager(this, 2);
        adapterSearch = new AdapterSearch();
        recyclerview_search.setLayoutManager(linearLayoutManager);
        recyclerview_search.setHasFixedSize(true);
        recyclerview_search.setAdapter(adapterSearch);
        adapterSearch.setContext(this);
        adapterSearch.setClick(new AdapterSearch.Click() {
            @Override
            public void clickITEM(IMGSearch img, ImageView view) {
                titles=img.getTitle();
                title=img.getHref();
                list.clear();
                myAdapter.notifyDataSetChanged();
                Log.e("AAAAAAAAAAAA",URL+img.getHref());
                new DownloadAsynTaskIMG(SearchActivity.this, 0).execute(URL+img.getHref());
            }
        });
    }

    private void add_recyclerview_v1() {
        list = new ArrayList<>();
        linearLayoutManager1 = new GridLayoutManager(this, 2);
        recyclerview_search_v1.setLayoutManager(linearLayoutManager1);
        myAdapter = new MyAdapter(recyclerview_search_v1, this, list, this, getwidth());
        recyclerview_search_v1.setHasFixedSize(true);
        recyclerview_search_v1.setAdapter(myAdapter);
        myAdapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if (dialog != null) {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                }
                loadingview.setVisibility(View.VISIBLE);
                String url = URL_SEARCH + title + "?page=" + i + "&seed=&type=";

                if (title.startsWith("/vi-vn/tim-kiem/")){
                    String url1 = URL + title + "?page=" + i + "&seed=&type=";
                    Log.e("url_search1", url1);
                    new DownloadAsynTaskIMG(SearchActivity.this, 0).execute(url1);
                }else {
                    Log.e("url_search", url);
                    new DownloadAsynTaskIMG(SearchActivity.this, 0).execute(url);
                }

                i++;
                Log.e("I", i + "");
            }
        });
        myAdapter.setClick(new MyAdapter.Click() {
            @Override
            public void clickItems(IMG img, int position, ImageView view) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.DATA, img);
                startActivity(intent);
            }
        });
    }

    private int getwidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private String searchIMG(String search) {
        String title = "";
        if (search == null || search.equals("")) {
            Toast.makeText(this, getString(R.string.nhap_search), Toast.LENGTH_SHORT).show();
            return "";
        }
        String[] mang_search = search.split("\\s");
        for (int i = 0; i < mang_search.length; i++) {

            if (mang_search.length == 1) {
                title = mang_search[0] + "/";
            } else if (mang_search.length > 1) {

                if (i < mang_search.length - 1) {
                    title += mang_search[i] + "%20";
                }
                if (i == mang_search.length - 1) {
                    title += mang_search[i] + "/";
                }

            }

        }
        return title;
    }

    @Override
    public void onSussce(List<IMG> imgs, int type) {
        loadingview.setVisibility(View.GONE);
        list.addAll(imgs);
        search.setText("");
        search.setHint(getString(R.string.timkiem_cho) + " " + titles);
        myAdapter.notifyDataSetChanged();
        myAdapter.setLoaded();
        dialog.cancel();
    }

    @Override
    public void onSussceSearch(List<IMGSearch> imgSearches) {
        for (IMGSearch img : imgSearches) {
            Log.e("HREF", img.getTitle() + "-" + img.getHref() + "-" + img.getImg());
        }
        adapterSearch.setImgSearches(imgSearches);
    }

    @Override
    public void onFaied(int type) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }
        Toast.makeText(this, getString(R.string.errnetwork) + "-" + getString(R.string.khongtimthay)+"-"+getString(R.string.end), Toast.LENGTH_SHORT).show();
    }
}
