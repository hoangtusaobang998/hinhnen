package com.teamdev.hinhnen4k.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.teamdev.hinhnen4k.R;
import com.teamdev.hinhnen4k.listen.ILoadMore;
import com.teamdev.hinhnen4k.model.IMG;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<IMG> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;
    private Context context;
    private int wid;
    private Click click;

    public void setClick(Click click) {
        this.click = click;
    }

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<IMG> items, Context context, int wid) {
        this.activity = activity;
        this.items = items;
        this.context=context;
        this.wid=wid;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null)
                        loadMore.onLoadMore();
                        isLoading = true;
                }

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_view_recyclerview, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            RelativeLayout relativeLayout=((ItemViewHolder) holder).layout.findViewById(R.id.layout);
            ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
            layoutParams.width = wid/2-12;
            layoutParams.height = (int) (wid/1.5);
            holder.itemView.setLayoutParams(layoutParams);
            ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
            marginLayoutParams.setMargins(5, 5, 5, 5);
              final IMG img=items.get(position);
           // ((ItemViewHolder) holder).img.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade));
            Picasso.get().load(img.getUrl()).fit().transform(boderIMG(0, 5))
                    .centerCrop().placeholder(R.drawable.shape_img).error(R.drawable.ic_launcher_background).into(((ItemViewHolder) holder).img, new Callback() {
                @Override
                public void onSuccess() {
                    //code loading
                    ((ItemViewHolder) holder).spinKit.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.clickItems(img,position,((ItemViewHolder) holder).img);
                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        } else return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }
    private Transformation boderIMG(int boderW, int boderConer) {
        return new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(boderW)
                .cornerRadiusDp(boderConer)
                .oval(false)
                .build();

    }

    public interface Click{
        void clickItems(IMG img,int position,ImageView view);
    }
}


class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.loadingview);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {


    public RelativeLayout layout;
    public ImageView img;
    public ProgressBar spinKit;
    public RelativeLayout video;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        img = (ImageView) itemView.findViewById(R.id.img);
        spinKit = (ProgressBar) itemView.findViewById(R.id.spin_kit);
        video = (RelativeLayout) itemView.findViewById(R.id.video);
    }

}




