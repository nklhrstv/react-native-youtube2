package com.stellarscript.rctyoutubevideo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

final class YoutubeVideoViewManager extends SimpleViewManager<RCTYouTubeVideoView> {

    private static final String REACT_CLASS = RCTYouTubeVideoView.class.getSimpleName();
    private static final String REACT_REGISTRATION_NAME = "registrationName";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    @Nullable
    public Map<String, Integer> getCommandsMap() {
        final Map<String, Integer> commands = MapBuilder.newHashMap();

        commands.put(RCTYoutubeVideoProps.PLAY_COMMAND_NAME, RCTYoutubeVideoProps.PLAY_COMMAND_ID);
        commands.put(RCTYoutubeVideoProps.PAUSE_COMMAND_NAME, RCTYoutubeVideoProps.PAUSE_COMMAND_ID);
        commands.put(RCTYoutubeVideoProps.SEEK_COMMAND_NAME, RCTYoutubeVideoProps.SEEK_COMMAND_ID);

        return commands;
    }

    @Override
    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        final Map<String, Object> events = MapBuilder.newHashMap();

        events.put(YoutubeVideoEvents.ON_BUFFERING_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_BUFFERING_EVENT));
        events.put(YoutubeVideoEvents.ON_PLAYING_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_PLAYING_EVENT));
        events.put(YoutubeVideoEvents.ON_PAUSED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_PAUSED_EVENT));
        events.put(YoutubeVideoEvents.ON_END_REACHED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_END_REACHED_EVENT));
        events.put(YoutubeVideoEvents.ON_ERROR_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_ERROR_EVENT));
        events.put(YoutubeVideoEvents.ON_TIME_CHANGED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_TIME_CHANGED_EVENT));
        events.put(YoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, YoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT));

        return events;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedViewConstants() {
        final Map<String, Object> constants = MapBuilder.newHashMap();

        constants.put("ON_BUFFERING", YoutubeVideoEvents.ON_BUFFERING_EVENT);
        constants.put("ON_PLAYING", YoutubeVideoEvents.ON_PLAYING_EVENT);
        constants.put("ON_PAUSED", YoutubeVideoEvents.ON_PAUSED_EVENT);
        constants.put("ON_END_REACHED", YoutubeVideoEvents.ON_END_REACHED_EVENT);
        constants.put("ON_ERROR", YoutubeVideoEvents.ON_ERROR_EVENT);
        constants.put("ON_TIME_CHANGED", YoutubeVideoEvents.ON_TIME_CHANGED_EVENT);
        constants.put("ON_SEEK_PERFORMED", YoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT);

        return constants;
    }

    @Override
    public void receiveCommand(@NonNull final RCTYouTubeVideoView videoView, final int commandId, @Nullable final ReadableArray args) {
        switch (commandId) {
            case RCTYoutubeVideoProps.PLAY_COMMAND_ID:
                videoView.play();
                break;
            case RCTYoutubeVideoProps.PAUSE_COMMAND_ID:
                videoView.pause();
                break;
            case RCTYoutubeVideoProps.SEEK_COMMAND_ID:
                if (args != null &&
                        args.size() > 0 &&
                        !args.isNull(RCTYoutubeVideoProps.SEEK_COMMAND_TIME_ARGUMENT_INDEX) &&
                        args.getType(RCTYoutubeVideoProps.SEEK_COMMAND_TIME_ARGUMENT_INDEX) == ReadableType.Number) {
                    final int seekTime = args.getInt(RCTYoutubeVideoProps.SEEK_COMMAND_TIME_ARGUMENT_INDEX);
                    videoView.seek(seekTime);
                }
                break;
        }
    }

    @Override
    protected RCTYouTubeVideoView createViewInstance(@NonNull final ThemedReactContext themedReactContext) {
        return new RCTYouTubeVideoView(themedReactContext);
    }

    @ReactProp(name = RCTYoutubeVideoProps.MEDIA_PROP)
    public void loadMedia(@NonNull final RCTYouTubeVideoView videoView, @Nullable final ReadableMap media) {
        if (media == null ||
                !media.hasKey(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) ||
                media.isNull(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) ||
                media.getType(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) != ReadableType.String) {
            return;
        }

        final String sourceUrl = media.getString(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP);

        int startTime = RCTYoutubeVideoProps.MEDIA_START_TIME_DEFAULT_VALUE;
        if (media.hasKey(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) &&
                !media.isNull(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) &&
                media.getType(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) == ReadableType.Number) {
            startTime = media.getInt(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP);
        }

        boolean autoplay = RCTYoutubeVideoProps.MEDIA_AUTOPLAY_DEFAULT_VALUE;
        if (media.hasKey(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) &&
                !media.isNull(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) &&
                media.getType(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) == ReadableType.Boolean) {
            autoplay = media.getBoolean(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP);
        }

        videoView.loadMedia(sourceUrl, startTime, autoplay);
    }

}
