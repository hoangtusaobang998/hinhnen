package com.teamdev.hinhnen4k.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.teamdev.hinhnen4k.R;
import com.teamdev.hinhnen4k.model.IMG;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewholderIMG> {


    private List<IMG> imgs;

    public List<IMG> getImgs() {
        return imgs;
    }

    public void setImgs(List<IMG> imgs) {
        this.imgs = imgs;
        notifyDataSetChanged();
    }

    private Context context;

    public RecyclerviewAdapter(Context context, Click click) {
        this.context = context;
        this.click = click;
    }

    private View view;
    boolean isLoading =true;
    public static final int LOADING = 2;
    public static final int VIEW = 1;
    private Click click;

    @NonNull
    @Override
    public ViewholderIMG onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recyclerview,parent,false);

        return new ViewholderIMG(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderIMG holder, int position) {
        Log.e("URL",imgs.get(position).getUrl());
        holder.img.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade));
        Picasso.get().load(imgs.get(position).getUrl()).fit().transform(boderIMG(0, 5))
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

    }

    @Override
    public int getItemCount() {
       if (imgs==null){
           return 0;
       }else return imgs.size();
    }

    public class ViewholderIMG extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView img;
        private ProgressBar spinKit;


        public ViewholderIMG(@NonNull View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            img = (ImageView) itemView.findViewById(R.id.img);
            spinKit = (ProgressBar) itemView.findViewById(R.id.spin_kit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()!=RecyclerView.NO_POSITION){
                        click.clickITEM(imgs.get(getAdapterPosition()),img);
                    }
                }
            });

        }
    }

    public static Transformation boderIMG(int boderW, int boderConer) {
        return new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(boderW)
                .cornerRadiusDp(boderConer)
                .oval(false)
                .build();

    }
    @Override
    public int getItemViewType(int position) {
        if (isLoading){
            if (position == imgs.size() - 1)

                return LOADING;

            else return VIEW;
        }else
            return VIEW;
    }
    public interface Click{
        void clickITEM(IMG img,ImageView view);
    }
}
