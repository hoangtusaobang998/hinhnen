package com.teamdev.hinhnen4k.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.ixuea.android.downloader.domain.DownloadInfo;
import com.teamdev.hinhnen4k.listen.SaveIMG;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadFileIMG {



    public static File savebitmap(Bitmap bmp, Context context, SaveIMG saveIMG) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = null;

        try {
            f = new File(getFilename(context));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            saveIMG.TC();
        } catch (Exception e) {
            Log.e("null", e.toString() + "");
            saveIMG.TB();
            return null;
        }
        return f;
    }

    public static String getFilename(Context context) {
        String format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + context.getPackageName() + "/IMG";
        File storageDir = new File(file_path);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File file = File.createTempFile(
                    "img" + format,
                    ".jpg",
                    storageDir
            );
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
