package ru.ifmo.servicedemoapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static ru.ifmo.servicedemoapplication.ImageService.ERROR_MESSAGE_KEY;
import static ru.ifmo.servicedemoapplication.ImageService.FILE_PATH_KEY;
import static ru.ifmo.servicedemoapplication.ImageService.PROGRESS_KEY;
import static ru.ifmo.servicedemoapplication.ImageService.PROGRESS_MESSAGE_KEY;

/**
 * Created by Dima Stoyanov on 23.11.2016.
 * Project ServiceDemoApplication
 * Start time : 2:51
 */

public abstract class ImageBroadcast extends BroadcastReceiver {

    public static final String BROADCAST_ACTION = "ru.ifmo/servicedemoapplication";
    public static final String ACTION_TYPE_PARAM = "type";

    public static final int ACTION_TYPE_START_LOADING = 0;
    public static final int ACTION_TYPE_FINISH_LOADING = 1;
    public static final int ACTION_TYPE_PUBLISH_PROGRESS = 2;
    public static final int ACTION_TYPE_ERROR = 3;

    private static String TAG = ImageBroadcast.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getExtras().getInt(ACTION_TYPE_PARAM, 0)) {
            case ACTION_TYPE_FINISH_LOADING:
                Log.d(TAG, "Broadcast finish loading received");
                onFinishLoading(intent.getStringExtra(FILE_PATH_KEY));
                break;
            case ACTION_TYPE_PUBLISH_PROGRESS:
                onPublishProgress(intent.getIntExtra(PROGRESS_KEY, 0), intent.getStringExtra(PROGRESS_MESSAGE_KEY));
                break;
            case ACTION_TYPE_START_LOADING:
                onStartLoading();
                break;
            case ACTION_TYPE_ERROR:
                onError(intent.getStringExtra(ERROR_MESSAGE_KEY));
        }
    }


    public abstract void onStartLoading();

    public abstract void onPublishProgress(int progress, String name);

    public abstract void onError(String message);

    public abstract void onFinishLoading(String path);
}


