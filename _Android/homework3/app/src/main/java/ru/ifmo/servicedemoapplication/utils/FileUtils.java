package ru.ifmo.servicedemoapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.stetho.urlconnection.StethoURLConnectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Dima Stoyanov on 23.11.2016.
 * Project ServiceDemoApplication
 * Start lastTime : 1:27
 */

public final class FileUtils {

    public static final String PACKAGE_NAME = "serviceDemoApplicationImages";

    private final static String TAG = FileUtils.class.getSimpleName();

    private static long lastTime;
    private static int lastBytes;


    public static File createFile() throws IOException {
        File directory = new File(getExternalStorageDirectory().getAbsolutePath(), PACKAGE_NAME);
        if (!directory.isDirectory() && !directory.mkdir())
            throw new IOException("Can't create directory");
        return File.createTempFile("img", ".png", directory);
    }


    public static boolean deleteFileFully(File f) {
        if (!f.exists())
            return false;
        if (f.isFile())
            return f.delete();
        if (f.isDirectory()) {
            for (File sub : f.listFiles())
                deleteFileFully(sub);
        }
        return f.delete();
    }


    /**
     * Выполняет сетевой запрос для скачивания файла, и сохраняет ответ в указанный файл.
     *
     * @param downloadUrl      URL - откуда скачивать (http:// или https://)
     * @param destFile         файл, в который сохранять.
     * @param progressCallback опциональный callback для уведомления о прогрессе скачивания
     *                         файлы. Его метод onProgressChanged вызывается синхронно
     *                         в текущем потоке.
     * @throws IOException В случае ошибки выполнения сетевого запроса или записи файла.
     */
    public static void downloadFile(@Nullable String downloadUrl,
                                    File destFile,
                                    @Nullable ProgressCallback progressCallback) throws IOException {
        if(downloadUrl == null){
            throw new IOException("Null url");
        }
        Log.d(TAG, "Start downloading url: " + downloadUrl);
        Log.d(TAG, "Saving to file: " + destFile);

        StethoURLConnectionManager stethoManager = new StethoURLConnectionManager("Download");

        // Выполняем запрос по указанному урлу. Поскольку мы используем только http:// или https://
        // урлы для скачивания, мы привести результат к HttpURLConnection. В случае урла с другой
        // схемой, будет ошибка.
        HttpURLConnection conn = (HttpURLConnection) new URL(downloadUrl).openConnection();
        stethoManager.preConnect(conn, null);

        InputStream in = null;
        OutputStream out = null;

        try {

            // Проверяем HTTP код ответа. Ожидаем только ответ 200 (ОК).
            // Остальные коды считаем ошибкой.
            int responseCode = conn.getResponseCode();
            Log.d(TAG, "Received HTTP response code: " + responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new FileNotFoundException("Unexpected HTTP response: " + responseCode
                        + ", " + conn.getResponseMessage());
            }

            // Узнаем размер файла, который мы собираемся скачать
            // (приходит в ответе в HTTP заголовке Content-Length)
            int contentLength = conn.getContentLength();
            Log.d(TAG, "Content Length: " + contentLength);

            stethoManager.postConnect();

            // Создаем временный буффер для I/O операций размером 8кб
            byte[] buffer = new byte[1 << 13];

            // Размер полученной порции в байтах
            int receivedBytes;
            // Сколько байт всего получили (и записали).
            int receivedLength = 0;
            // прогресс скачивания от 0 до 100
            int progress = 0;

            // Начинаем читать ответ
            in = conn.getInputStream();
            in = stethoManager.interpretResponseStream(in);
            // И открываем файл для записи
            out = new FileOutputStream(destFile);
            lastTime = System.currentTimeMillis();
            lastBytes = 0;
            // В цикле читаем данные порциями в буффер, и из буффера пишем в файл.
            // Заканчиваем по признаку конца файла -- in.read(buffer) возвращает -1
            while ((receivedBytes = in.read(buffer)) >= 0) {
                out.write(buffer, 0, receivedBytes);
                receivedLength += receivedBytes;

                if (contentLength > 0) {
                    int newProgress = 100 * receivedLength / contentLength;
                    if (newProgress > progress && progressCallback != null) {
//                        Log.d(TAG, "Downloaded " + newProgress + "% of " + contentLength + " bytes");
                        progressCallback.onProgressChanged(newProgress,
                                String.valueOf(getProgressMessage(receivedLength, contentLength)));
                    }
                    progress = newProgress;
                }
            }

            if (receivedLength != contentLength) {
                Log.w(TAG, "Received " + receivedLength + " bytes, but expected " + contentLength);
            } else {
                Log.d(TAG, "Received " + receivedLength + " bytes");
            }

        } catch (IOException e) {
            // Ловим ошибку только для отладки, кидаем ее дальше
            stethoManager.httpExchangeFailed(e);
            throw e;

        } finally {
            // Закрываем все потоки и соедиениние
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close HTTP input stream: " + e, e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close file: " + e, e);
                }
            }
            conn.disconnect();
        }


    }

    private static String getProgressMessage(int cur, int all) {
        long speed = ((cur - lastBytes) * 1000) / ((System.currentTimeMillis() - lastTime + 1));
        lastTime = System.currentTimeMillis();
        lastBytes = cur;
        return "Downloading " + getNormalSize(cur) + " of " + getNormalSize(all) + ", " + getNormalSize(speed) + "/s";
    }


    private static String getNormalSize(long cur) {
        double sizeInMb = cur >> 20;
        return sizeInMb == 0 ? (cur / (1 << 10)) + " Kb" : Math.ceil(cur / ((double) (1 << 20)) * 10) / 10 + " Mb";
    }


    /**
     * Callback интерфейс для получения уведомления о прогрессе.
     */
    public interface ProgressCallback {

        /**
         * Вызывается при изменении значения прогресса.
         *
         * @param progress новое значение прогресса от 0 до 100.
         * @param message  Описание состояния прогресса
         */
        void onProgressChanged(int progress, String message);

    }


}
