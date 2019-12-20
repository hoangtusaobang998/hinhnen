package com.teamdev.hinhnen4k;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.teamdev.hinhnen4k.listen.SaveIMG;

import com.teamdev.hinhnen4k.task.DownloadFileIMG;

import java.io.IOException;


public class DetailActivity extends AppCompatActivity implements SaveIMG {
    public static final String DATA = "DATA";
    public static final String IMG = "IMG";
    private LinearLayout layout1, layout2;
    private RelativeLayout layout3, layout4;
    private Animation animation, animation1, animation2;
    private com.teamdev.hinhnen4k.model.IMG img;
    private Dialog progressDialog;
    private Bitmap bitmap = null;
    private TextView txtloadding;
    private TextView title;
    private ImageView download, comment;
    private String[] P={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PERMISSION_REQUEST_CODE=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        img = (com.teamdev.hinhnen4k.model.IMG) extras.getSerializable(DATA);
        Log.e("SIZE_IMG", img.getLisurl().size() + "");
        final ImageView imageView = findViewById(R.id.img);
        final ProgressBar rf = findViewById(R.id.rf);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        download = findViewById(R.id.download);
        comment = findViewById(R.id.comment);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            comment.setImageDrawable(getDrawable(R.drawable.ic_chat_));
        } else {
            comment.setImageDrawable(getDrawable(R.drawable.ic_chat));
        }
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_a);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.fade_b);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.fade);
        progressDialog = customDialog();
        progressDialog.setCanceledOnTouchOutside(true);
        txtloadding = (TextView) progressDialog.findViewById(R.id.txtloadding);
        title = (TextView) progressDialog.findViewById(R.id.title);
        String imageUrl = img.getUrl();
        Picasso.get()
                .load(imageUrl).centerCrop().fit()
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        rf.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                        layout3.setVisibility(View.VISIBLE);
                        layout4.setVisibility(View.VISIBLE);
                        layout1.setAnimation(animation);
                        layout2.setAnimation(animation);
                        layout3.setAnimation(animation1);
                        layout4.setAnimation(animation2);
                        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    }

                    @Override
                    public void onError(Exception e) {

                    }


                });
        click(imageView);
    }

    private void click(final ImageView view) {
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Đang cài hình nền");
                if (bitmap != null) {
                    new SetWallpaperManager().execute(bitmap);
                } else {
                    Toast.makeText(DetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }

            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        customDialog1().show();
                        Log.e("URL", img.getUrl());
                    } else {
                        requestPermission();
                    }
                }else {

                }


            }
        });
    }

    private void dowload_img(int type) {

        title.setText("Đang tải về máy");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        String url = "";
        if (type == 0) {
            url = img.getLisurl().get(0);
        }
        if (type == 1) {
            url = img.getLisurl().get(1);
        }

        if (type == 2) {
            url = img.getLisurl().get(2);
        }
        if (type == 3) {
            url = img.getLisurl().get(3);
        }
        final AltexImageDownloader downloader = new AltexImageDownloader(new AltexImageDownloader.OnImageLoaderListener() {
            @Override
            public void onError(AltexImageDownloader.ImageError error) {

            }

            @Override
            public void onProgressChange(int percent) {
                txtloadding.setText(percent + "");
            }

            @Override
            public void onComplete(Bitmap result) {
                try {
                    DownloadFileIMG.savebitmap(result, DetailActivity.this, DetailActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        downloader.download(url, true);
    }

    @Override
    public void TC() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void TB() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        Toast.makeText(DetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
    }

    private class SetWallpaperManager extends AsyncTask<Bitmap, Integer, Void> {

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(getApplicationContext());
            for (int i = 0; i <= 99; i++) {
                SystemClock.sleep(10);
                publishProgress(i);
            }
            try {
                myWallpaperManager.setBitmap(bitmaps[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        Toast.makeText(DetailActivity.this, "Mạng kém - hình ảnh sẽ được cài sớm nhất", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            }, 10000);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("A", values[0] + "");
            txtloadding.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(DetailActivity.this, "Cài hình nền thành công", Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            moveTaskToBack(true);
        }
    }

    private Dialog customDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    private Dialog customDialog1() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogdownload);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        lp.y = getPixels(80);
        lp.x = getPixels(65);
        window.getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        window.setAttributes(lp);
        TextView text;
        TextView text1;
        TextView text2;
        TextView text3;

        text = (TextView) dialog.findViewById(R.id.text);
        text1 = (TextView) dialog.findViewById(R.id.text1);
        text2 = (TextView) dialog.findViewById(R.id.text2);
        text3 = (TextView) dialog.findViewById(R.id.text3);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowload_img(0);
                dialog.cancel();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowload_img(1);
                dialog.cancel();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowload_img(2);
                dialog.cancel();
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowload_img(3);
                dialog.cancel();
            }
        });


        return dialog;
    }

    private int getPixels(int sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

    private boolean checkPermission() {
        for (String p:P){
            int result = ContextCompat.checkSelfPermission(DetailActivity.this,p);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DetailActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DetailActivity.this, P, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    customDialog1().show();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                    requestPermission();
                }
                break;
        }
    }
}
