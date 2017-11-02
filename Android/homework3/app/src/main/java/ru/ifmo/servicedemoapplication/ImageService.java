package ru.ifmo.servicedemoapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.ifmo.servicedemoapplication.utils.FileUtils;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Intent.ACTION_WALLPAPER_CHANGED;
import static android.graphics.BitmapFactory.decodeStream;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_ERROR;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_FINISH_LOADING;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_PARAM;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_PUBLISH_PROGRESS;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_START_LOADING;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.BROADCAST_ACTION;
import static ru.ifmo.servicedemoapplication.api.AlphaCodersApi.downloadImage;
import static ru.ifmo.servicedemoapplication.api.AlphaCodersApi.readImageURL;

/**
 * Created by Dima Stoyanov on 23.11.2016.
 * Project ServiceDemoApplication
 * Start time : 2:16
 */

public class ImageService extends Service {

    private static final String TAG = ImageService.class.getSimpleName();
    public static final String ADD_GALLERY_KEY = "add_gallery";
    public static final String FILE_PATH_KEY = "img_path";
    public static final String PROGRESS_MESSAGE_KEY = "progress_msg";
    public static final String PROGRESS_KEY = "progress";
    public static final String ERROR_MESSAGE_KEY = "error_msg";

    private BroadcastReceiver receiver;
    private PendingIntent pendingIntent;
    private AsyncTask<Void, Void, Void> imageTask;
    private static long time;
    private boolean isLoading = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Start service");
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.d(TAG, "Receive message");
                if (isLoading)
                    return;
                isLoading = true;
                final Notification.Builder builder = new Notification.Builder(getApplicationContext());
                final Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                resultIntent.setAction(Intent.ACTION_MAIN);
                resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                resultIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_START_LOADING);
                pendingIntent = PendingIntent
                        .getActivity(getApplicationContext(), 0,
                                resultIntent, FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentText("Start downloading")
                        .setContentTitle("Image service")
                        .setSmallIcon(android.R.drawable.stat_sys_download)
                        .setProgress(100, 0, true);
                manager.notify(0, builder.build());

                imageTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            final Intent activityIntent = new Intent(BROADCAST_ACTION);
                            // if activity stop thread, service don't send new broadcasts, but it finish current task
                            // because i don't know how to stop thread fully without bugs
                            // (I didn't want to check isInterrupted() while downloading image)
                            if (imageTask.isCancelled())
                                return null;
                            sendBroadcast(activityIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_START_LOADING));
                            Pair<String, String> apiResult = readImageURL();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 6;
                            Bitmap bitmap = decodeStream(new FileInputStream(downloadImage(apiResult.second, null)));
                            builder.setStyle(new Notification.BigPictureStyle().bigPicture(bitmap));
                            resultIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_PUBLISH_PROGRESS);
                            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, FLAG_UPDATE_CURRENT);
                            builder.setProgress(100, 0, false);
                            if (imageTask.isCancelled())
                                return null;
                            manager.notify(0, builder.build());

                            String image_path = downloadImage(apiResult.first, new FileUtils.ProgressCallback() {
                                @Override
                                public void onProgressChanged(int progress, String message) {
                                    if (!imageTask.isCancelled()) {
                                        sendBroadcast(activityIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_PUBLISH_PROGRESS)
                                                .putExtra(PROGRESS_MESSAGE_KEY, message).putExtra(PROGRESS_KEY, progress));
                                        builder.setProgress(100, progress, false).setContentText(message);
                                        if (System.currentTimeMillis() - time > 500) {
                                            builder.setContentTitle(message);
                                            time = System.currentTimeMillis();
                                        }
                                        manager.notify(0, builder.build());

                                    }
                                }

                            });
                            if (imageTask.isCancelled())
                                return null;
                            sendBroadcast(activityIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_FINISH_LOADING).putExtra(FILE_PATH_KEY, image_path));
                            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                    resultIntent.putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_FINISH_LOADING)
                                            .putExtra(FILE_PATH_KEY, image_path), FLAG_UPDATE_CURRENT);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                                    .setSubText("Download finished")
                                    .setContentTitle("Image service")
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                                    .setPriority(Notification.PRIORITY_MAX);
                            manager.notify(0, builder.build());

                        } catch (IOException e) {
                            sendBroadcast(new Intent(BROADCAST_ACTION)
                                    .putExtra(ACTION_TYPE_PARAM, ACTION_TYPE_ERROR).putExtra(ERROR_MESSAGE_KEY, e.getMessage()));
                            NotificationCompat.Builder errorNotif = new NotificationCompat.Builder(getApplicationContext())
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(android.R.drawable.stat_notify_error)
                                    .setTicker("Downloading error")
                                    .setContentTitle("Image service")
                                    .setContentText("Downloading error");
                            manager.notify(0, errorNotif.build());
                            e.printStackTrace();
                        } finally {
                            isLoading = false;
                        }
                        return null;
                    }

                };
                imageTask.executeOnExecutor(Executors.newSingleThreadExecutor());


            }
        };

        registerReceiver(receiver, new IntentFilter(ACTION_WALLPAPER_CHANGED));
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Start command");
        return START_REDELIVER_INTENT;
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "On destroy service");
        if (imageTask != null) imageTask.cancel(true);
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
