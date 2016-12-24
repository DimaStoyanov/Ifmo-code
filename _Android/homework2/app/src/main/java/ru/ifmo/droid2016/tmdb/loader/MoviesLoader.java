package ru.ifmo.droid2016.tmdb.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.facebook.stetho.urlconnection.StethoURLConnectionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.ifmo.droid2016.tmdb.api.TmdbApi;
import ru.ifmo.droid2016.tmdb.model.Movie;
import ru.ifmo.droid2016.tmdb.utils.IOUtils;

/**
 * Created by Dima Stoyanov on 19.10.2016.
 * Project homework2
 * Start time : 20:00
 */

public class MoviesLoader extends AsyncTaskLoader<LoadResult<List<Movie>>> {
    private String current_page;
    private final String TAG = MoviesLoader.class.getSimpleName();

    public MoviesLoader(Context context, int current_page) {
        super(context);
        this.current_page = String.valueOf(current_page);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public LoadResult<List<Movie>> loadInBackground() {
        final StethoURLConnectionManager stethoManager = new StethoURLConnectionManager("API");
        ResultType resultType = ResultType.ERROR;
        List<Movie> data = null;
        HttpURLConnection connection = null;

        try {
            connection = TmdbApi.getPopularMoviesRequest(Locale.getDefault().getLanguage(), current_page);
            stethoManager.preConnect(connection, null);
            connection.connect();
            stethoManager.postConnect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Data downloaded");
                data = parse(stethoManager.interpretResponseStream(connection.getInputStream()));
                resultType = ResultType.OK;
            } else {
                throw new BadResponseException("HTTP: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            stethoManager.httpExchangeFailed(e);
            if (!IOUtils.isConnectionAvailable(getContext(), false)) {
                resultType = ResultType.NO_INTERNET;
                Log.d(TAG, "No internet connection");
            } else {
                Log.d(TAG, e.getMessage() + " " + e.getCause());
            }
        } catch (BadResponseException | JSONException e) {
            Log.d(TAG, e.getMessage() + " " + e.getCause());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.d(TAG, "Data parsed");
        return new LoadResult<>(resultType, data);
    }


    private List<Movie> parse(InputStream in) throws IOException, JSONException {
        JSONArray moviesJson = new JSONObject(IOUtils.readToString(in)).getJSONArray("results");
        List<Movie> movies = new ArrayList<>();
        JSONObject currentMovie;
        for (int i = 0; i < moviesJson.length(); i++) {
            currentMovie = moviesJson.getJSONObject(i);
            movies.add(new Movie(currentMovie.optString("poster_path", null), currentMovie.optString("original_title", ""),
                    currentMovie.optString("overview", ""), currentMovie.optString("title", ""), currentMovie.optInt("id", 0)));
        }
        return movies;
    }

}
