package ru.ifmo.servicedemoapplication.api;

import android.content.Context;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.util.JsonReader;

import com.facebook.stetho.urlconnection.StethoURLConnectionManager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.ifmo.servicedemoapplication.utils.FileUtils;
import ru.ifmo.servicedemoapplication.utils.FileUtils.ProgressCallback;

/**
 * Created by Dima Stoyanov on 23.11.2016.
 * Project ServiceDemoApplication
 * Start time : 1:09
 */

public class AlphaCodersApi {
    private final static String API_KEY = "b26c015e80b07110d6dd23756dd9c08f";
    private final static Uri BASE_URI = Uri.parse("https://wall.alphacoders.com/api2.0/get.php");

    private static HttpURLConnection getImagesHttpURLConnection() throws IOException {
        Uri uri = BASE_URI.buildUpon()
                .appendQueryParameter("auth", API_KEY)
                .appendQueryParameter("method", "random")
                .appendQueryParameter("count", "1").build();
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }


    public static String downloadImage(String url, ProgressCallback callback) throws IOException {
        File file = FileUtils.createFile();
        FileUtils.downloadFile(url, file, callback);
        return file.getAbsolutePath();
    }



    public static Pair<String, String> readImageURL() throws IOException {
        StethoURLConnectionManager stethoManager = new StethoURLConnectionManager("API");
        HttpURLConnection connection = getImagesHttpURLConnection();
        stethoManager.preConnect(connection, null);
        connection.connect();
        stethoManager.postConnect();
        InputStream in;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            in = connection.getInputStream();
            in = stethoManager.interpretResponseStream(in);
            return parseJson(in);
        } else {
            throw new IOException("HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }
    }


    private static Pair<String, String> parseJson(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.beginObject();
        if (!"success".equals(reader.nextName()) || !reader.nextBoolean() || !"wallpapers".equals(reader.nextName()))
            throw new IOException("Incorrect json");
        reader.beginArray();
        reader.beginObject();
        String imgUrl = null, thumbUrl;
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "url_image":
                    imgUrl = reader.nextString();
                    break;
                case "url_thumb":
                    thumbUrl = reader.nextString();
                    reader.close();
                    return new Pair<>(imgUrl, thumbUrl);
                default:
                    reader.skipValue();
            }
        }
        return null;
    }
}
