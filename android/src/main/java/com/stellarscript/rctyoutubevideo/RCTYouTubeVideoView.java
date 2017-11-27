package com.stellarscript.rctyoutubevideo;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.stellarscript.youtubevideo.YouTubeVideoView;

final class RCTYouTubeVideoView extends FrameLayout {

    private final YouTubeVideoView mVideoView;

    public RCTYouTubeVideoView(final ThemedReactContext themedReactContext) {
        super(themedReactContext);

        final RCTYoutubeVideoEventEmitter eventEmitter = new RCTYoutubeVideoEventEmitter(RCTYouTubeVideoView.this, themedReactContext);
        mVideoView = new YouTubeVideoView(themedReactContext, eventEmitter);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mVideoView, layoutParams);
    }

    public void play() {
        mVideoView.play();
    }

    public void pause() {
        mVideoView.pause();
    }

    public void seek(final int time) {
        mVideoView.seek(time);
    }

    public void loadMedia(final String videoId, final int startTime, final boolean autoplay) {
        mVideoView.loadMedia(videoId, startTime, autoplay);
    }

}
