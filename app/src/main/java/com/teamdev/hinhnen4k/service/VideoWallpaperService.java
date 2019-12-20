package com.teamdev.hinhnen4k.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.core.content.FileProvider;

import com.teamdev.hinhnen4k.R;

import java.io.File;

public class VideoWallpaperService extends WallpaperService {
    protected static int playheadTime = 0;
    private SharedPreferences sharedPreferences;
    private static final String ACTION_PREFIX = VideoWallpaperService.class.getName()+".";
    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }


    class VideoEngine extends Engine {

        private final String TAG = getClass().getSimpleName();
        private final MediaPlayer mediaPlayer;

        private  void sendAction(Context context, String action)
        {
            Intent intent = new Intent();
            intent.setAction(VideoWallpaperService.ACTION_PREFIX + action);
            context.sendBroadcast(intent);
        }
        public VideoEngine() {
            super();
            Log.i(TAG, "( VideoEngine )");
            sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
            File file = new File(sharedPreferences.getString("URL", ""));
            Uri fileURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    getApplicationContext()
                            .getPackageName() + ".provider", file);
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.video);

        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "onSurfaceCreated");
            mediaPlayer.setSurface(holder.getSurface());
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "( INativeWallpaperEngine ): onSurfaceDestroyed");
            playheadTime = mediaPlayer.getCurrentPosition();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
