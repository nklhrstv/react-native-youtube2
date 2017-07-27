package com.stellarscript.rctyoutubevideo;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.stellarscript.youtubevideo.YouTubeEventListener;
import com.stellarscript.youtubevideo.YouTubeVideoView;

final class RCTYouTubeVideoView extends FrameLayout {

    private final YouTubeVideoView mVideoView;
    private final RCTEventEmitter mEventEmitter;
    private final YouTubeEventListener mEventListener = new YouTubeEventListener() {

        @Override
        public void onError(@NonNull final String message) {
            final WritableMap event = Arguments.createMap();
            event.putString(YoutubeVideoEvents.ON_ERROR_MESSAGE_PROP, message);
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_ERROR_EVENT, event);
        }

        @Override
        public void onTimeChanged(final int time) {
            final WritableMap event = Arguments.createMap();
            event.putInt(YoutubeVideoEvents.ON_TIME_CHANGED_TIME_PROP, time);
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_TIME_CHANGED_EVENT, event);
        }

        @Override
        public void onEndReached() {
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_END_REACHED_EVENT, null);
        }

        @Override
        public void onPlaying(final int duration) {
            final WritableMap event = Arguments.createMap();
            event.putInt(YoutubeVideoEvents.ON_PLAYING_DURATION_PROP, duration);
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_PLAYING_EVENT, event);
        }

        @Override
        public void onPaused() {
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_PAUSED_EVENT, null);
        }

        @Override
        public void onSeekPerformed() {
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT, null);
        }

        @Override
        public void onBuffering(final int buffering) {
            final WritableMap event = Arguments.createMap();
            event.putInt(YoutubeVideoEvents.ON_BUFFERING_BUFFERING_PROP, buffering);
            mEventEmitter.receiveEvent(RCTYouTubeVideoView.this.getId(), YoutubeVideoEvents.ON_BUFFERING_EVENT, event);
        }

        @Override
        public void onReady() {
        }

    };

    public RCTYouTubeVideoView(@NonNull final ThemedReactContext themedReactContext) {
        super(themedReactContext);

        mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);

        mVideoView = new YouTubeVideoView(themedReactContext, mEventListener);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mVideoView.setLayoutParams(layoutParams);
        addView(mVideoView);
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            layout(left, top, right, bottom);
        }
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

    public void loadMedia(@NonNull final String videoId, final int startTime, final boolean autoplay) {
        mVideoView.loadMedia(videoId, startTime, autoplay);
    }

}
