package ru.ifmo.droid2016.tmdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ru.ifmo.droid2016.tmdb.adapter.MoviesRecyclerAdapter;
import ru.ifmo.droid2016.tmdb.loader.LoadResult;
import ru.ifmo.droid2016.tmdb.loader.MoviesLoader;
import ru.ifmo.droid2016.tmdb.loader.ResultType;
import ru.ifmo.droid2016.tmdb.loader.SelectedMovieLoader;
import ru.ifmo.droid2016.tmdb.model.Movie;

import static ru.ifmo.droid2016.tmdb.Constant.FILM_ID;
import static ru.ifmo.droid2016.tmdb.Constant.LOCALE;
import static ru.ifmo.droid2016.tmdb.Constant.MOVIES;
import static ru.ifmo.droid2016.tmdb.Constant.PAGE;
import static ru.ifmo.droid2016.tmdb.Constant.SCROLL;
import static ru.ifmo.droid2016.tmdb.Constant.VIDEO_URL;
import static ru.ifmo.droid2016.tmdb.utils.ObjectUtils.deserialize;
import static ru.ifmo.droid2016.tmdb.utils.ObjectUtils.serialize;

/**
 * Экран, отображающий список популярных фильмов из The Movie DB.
 */
public class PopularMoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LoadResult<List<Movie>>> {

    private ProgressBar pBar;
    private RecyclerView rView;
    private MoviesRecyclerAdapter adapter;
    private int lastPage, scrollPosition;
    private boolean langChanged, showDialog = true;
    private AlertDialog dialog;
    private final String TAG = PopularMoviesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Log.d(TAG, "On create");
        pBar = (ProgressBar) findViewById(R.id.p_bar);
        rView = (RecyclerView) findViewById(R.id.r_view);
        rView.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            try {
                restoreData(savedInstanceState);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        rView.setRecyclerListener(new RecyclerView.RecyclerListener() {

            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                scrollPosition = holder.getAdapterPosition();
                // If user scrolled over 50% of movies list and we didn't start downloading next page yet
                // we will download next page
                if (2 * holder.getAdapterPosition() / adapter.getItemCount() >= 1
                        && getSupportLoaderManager().getLoader(lastPage) == null) {
                    Log.d(TAG, "Downloading page " + (lastPage + 1));
                    getSupportLoaderManager().initLoader(++lastPage, null, PopularMoviesActivity.this);
                }
            }
        });


        if (savedInstanceState == null) {
            getSupportLoaderManager().initLoader(++lastPage, null, this);
        }

    }


    @SuppressWarnings("unchecked")
    private void restoreData(Bundle savedInstanceState) throws IOException, ClassNotFoundException {
        Log.d(TAG, "restore data");
        lastPage = savedInstanceState.getInt(PAGE);
        scrollPosition = savedInstanceState.getInt(SCROLL);
        String lastLanguage = savedInstanceState.getString(LOCALE);
        if (lastLanguage != null && !lastLanguage.equals(Locale.getDefault().getLanguage())) {
            Log.d(TAG, "Language changed. Need to update " + lastPage + " pages");
            langChanged = true;
            getSupportLoaderManager().initLoader(1, null, this);
            return;
        }
        List<Movie> movies = (List<Movie>) deserialize(savedInstanceState.getByteArray(MOVIES));
        adapter = new MoviesRecyclerAdapter(this, movies, new OnFilmClickListener());
        rView.setAdapter(adapter);
        rView.setVisibility(View.VISIBLE);
        rView.scrollToPosition(scrollPosition);
    }


    @Override
    public Loader<LoadResult<List<Movie>>> onCreateLoader(int id, Bundle args) {
        if (id == 1) pBar.setVisibility(View.VISIBLE);
        return new MoviesLoader(this, id);
    }


    @Override
    @UiThread
    public void onLoadFinished(Loader<LoadResult<List<Movie>>> loader, LoadResult<List<Movie>> result) {
        if (result.resultType == ResultType.OK && result.data != null && !result.data.isEmpty()) {
            if (loader.getId() == 1) {
                adapter = new MoviesRecyclerAdapter(this, result.data, new OnFilmClickListener());
                rView.setAdapter(adapter);
                rView.setVisibility(View.VISIBLE);
            } else {
                adapter.onUpdate(result.data);
            }

            if (loader.getId() < lastPage) {
                getSupportLoaderManager().initLoader(loader.getId() + 1, null, this);
            }
            // if we download all data in new language
            if (loader.getId() == lastPage && langChanged) {
                rView.scrollToPosition(scrollPosition);
                langChanged = false;
            }
        } else {
            final int errorTextResId;

            if (result.data != null && result.data.isEmpty()) {
                errorTextResId = R.string.no_movie;
            } else if (result.resultType == ResultType.NO_INTERNET) {
                errorTextResId = R.string.no_internet;
            } else {
                errorTextResId = R.string.error;
            }
            showError(getString(errorTextResId), this, loader.getId(), null);
        }
        // while we downloading next pages in background, progress bar isn't show
        if (!langChanged) pBar.setVisibility(View.GONE);
        onLoaderReset(loader);
    }


    @Override
    public void onLoaderReset(Loader<LoadResult<List<Movie>>> loader) {
        getSupportLoaderManager().destroyLoader(loader.getId());
    }


    private void showError(String message, final LoaderManager.LoaderCallbacks callbacks, final int id, final Bundle bundle) {
        if (!showDialog || (dialog != null && dialog.isShowing())) return;
        // if error was in downloading page movies, we don't need to skip it
        if (bundle == null) lastPage--;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDialog = false;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportLoaderManager().initLoader(id, bundle, callbacks);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    class OnFilmClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            pBar.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putInt(FILM_ID, (int) id);
            getSupportLoaderManager().initLoader(position, bundle, new VideoLoaderManager());
        }
    }


    class VideoLoaderManager implements LoaderManager.LoaderCallbacks<LoadResult<List<String>>> {
        private Bundle bundle;

        @Override
        public Loader<LoadResult<List<String>>> onCreateLoader(int id, Bundle args) {
            bundle = args;
            return new SelectedMovieLoader(PopularMoviesActivity.this, args.getInt(FILM_ID), Locale.getDefault().getLanguage());
        }

        @Override
        public void onLoadFinished(Loader<LoadResult<List<String>>> loader, LoadResult<List<String>> result) {
            if (result.resultType == ResultType.OK && result.data != null && !result.data.isEmpty()) {
                Intent intent;
                pBar.setVisibility(View.GONE);
                intent = new Intent(PopularMoviesActivity.this, SelectedMovieActivity.class);
                intent.putExtra(VIDEO_URL, result.data.get(0));
                startActivity(intent);
            } else {
                final int errorTextResId;
                if (result.data != null && result.data.isEmpty()) {
                    errorTextResId = R.string.no_movie;
                } else if (result.resultType == ResultType.NO_INTERNET) {
                    errorTextResId = R.string.no_internet;
                } else {
                    errorTextResId = R.string.error;
                }
                showError(getString(errorTextResId), new VideoLoaderManager(), loader.getId(), bundle);
                pBar.setVisibility(View.GONE);
            }

            onLoaderReset(loader);
        }

        @Override
        public void onLoaderReset(Loader<LoadResult<List<String>>> loader) {
            getSupportLoaderManager().destroyLoader(loader.getId());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "save data");
        if (adapter == null || adapter.getMovies() == null) return;
        try {
            List<Movie> temp = adapter.getMovies();
            outState.putByteArray(MOVIES, serialize(temp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        outState.putInt(PAGE, lastPage);
        outState.putString(LOCALE, Locale.getDefault().getLanguage());
        outState.putInt(SCROLL, scrollPosition);
    }


}
