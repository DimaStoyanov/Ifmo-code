package ru.ifmo.servicedemoapplication;

import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executors;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import ru.ifmo.servicedemoapplication.utils.FileUtils;

import static android.os.Environment.getExternalStorageDirectory;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_FINISH_LOADING;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_PARAM;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_PUBLISH_PROGRESS;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.ACTION_TYPE_START_LOADING;
import static ru.ifmo.servicedemoapplication.ImageBroadcast.BROADCAST_ACTION;
import static ru.ifmo.servicedemoapplication.ImageService.FILE_PATH_KEY;
import static ru.ifmo.servicedemoapplication.utils.FileUtils.PACKAGE_NAME;
import static ru.ifmo.servicedemoapplication.utils.ServiceUtils.isServiceRunning;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Button actionService;
    private Intent serviceIntent;
    private ImageView imageView;
    private MaterialProgressBar progressBar, circularPB;
    private BroadcastReceiver receiver;
    private TextView statusTextView, progressText;
    private FloatingActionButton addToGallery, setWall, delWall;
    private Bitmap bitmap;
    private long time;
    private String lastPath;
    private boolean isButtonShowing;
    private int buttonsFlags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "On create");
        actionService = (Button) findViewById(R.id.dummy_button);
        imageView = (ImageView) findViewById(R.id.imageview);
        statusTextView = (TextView) findViewById(R.id.fullscreen_content);
        progressBar = (MaterialProgressBar) findViewById(R.id.progress_bar);
        progressText = (TextView) findViewById(R.id.progress_text);
        circularPB = (MaterialProgressBar) findViewById(R.id.circular_pb);
        addToGallery = (FloatingActionButton) findViewById(R.id.addToGallery);
        setWall = (FloatingActionButton) findViewById(R.id.setWall);
        delWall = (FloatingActionButton) findViewById(R.id.delWall);

        addToGallery.hide();
        setWall.hide();
        delWall.hide();

        addToGallery.setOnLongClickListener(this);
        setWall.setOnLongClickListener(this);
        delWall.setOnLongClickListener(this);


        serviceIntent = new Intent(getApplicationContext(), ImageService.class);
        actionService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isServiceRunning(ImageService.class, getApplicationContext())) {
                    stopService(serviceIntent);

                    buttonsFlags = -1;
                    // waiting while service is destroying (and cancel async task)
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            actionService.setText(R.string.start_service);
                            statusTextView.setText(R.string.dummy_content);
                            circularPB.setVisibility(View.GONE);
                            progressText.setVisibility(View.GONE);
                            statusTextView.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                            hideButtons();
                            circularPB.setVisibility(View.GONE);
                            progressText.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
                        }
                    }, 100);
                    Log.d(TAG, "On stop");
                } else {
                    statusTextView.setText(R.string.dummy_content_start);
                    actionService.setText(R.string.stop_service);
                    imageView.setImageBitmap(null);
                    imageView.destroyDrawingCache();
                    imageView.setVisibility(View.VISIBLE);
                    statusTextView.setVisibility(View.VISIBLE);
                    startService(serviceIntent);
                }
            }
        });


        receiver = new ImageBroadcast() {
            @Override
            public void onStartLoading() {
                if (bitmap != null) bitmap.recycle();
                imageView.setVisibility(View.GONE);
                hideButtons();
                buttonsFlags = -1;
                statusTextView.setVisibility(View.GONE);
                circularPB.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                progressBar.setIndeterminate(false);
                progressBar.setMax(100);
                time = 0;
            }

            @Override
            public void onPublishProgress(int progress, String message) {
                progressBar.setProgress(progress);
                progressBar.setVisibility(View.VISIBLE);
                circularPB.setVisibility(View.GONE);
                if (System.currentTimeMillis() - time > 500) {
                    progressText.setText(message);
                    time = System.currentTimeMillis();
                }
            }

            @Override
            public void onFinishLoading(String path) {
                showImage(path);
            }

            @Override
            public void onError(String message) {
                showError(isInternetConnectionAvailable() ?
                        message : "No internet connection");
            }
        };

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (isServiceRunning(ImageService.class, getApplicationContext())) {
            statusTextView.setText(R.string.dummy_content_start);
            actionService.setText(R.string.stop_service);
            switch (getIntent().getIntExtra(ACTION_TYPE_PARAM, -1)) {
                case ACTION_TYPE_START_LOADING:
                case ACTION_TYPE_PUBLISH_PROGRESS:
                    statusTextView.setVisibility(View.GONE);
                    break;
                case ACTION_TYPE_FINISH_LOADING:
                    statusTextView.setVisibility(View.GONE);
                    showImage(getIntent().getStringExtra(FILE_PATH_KEY));

            }
        }
        registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
    }

    private void showImage(String path) {
        lastPath = path;
        progressText.setText(R.string.decoding);
        progressBar.setVisibility(View.GONE);
        circularPB.setVisibility(View.VISIBLE);
        statusTextView.setVisibility(View.GONE);
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... args) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                try {
                    FileInputStream in = new FileInputStream(args[0]);
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 8;
                    BitmapFactory.decodeStream(in, null, options);
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    showError(e.getMessage());
                }
                bitmap = BitmapFactory.decodeFile(args[0]);
                return bitmap;
            }


            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                circularPB.setVisibility(View.GONE);
                progressText.setVisibility(View.GONE);
                progressText.setText("");
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                super.onPostExecute(bitmap);
            }
        }.executeOnExecutor(Executors.newFixedThreadPool(3), path);
    }

    public void showError(String message) {
        statusTextView.setText(message);
        statusTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        circularPB.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        hideButtons();
        buttonsFlags = -1;
        imageView.setVisibility(View.GONE);
    }

    private boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void onSetWallpaperClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Can't set wallpaper", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }).start();
        buttonsFlags -= 2;
        setWall.hide();
    }

    public void onDeleteImageClick(View view) {
        if (!new File(lastPath).delete()) {
            Toast.makeText(this, "Can't delete file", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageBitmap(null);
        bitmap.recycle();

        //for sure not met java out of memory :)
        imageView.destroyDrawingCache();
        buttonsFlags = 0;
        hideButtons();
        imageView.setVisibility(View.GONE);
        statusTextView.setVisibility(View.VISIBLE);
        onAddToGalleryClick(view);

    }

    public void onAddToGalleryClick(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        galleryIntent.setData(Uri.fromFile(new File(lastPath)));
        sendBroadcast(galleryIntent);
        buttonsFlags -= 4;
        addToGallery.hide();
    }


    public void onImageClick(View view) {
        if (isButtonShowing) {
            hideButtons();
            isButtonShowing = false;
        } else {
            if (imageView.getVisibility() == View.VISIBLE)
                if (buttonsFlags == -1) showButtons();
                else {
                    if ((buttonsFlags & 1) == 1)
                        delWall.show();
                    if ((buttonsFlags & 2) == 2)
                        setWall.show();
                    if ((buttonsFlags & 4) == 4)
                        addToGallery.show();

                }
            isButtonShowing = true;
        }
    }


    private void showButtons() {
        delWall.show();
        addToGallery.show();
        setWall.show();
        buttonsFlags = 7;
    }

    private void hideButtons() {
        addToGallery.hide();
        setWall.hide();
        delWall.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, Menu.CATEGORY_CONTAINER, "Clear cache").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Warning")
                        .setMessage("This option will delete all images downloaded from this app")
                        .setIcon(android.R.drawable.stat_sys_warning)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFromGallery();
                                if (FileUtils.deleteFileFully(new File(getExternalStorageDirectory(), PACKAGE_NAME))) {
                                    Log.d(TAG, "All photos deleted successfully");
                                    Toast.makeText(MainActivity.this, "Clear successfully", Toast.LENGTH_SHORT).show();
                                    imageView.setImageBitmap(null);
                                    bitmap.recycle();

                                    imageView.destroyDrawingCache();
                                    statusTextView.setVisibility(View.VISIBLE);
                                } else
                                    Toast.makeText(MainActivity.this, "Can't delete data", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();

                return false;
            }
        });
        return true;
    }

    private void deleteFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File dir = new File(getExternalStorageDirectory(), PACKAGE_NAME);
        if (dir.isDirectory() && dir.listFiles() != null) {
            File[] images = dir.listFiles();
            for (File f : images) {
                galleryIntent.setData(Uri.fromFile(f));
                sendBroadcast(galleryIntent);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "On destroy");
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.addToGallery:
                Toast.makeText(this, "Add image to system gallery", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delWall:
                Toast.makeText(this, "Delete image from storage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setWall:
                Toast.makeText(this, "Set image as background", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
