package com.stellarscript.rctyoutubevideo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.stellarscript.youtubevideo.YouTubeEventListener;

final class RCTYoutubeVideoEventEmitter implements YouTubeEventListener {

    private final RCTYouTubeVideoView mVideoView;
    private final RCTEventEmitter mEventEmitter;

    RCTYoutubeVideoEventEmitter(final RCTYouTubeVideoView videoView, final ThemedReactContext themedReactContext) {
        mVideoView = videoView;
        mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);
    }

    @Override
    public void onError(final String message) {
        final WritableMap event = Arguments.createMap();
        event.putString(RCTYoutubeVideoEvents.ON_ERROR_MESSAGE_PROP, message);
        event.putBoolean(RCTYoutubeVideoEvents.ON_ERROR_IS_CRITICAL_PROP, true);
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_ERROR_EVENT, event);
    }

    @Override
    public void onTimeChanged(final int time) {
        final WritableMap event = Arguments.createMap();
        event.putInt(RCTYoutubeVideoEvents.ON_TIME_CHANGED_TIME_PROP, time);
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_TIME_CHANGED_EVENT, event);
    }

    @Override
    public void onEndReached() {
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_END_REACHED_EVENT, null);
    }

    @Override
    public void onPlaying(final int duration) {
        final WritableMap event = Arguments.createMap();
        event.putInt(RCTYoutubeVideoEvents.ON_PLAYING_DURATION_PROP, duration);
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_PLAYING_EVENT, event);
    }

    @Override
    public void onPaused() {
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_PAUSED_EVENT, null);
    }

    @Override
    public void onSeekPerformed() {
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT, null);
    }

    @Override
    public void onSeekRequested(final int time) {
        final WritableMap event = Arguments.createMap();
        event.putInt(RCTYoutubeVideoEvents.ON_SEEK_REQUESTED_TIME_PROP, time);
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_SEEK_REQUESTED_EVENT, event);
    }

    @Override
    public void onBuffering(final int buffering) {
        final WritableMap event = Arguments.createMap();
        event.putInt(RCTYoutubeVideoEvents.ON_BUFFERING_BUFFERING_PROP, buffering);
        mEventEmitter.receiveEvent(mVideoView.getId(), RCTYoutubeVideoEvents.ON_BUFFERING_EVENT, event);
    }

    @Override
    public void onReady() {
    }

}
