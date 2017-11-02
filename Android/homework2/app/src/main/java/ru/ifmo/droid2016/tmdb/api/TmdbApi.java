package ru.ifmo.droid2016.tmdb.api;

import android.net.Uri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Методы для работы с The Movie DB API
 * <p>
 * https://www.themoviedb.org/documentation/api
 */
public final class TmdbApi {

    private static final String API_KEY = "07f6e68e8ef16be5b510c1855d15d858";

    private static final Uri BASE_URI = Uri.parse("https://api.themoviedb.org/3");


    private TmdbApi() {
    }

    /**
     * Возвращает {@link HttpURLConnection} для выполнения запроса популярных фильмов
     * <p>
     * https://developers.themoviedb.org/3/movies/get-popular-movies
     *
     * @param lang язык пользователя
     */
    public static HttpURLConnection getPopularMoviesRequest(String lang, String current_page) throws IOException {
        Uri uri = BASE_URI.buildUpon().appendQueryParameter("api_key", API_KEY).appendPath("movie")
                .appendPath("popular").appendQueryParameter("language", lang).appendQueryParameter("page", current_page).build();
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }

    public static HttpURLConnection getSelectedVideoRequest(String lang, String id) throws IOException {
        Uri uri = BASE_URI.buildUpon().appendQueryParameter("api_key", API_KEY).appendPath("movie").appendPath(id)
                .appendPath("videos").appendQueryParameter("language", lang).build();
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }
}
