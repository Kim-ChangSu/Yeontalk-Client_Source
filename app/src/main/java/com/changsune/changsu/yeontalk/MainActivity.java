package com.changsune.changsu.yeontalk;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.changsune.changsu.yeontalk.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private View spinnerContainer;
    private String out;
    private String in;
    private String watermark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button process = findViewById(R.id.btn_process);
        videoView = findViewById(R.id.vv_player);
        spinnerContainer = findViewById(R.id.v_spinner_container);

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClicked();
            }
        });

        out = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "out.mp4";
        in = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "in.mp4";
        watermark = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "watermark.png";

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1337);

        try {
            final InputStream videoIn = getAssets().open("video.mp4");
            final OutputStream videoOut = new FileOutputStream(new File(in));
            copyFile(videoIn, videoOut);

            final InputStream watermarkIn = getAssets().open("watermark.png");
            final OutputStream watermarkOut = new FileOutputStream(watermark);
            copyFile(watermarkIn, watermarkOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    @SuppressWarnings("unused")
    private void processClicked() {
        spinnerContainer.setVisibility(View.VISIBLE);



    }

    void finished() {
        videoView.setVideoPath(out);
        videoView.start();
        spinnerContainer.setVisibility(View.GONE);
    }
}
