package com.changsune.changsu.yeontalk.screens.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.changsune.changsu.yeontalk.screens.common.playermanager.PlayerManager;
import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.common.playermanager.PlayerManager;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoActivity extends AppCompatActivity {

    private static final String EXTRA_VIDEO_NUM = "EXTRA_VIDEO_NUM";

    private String mVideoUri;
    private PlayerManager player;
    private PlayerView playerView;

    public static void start(Context context, String videoUri) {

        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_NUM, videoUri);
        context.startActivity(intent);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoUri = getIntent().getExtras().getString(EXTRA_VIDEO_NUM);
        playerView = (PlayerView) findViewById(R.id.player_view);
        player = new PlayerManager((Context) this, this.mVideoUri);
    }

    public void onResume() {
        super.onResume();
        this.player.init(this, this.playerView);
    }

    public void onPause() {
        super.onPause();
        this.player.reset();
    }

    public void onDestroy() {
        this.player.release();
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
