package com.changsune.changsu.yeontalk.screens.common.playermanager;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerManager {

    private long contentPosition;
    private final Factory dataSourceFactory;
    private String mVideoUri;
    public SimpleExoPlayer player;

    public PlayerManager(Context context) {

        this.dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "연톡"));
    }

    public PlayerManager(Context context, String videoUri) {

        this.dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "연톡"));
        this.mVideoUri = videoUri;
    }

    public void init(Context context, PlayerView playerView) {

        this.player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        playerView.setPlayer(this.player);
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(this.dataSourceFactory).createMediaSource(Uri.parse(this.mVideoUri));
        this.player.seekTo(this.contentPosition);
        this.player.prepare(mediaSource);
        this.player.setPlayWhenReady(true);
    }

    public void reset() {

        if (this.player != null) {
            this.contentPosition = this.player.getContentPosition();
            this.player.release();
            this.player = null;
        }
    }

    public void release() {

        if (this.player != null) {
            this.player.release();
            this.player = null;
        }
    }
}
