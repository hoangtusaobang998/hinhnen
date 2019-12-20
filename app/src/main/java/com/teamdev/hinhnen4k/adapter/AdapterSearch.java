package com.teamdev.hinhnen4k.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.teamdev.hinhnen4k.R;
import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGSearch;

import java.util.List;

import static com.teamdev.hinhnen4k.adapter.RecyclerviewAdapter.boderIMG;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolderSearch> {


    private List<IMGSearch> imgSearches;
    private Context context;
    private Click click;

    public void setClick(Click click) {
        this.click = click;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<IMGSearch> getImgSearches() {
        return imgSearches;

    }

    public void setImgSearches(List<IMGSearch> imgSearches) {
        this.imgSearches = imgSearches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderSearch(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSearch holder, int position) {

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_a));

        holder.title.setText(imgSearches.get(position).getTitle());
        Picasso.get().load(imgSearches.get(position).getImg()).fit().transform(boderIMG(0, 50))
                .centerCrop().placeholder(R.drawable.shape_img).error(R.drawable.ic_launcher_background).into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
                //code loading
                }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (imgSearches==null){
            return 0;
        }else return imgSearches.size();
    }

    public class ViewHolderSearch extends RecyclerView.ViewHolder {
        private Toolbar v2;
        private ImageView img;
        private TextView title;


        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            v2 = (Toolbar) itemView.findViewById(R.id.v2);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()!=RecyclerView.NO_POSITION){
                        click.clickITEM(imgSearches.get(getAdapterPosition()),img);
                    }
                }
            });
        }
    }

    public interface Click{
        void clickITEM(IMGSearch img, ImageView view);
    }

}
