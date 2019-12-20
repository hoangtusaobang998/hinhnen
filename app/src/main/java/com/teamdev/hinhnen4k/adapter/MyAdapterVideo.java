package com.teamdev.hinhnen4k.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.teamdev.hinhnen4k.model.IMGGIF;

import java.util.List;

public class MyAdapterVideo extends RecyclerView.Adapter<ItemViewHolder> {


    List<IMGGIF> items;
    private int wid;
    private Click click;


    public void setItems(List<IMGGIF> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<IMGGIF> getItems() {
        return items;
    }

    public MyAdapterVideo(int wid) {
        this.wid = wid;
    }

    public void setClick(Click click) {
        this.click = click;
    }


    @NonNull
    @Override
    public com.teamdev.hinhnen4k.adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_recyclerview, parent, false);
        return new com.teamdev.hinhnen4k.adapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.teamdev.hinhnen4k.adapter.ItemViewHolder holder, final int position) {

        RelativeLayout relativeLayout = holder.layout.findViewById(R.id.layout);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = wid / 2 - 12;
        layoutParams.height = (int) (wid / 1.5);
        holder.itemView.setLayoutParams(layoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
        marginLayoutParams.setMargins(5, 5, 5, 5);
        final IMGGIF img = items.get(position);
        holder.video.setVisibility(View.VISIBLE);
        Picasso.get().load(img.getImg_url()).fit().transform(boderIMG(0, 5))
                .centerCrop().placeholder(R.drawable.shape_img).error(R.drawable.ic_launcher_background).into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
                //code loading
                holder.spinKit.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   click.clickItems(img, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        } else return items.size();
    }


    private Transformation boderIMG(int boderW, int boderConer) {
        return new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(boderW)
                .cornerRadiusDp(boderConer)
                .oval(false)
                .build();

    }

    public interface Click {
        void clickItems(IMGGIF img, int position);
    }

}





