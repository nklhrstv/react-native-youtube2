import React, { Component, PropTypes } from 'react';
import { View, UIManager, requireNativeComponent, findNodeHandle } from 'react-native';

const RCTYouTubeVideoViewConstants = UIManager.RCTYouTubeVideoView.Constants;

class YouTubeVideo extends Component {
    constructor(props) {
        super(props);

        this._assignRoot = this._assignRoot.bind(this);

        this.callbacks = {
            [RCTYouTubeVideoViewConstants.ON_BUFFERING]: this._invokeEventCallback.bind(this, 'onBuffering'),
            [RCTYouTubeVideoViewConstants.ON_PLAYING]: this._invokeEventCallback.bind(this, 'onPlaying'),
            [RCTYouTubeVideoViewConstants.ON_PAUSED]: this._invokeEventCallback.bind(this, 'onPaused'),
            [RCTYouTubeVideoViewConstants.ON_END_REACHED]: this._invokeEventCallback.bind(this, 'onEndReached'),
            [RCTYouTubeVideoViewConstants.ON_ERROR]: this._invokeEventCallback.bind(this, 'onError'),
            [RCTYouTubeVideoViewConstants.ON_TIME_CHANGED]: this._invokeEventCallback.bind(this, 'onTimeChanged'),
            [RCTYouTubeVideoViewConstants.ON_SEEK_PERFORMED]: this._invokeEventCallback.bind(this, 'onSeekPerformed')
        };
    }

    shouldComponentUpdate(nextProps, nextState) {
        return nextProps.sourceUrl !== this.props.sourceUrl ||
            nextProps.keyControlEnabled !== this.props.keyControlEnabled ||
            nextProps.style !== this.props.style;
    }

    render() {
        const media = {
            sourceUrl: this.props.sourceUrl,
            autoplay: this.props.autoplay,
            startTime: this.props.startTime
        };

        return (
            <RCTYouTubeVideoView
                ref={this._assignRoot}
                style={this.props.style}
                keyControlEnabled={this.props.keyControlEnabled}
                media={media}
                {...this.callbacks}
            />
        );
    }

    _assignRoot(root) {
        this._root = root;
    }

    _getViewHandle() {
        return findNodeHandle(this._root);
    }

    _invokeEventCallback(eventName, event) {
        if (typeof this.props[eventName] === 'function') {
            this.props[eventName](event.nativeEvent);
        }
    }

    seek(time) {
        if (typeof time !== 'number' || isNaN(time) || time < 0) {
            time = 0;
        }

        UIManager.dispatchViewManagerCommand(
            this._getViewHandle(),
            UIManager.RCTYouTubeVideoView.Commands.seek,
            [time]
        );
    }

    play() {
        UIManager.dispatchViewManagerCommand(
            this._getViewHandle(),
            UIManager.RCTYouTubeVideoView.Commands.play,
            null
        );
    }

    pause() {
        UIManager.dispatchViewManagerCommand(
            this._getViewHandle(),
            UIManager.RCTYouTubeVideoView.Commands.pause,
            null
        );
    }

}

YouTubeVideo.propTypes = {
    ...View.propTypes,
    sourceUrl: PropTypes.string.isRequired,
    autoplay: PropTypes.bool.isRequired,
    startTime: PropTypes.number.isRequired,
    keyControlEnabled: PropTypes.bool.isRequired,
    onBuffering: PropTypes.func,
    onPlaying: PropTypes.func,
    onPaused: PropTypes.func,
    onEndReached: PropTypes.func,
    onError: PropTypes.func,
    onTimeChanged: PropTypes.func,
    onSeekPerformed: PropTypes.func
};

YouTubeVideo.defaultProps = {
    autoplay: true,
    startTime: 0,
    keyControlEnabled: false
};

const RCTYouTubeVideoViewInterface = {
    name: 'YouTubeVideo',
    propTypes: {
        ...View.propTypes,
        media: PropTypes.object.isRequired,
        keyControlEnabled: PropTypes.bool.isRequired,
        [RCTYouTubeVideoViewConstants.ON_BUFFERING]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_PLAYING]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_PAUSED]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_END_REACHED]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_ERROR]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_TIME_CHANGED]: PropTypes.func,
        [RCTYouTubeVideoViewConstants.ON_SEEK_PERFORMED]: PropTypes.func
    }
};

const RCTYouTubeVideoView = requireNativeComponent('RCTYouTubeVideoView', RCTYouTubeVideoViewInterface, {
    nativeOnly: {
        media: true,
        keyControlEnabled: true
    }
});

export default YouTubeVideo;
