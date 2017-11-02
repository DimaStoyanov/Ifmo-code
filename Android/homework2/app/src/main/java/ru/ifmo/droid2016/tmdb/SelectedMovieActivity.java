package ru.ifmo.droid2016.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import ru.ifmo.droid2016.tmdb.api.YoutubeApi;

public class SelectedMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    ProgressBar pBar;
    TextView err_text;
    private final String TAG = SelectedMovieActivity.class.getSimpleName();
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_movie);
        Log.d(TAG, "ON SELECTED CREATE");
        init();
        Bundle intent = getIntent().getExtras();
        url = intent.getString(Constant.VIDEO_URL);
        if ("".equals(url)) {
            err_text.setText(getString(R.string.no_movie));
            return;
        }

        youTubeView.initialize(YoutubeApi.YOUTUBE_API_KEY, this);
    }


    private void init() {
        youTubeView = (YouTubePlayerView) findViewById(R.id.video);
        pBar = (ProgressBar) findViewById(R.id.p_bar);
        err_text = (TextView) findViewById(R.id.err_text);
        err_text.setVisibility(View.GONE);
        pBar.setVisibility(View.GONE);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.loadVideo(url);
        }
        youTubePlayer.play();
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.player_error) + " " + errorReason.toString();
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(YoutubeApi.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


}

