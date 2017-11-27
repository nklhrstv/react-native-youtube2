package com.stellarscript.rctyoutubevideo;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

final class RCTYoutubeVideoViewManager extends SimpleViewManager<RCTYouTubeVideoView> {

    private static final String REACT_CLASS = RCTYouTubeVideoView.class.getSimpleName();
    private static final String REACT_REGISTRATION_NAME = "registrationName";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        final Map<String, Integer> commands = MapBuilder.newHashMap();

        commands.put(RCTYoutubeVideoProps.PLAY_COMMAND_NAME, RCTYoutubeVideoProps.PLAY_COMMAND_ID);
        commands.put(RCTYoutubeVideoProps.PAUSE_COMMAND_NAME, RCTYoutubeVideoProps.PAUSE_COMMAND_ID);
        commands.put(RCTYoutubeVideoProps.SEEK_COMMAND_NAME, RCTYoutubeVideoProps.SEEK_COMMAND_ID);

        return commands;
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        final Map<String, Object> events = MapBuilder.newHashMap();

        events.put(RCTYoutubeVideoEvents.ON_BUFFERING_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_BUFFERING_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_PLAYING_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_PLAYING_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_PAUSED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_PAUSED_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_END_REACHED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_END_REACHED_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_ERROR_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_ERROR_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_TIME_CHANGED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_TIME_CHANGED_EVENT));
        events.put(RCTYoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT, MapBuilder.of(REACT_REGISTRATION_NAME, RCTYoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT));

        return events;
    }

    @Override
    public Map<String, Object> getExportedViewConstants() {
        final Map<String, Object> constants = MapBuilder.newHashMap();

        constants.put("ON_BUFFERING", RCTYoutubeVideoEvents.ON_BUFFERING_EVENT);
        constants.put("ON_PLAYING", RCTYoutubeVideoEvents.ON_PLAYING_EVENT);
        constants.put("ON_PAUSED", RCTYoutubeVideoEvents.ON_PAUSED_EVENT);
        constants.put("ON_END_REACHED", RCTYoutubeVideoEvents.ON_END_REACHED_EVENT);
        constants.put("ON_ERROR", RCTYoutubeVideoEvents.ON_ERROR_EVENT);
        constants.put("ON_TIME_CHANGED", RCTYoutubeVideoEvents.ON_TIME_CHANGED_EVENT);
        constants.put("ON_SEEK_PERFORMED", RCTYoutubeVideoEvents.ON_SEEK_PERFORMED_EVENT);

        return constants;
    }

    @Override
    public void receiveCommand(final RCTYouTubeVideoView videoView, final int commandId, final ReadableArray args) {
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
                    final int seekTime = (int) args.getDouble(RCTYoutubeVideoProps.SEEK_COMMAND_TIME_ARGUMENT_INDEX);
                    videoView.seek(seekTime);
                }
                break;
        }
    }

    @Override
    protected RCTYouTubeVideoView createViewInstance(final ThemedReactContext themedReactContext) {
        return new RCTYouTubeVideoView(themedReactContext);
    }

    @ReactProp(name = RCTYoutubeVideoProps.MEDIA_PROP)
    public void loadMedia(final RCTYouTubeVideoView videoView, final ReadableMap media) {
        if (media == null ||
                !media.hasKey(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) ||
                media.isNull(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) ||
                media.getType(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP) != ReadableType.String) {
            return;
        }

        final String sourceUrl = media.getString(RCTYoutubeVideoProps.MEDIA_SOURCE_URL_PROP);

        final int startTime;
        if (media.hasKey(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) &&
                !media.isNull(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) &&
                media.getType(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP) == ReadableType.Number) {
            startTime = (int) media.getDouble(RCTYoutubeVideoProps.MEDIA_START_TIME_PROP);
        } else {
            startTime = RCTYoutubeVideoProps.MEDIA_START_TIME_DEFAULT_VALUE;
        }

        final boolean autoplay;
        if (media.hasKey(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) &&
                !media.isNull(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) &&
                media.getType(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP) == ReadableType.Boolean) {
            autoplay = media.getBoolean(RCTYoutubeVideoProps.MEDIA_AUTOPLAY_PROP);
        } else {
            autoplay = RCTYoutubeVideoProps.MEDIA_AUTOPLAY_DEFAULT_VALUE;
        }

        videoView.loadMedia(sourceUrl, startTime, autoplay);
    }

}
