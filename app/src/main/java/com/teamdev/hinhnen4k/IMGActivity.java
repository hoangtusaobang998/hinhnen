package com.teamdev.hinhnen4k;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teamdev.hinhnen4k.adapter.MyAdapter;
import com.teamdev.hinhnen4k.listen.ILoadMore;
import com.teamdev.hinhnen4k.listen.Listen;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGSearch;
import com.teamdev.hinhnen4k.task.DownloadAsynTaskIMG;

import java.util.List;

public class IMGActivity extends AppCompatActivity implements Listen {
    private RecyclerView recyclerView;
    private ProgressBar loadingview;
    private RelativeLayout layout4;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<IMG> list = null;
    int i = 2;
    public static final String DATA = "DATA";
    public static final String URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        recyclerView = findViewById(R.id.recyclerview);
        loadingview = findViewById(R.id.loadingview);
        layout4 = findViewById(R.id.layout4);
        list = (List<IMG>) getIntent().getSerializableExtra(DATA);

        if (list == null) {
            Toast.makeText(this, getString(R.string.errnetwork), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(recyclerView, this, list, this, getwidth());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                loadingview.setVisibility(View.VISIBLE);
                String url = getIntent().getStringExtra(URL) + "?page=" + i + "&seed=&type=";
                new DownloadAsynTaskIMG(IMGActivity.this, 0).execute(url);
                i++;
                Log.e("I", i + "");
            }
        });
        myAdapter.setClick(new MyAdapter.Click() {
            @Override
            public void clickItems(IMG img, int position, ImageView imgs) {
                Intent intent = new Intent(IMGActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.DATA, img);
                startActivity(intent);
            }
        });
    }

    private int getwidth() {
        DisplayMetrics metrics = new DisplayMetrics();   //for all android versions
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    public void onSussce(List<IMG> imgs, int type) {
        loadingview.setVisibility(View.GONE);
        list.addAll(imgs);
        myAdapter.notifyDataSetChanged();
        myAdapter.setLoaded();
    }

    @Override
    public void onSussceSearch(List<IMGSearch> imgSearches) {

    }

    @Override
    public void onFaied(int type) {
        Toast.makeText(this, getString(R.string.errnetwork), Toast.LENGTH_SHORT).show();
    }
}
