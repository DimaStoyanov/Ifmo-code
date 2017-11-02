package ru.ifmo.droid2016.tmdb.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;


/**
 * Информация о фильме, полученная из The Movie DB API
 */

public class Movie implements Serializable {

    /**
     * Path изображения постера фильма. Как из Path получить URL, описано здесь:
     * <p>
     * https://developers.themoviedb.org/3/getting-started/languages
     * <p>
     * В рамках ДЗ можно не выполнять отдельный запрос /configuration, а использовать
     * базовый URL для картинок: http://image.tmdb.org/t/p/ и
     */
    public final
    @NonNull
    String posterPath;

    /**
     * Название фильма на языке оригинала.
     */
    public final
    @NonNull
    String originalTitle;

    /**
     * Описание фильма на языке пользователя.
     */
    public final
    @Nullable
    String overviewText;

    /**
     * Название фильма на языке пользователя.
     */
    public final
    @Nullable
    String localizedTitle;

    /**
     * Id текущего фильма
     */
    public final int id;

    public Movie(String posterPath,
                 @NonNull String originalTitle,
                 @Nullable String overviewText,
                 @Nullable String localizedTitle,
                 int id) {
        this.posterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
        this.originalTitle = originalTitle;
        this.overviewText = overviewText;
        this.localizedTitle = localizedTitle;
        this.id = id;
    }


}
