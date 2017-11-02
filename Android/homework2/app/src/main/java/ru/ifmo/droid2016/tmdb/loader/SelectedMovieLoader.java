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

import ru.ifmo.droid2016.tmdb.api.TmdbApi;
import ru.ifmo.droid2016.tmdb.utils.IOUtils;

import static ru.ifmo.droid2016.tmdb.Constant.API;

/**
 * Created by Dima Stoyanov on 21.10.2016.
 * Project homework2
 * Start time : 23:38
 */

public class SelectedMovieLoader extends AsyncTaskLoader<LoadResult<List<String>>> {

    private final int id;
    private final String lang;
    private final String TAG = SelectedMovieLoader.class.getSimpleName();

    public SelectedMovieLoader(Context context, int id, String lang) {
        super(context);
        this.id = id;
        this.lang = lang;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public LoadResult<List<String>> loadInBackground() {
        final StethoURLConnectionManager stethoManager = new StethoURLConnectionManager(API);
        ResultType resultType = ResultType.ERROR;
        List<String> data = null;
        HttpURLConnection connection = null;

        try {
            connection = TmdbApi.getSelectedVideoRequest(lang, String.valueOf(id));
            stethoManager.preConnect(connection, null);
            connection.connect();
            stethoManager.postConnect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Movie url downloaded");
                data = parseMovie(stethoManager.interpretResponseStream(connection.getInputStream()));
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
        Log.d(TAG, "Movie url parsed");
        return new LoadResult<>(resultType, data);
    }

    private List<String> parseMovie(InputStream in) throws IOException, JSONException {
        ArrayList<String> result = new ArrayList<>();
        JSONArray moviesJson = new JSONObject(IOUtils.readToString(in)).getJSONArray("results");
        if (moviesJson.length() == 0) {
            result.add("");
            return result;
        }
        JSONObject currentMovie = moviesJson.getJSONObject(0);
        result.add(currentMovie.optString("key", ""));
        return result;
    }
}
