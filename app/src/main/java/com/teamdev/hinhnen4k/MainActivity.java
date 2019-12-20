package com.teamdev.hinhnen4k;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;
import com.teamdev.hinhnen4k.adapter.MyAdapterVideo;
import com.teamdev.hinhnen4k.adapter.RecyclerviewAdapter;
import com.teamdev.hinhnen4k.listen.Listen;

import com.teamdev.hinhnen4k.listen.ListenVideo;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGGIF;
import com.teamdev.hinhnen4k.model.IMGSearch;

import com.teamdev.hinhnen4k.task.DownloadAsynTaskIMG;
import com.teamdev.hinhnen4k.task.DownloadAsyncTaskIMGGIF;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Listen, RecyclerviewAdapter.Click, View.OnClickListener, ListenVideo {
    private NestedScrollView scrollView;
    private RecyclerView recyclerView, recyclerView_v1, recyclerView_v2, recyclerView_v3, recyclerView_v4, recyclerView_v5, aah_customRecyclerView;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager_v1, linearLayoutManager_v2, linearLayoutManager_v3, linearLayoutManager_v4, linearLayoutManager_v5;
    private RecyclerviewAdapter recyclerviewAdapter, recyclerviewAdapter_v1, recyclerviewAdapter_v2, recyclerviewAdapter_v3, recyclerviewAdapter_v4, recyclerviewAdapter_v5;
    private static final String[] URL = {
            "https://www.pexels.com/vi-vn/tim-kiem/girl/",
            "https://www.pexels.com/vi-vn/tim-kiem/boy/",
            "https://www.pexels.com/vi-vn/tim-kiem/nature/",
            "https://www.pexels.com/vi-vn/tim-kiem/noel/",
            "https://www.pexels.com/vi-vn/tim-kiem/4k/",
            "https://www.pexels.com/vi-vn/tim-kiem/vũ%20trụ/",
    };
    private static final String[] URL_VIDEO = {
            "https://www.pexels.com/vi-vn/tim-kiem/videos/biển/"
    };

    private int i_asyc;
    private static String TAG = "TAG";
    private Toolbar v2;

    private RelativeLayout v4;
    private RelativeLayout v5;
    private RelativeLayout v6;
    private RelativeLayout v7;
    private RelativeLayout v9;
    private RelativeLayout v8;

    private ProgressBar pr1, pr2, pr3, pr4, pr5, pr6, pro_v2;
    public static final int KEY_DATA_RESULT = 100;
    private MorphBottomNavigationView bottomBar;
    private MyAdapterVideo myAdapter;
    private LinearLayoutManager linearLayoutManager_video;
    private Animation animation, animation1, animation2,animation3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v2 = findViewById(R.id.v2);
        setSupportActionBar(v2);

        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, KEY_DATA_RESULT);
            }
        });


        for (i_asyc = 0; i_asyc < 6; i_asyc++) {
            new DownloadAsynTaskIMG(this, i_asyc).execute(URL[i_asyc]);
        }


        animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animation1 = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        animation3 = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView_v1 = findViewById(R.id.recyclerview_v1);
        recyclerView_v2 = findViewById(R.id.recyclerview_v2);
        recyclerView_v3 = findViewById(R.id.recyclerview_v3);
        recyclerView_v4 = findViewById(R.id.recyclerView_v4);
        recyclerView_v5 = findViewById(R.id.recyclerView_v5);
        aah_customRecyclerView = findViewById(R.id.aah_customRecyclerView);
        scrollView = findViewById(R.id.nes);
        v4 = (RelativeLayout) findViewById(R.id.v4);
        v5 = (RelativeLayout) findViewById(R.id.v5);
        v6 = (RelativeLayout) findViewById(R.id.v6);
        v7 = (RelativeLayout) findViewById(R.id.v7);
        v9 = (RelativeLayout) findViewById(R.id.v9);
        v8 = (RelativeLayout) findViewById(R.id.v8);
        v4.setOnClickListener(this);
        v5.setOnClickListener(this);
        v6.setOnClickListener(this);
        v7.setOnClickListener(this);
        v8.setOnClickListener(this);
        v9.setOnClickListener(this);
        pr1 = findViewById(R.id.pr1);
        pr2 = findViewById(R.id.pr2);
        pr3 = findViewById(R.id.pr3);
        pr4 = findViewById(R.id.pr4);
        pr5 = findViewById(R.id.pr5);
        pr6 = findViewById(R.id.pr6);
        pro_v2 = findViewById(R.id.pro_v2);
        bottomBar = findViewById(R.id.bottomNavigationView);


        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedIndex = menuItem.getItemId();
                if (selectedIndex == R.id.album) {

                    scrollView.startAnimation(animation);
                    aah_customRecyclerView.startAnimation(animation1);
                    scrollView.setVisibility(View.VISIBLE);
                    aah_customRecyclerView.setVisibility(View.GONE);
                    pro_v2.setVisibility(View.GONE);
                }
                if (selectedIndex == R.id.video) {
                    if (myAdapter.getItems() == null) {
                        pro_v2.setVisibility(View.VISIBLE);
                        myAdapter.setClick(new MyAdapterVideo.Click() {
                            @Override
                            public void clickItems(IMGGIF img, int position) {
                                Intent intent = new Intent(MainActivity.this, DetailVideoActivity.class);
                                intent.putExtra(DetailVideoActivity.DATA_VIDEO, img);
                                startActivity(intent);
                            }
                        });
                        new DownloadAsyncTaskIMGGIF(MainActivity.this).execute(URL_VIDEO[0]);
                    }
                    scrollView.startAnimation(animation2);
                    aah_customRecyclerView.startAnimation(animation3);
                    scrollView.setVisibility(View.GONE);
                    aah_customRecyclerView.setVisibility(View.VISIBLE);
                }
                if (selectedIndex == R.id.user) {

                }
                return true;
            }
        });


        linearLayoutManager();
        adapterrecyclerview();
        setRecyclerviewAdapter_video();
        add();
        showHideWhenScroll();

    }

    private void linearLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager_v1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager_v2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager_v3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager_v4 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager_v5 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
    }

    private void adapterrecyclerview() {
        recyclerviewAdapter = new RecyclerviewAdapter(this, this);
        recyclerviewAdapter_v1 = new RecyclerviewAdapter(this, this);
        recyclerviewAdapter_v2 = new RecyclerviewAdapter(this, this);
        recyclerviewAdapter_v3 = new RecyclerviewAdapter(this, this);
        recyclerviewAdapter_v4 = new RecyclerviewAdapter(this, this);
        recyclerviewAdapter_v5 = new RecyclerviewAdapter(this, this);
    }

    private void setRecyclerviewAdapter_video() {
        linearLayoutManager_video = new GridLayoutManager(this, 2);
        aah_customRecyclerView.setLayoutManager(linearLayoutManager_video);
        myAdapter = new MyAdapterVideo(getwidth());
        aah_customRecyclerView.setHasFixedSize(true);
        aah_customRecyclerView.setAdapter(myAdapter);
    }

    private int getwidth() {
        DisplayMetrics metrics = new DisplayMetrics();   //for all android versions
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    private void showHideWhenScroll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {


                    }
                    if (scrollY < oldScrollY) {


                    }

                    if (scrollY == 0) {
                        Log.i(TAG, "TOP SCROLL");
                    }

                    if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {

                    }
                }
            });
        } else {

        }
    }


    private void add() {
        addrecyclerview(recyclerView, recyclerviewAdapter, linearLayoutManager);
        addrecyclerview(recyclerView_v1, recyclerviewAdapter_v1, linearLayoutManager_v1);
        addrecyclerview(recyclerView_v2, recyclerviewAdapter_v2, linearLayoutManager_v2);
        addrecyclerview(recyclerView_v3, recyclerviewAdapter_v3, linearLayoutManager_v3);
        addrecyclerview(recyclerView_v4, recyclerviewAdapter_v4, linearLayoutManager_v4);
        addrecyclerview(recyclerView_v5, recyclerviewAdapter_v5, linearLayoutManager_v5);
    }

    private void addrecyclerview(RecyclerView recyclerView, RecyclerviewAdapter recyclerviewAdapter, LinearLayoutManager linearLayoutManager) {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerviewAdapter);
    }


    private int getPixels(int sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }


    @Override
    public void onSussce(List<IMG> imgs, int type) {

        if (type == 0) {
            setVisibilityGone(pr1);
            recyclerviewAdapter.setImgs(imgs);
        }
        if (type == 1) {
            setVisibilityGone(pr2);
            setAdaterIMGs(recyclerviewAdapter_v1, imgs);
        }
        if (type == 2) {
            setVisibilityGone(pr3);
            setAdaterIMGs(recyclerviewAdapter_v2, imgs);
        }
        if (type == 3) {
            setVisibilityGone(pr4);
            setAdaterIMGs(recyclerviewAdapter_v3, imgs);
        }
        if (type == 4) {
            setVisibilityGone(pr5);
            setAdaterIMGs(recyclerviewAdapter_v4, imgs);
        }
        if (type == 5) {
            setVisibilityGone(pr6);
            setAdaterIMGs(recyclerviewAdapter_v5, imgs);
        }


    }

    @Override
    public void onSussceSearch(List<IMGSearch> imgSearches) {

    }

    private void setVisibilityGone(View view) {
        view.setVisibility(View.GONE);
    }

    private void setAdaterIMGs(RecyclerviewAdapter adaterIMGs, List<IMG> imgs) {
        adaterIMGs.setImgs(imgs);
    }

    @Override
    public void onFaied(int type) {
        new DownloadAsynTaskIMG(this, type).execute(URL[type]);
    }

    @Override
    public void clickITEM(IMG img, ImageView imgs) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.DATA, img);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v4:
                startActivity(URL[0], recyclerviewAdapter.getImgs());
                break;
            case R.id.v5:
                startActivity(URL[1], recyclerviewAdapter_v1.getImgs());
                break;
            case R.id.v6:
                startActivity(URL[2], recyclerviewAdapter_v2.getImgs());
                break;
            case R.id.v7:
                startActivity(URL[3], recyclerviewAdapter_v3.getImgs());
                break;
            case R.id.v8:
                startActivity(URL[4], recyclerviewAdapter_v4.getImgs());
                break;
            case R.id.v9:
                startActivity(URL[5], recyclerviewAdapter_v5.getImgs());
                break;
        }
    }

    private void startActivity(String url, List<IMG> imgs) {
        Intent intent = new Intent(this, IMGActivity.class);
        intent.putExtra(IMGActivity.DATA, (Serializable) imgs);
        intent.putExtra(IMGActivity.URL, url);
        startActivity(intent);
    }

    @Override
    public void Sussce(List<IMGGIF> imggifs) {
        pro_v2.setVisibility(View.GONE);
        myAdapter.setItems(imggifs);
    }

    @Override
    public void Falied() {

    }
}
