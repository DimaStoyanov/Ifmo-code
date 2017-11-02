package ru.ifmo.droid2016.tmdb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {

    /**
     * Читает содержимое потока в строку, используя указанный charset
     */
    public static String readToString(InputStream in) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buffer = getIOBuffer();
        int readSize;

        while ((readSize = in.read(buffer)) >= 0) {
            baos.write(buffer, 0, readSize);
        }
        final byte[] data = baos.toByteArray();
        final String content = new String(data);
        baos.close();
        return content;
    }


    /**
     * @return Есть ли сейчас живое соединение?
     */
    public static boolean isConnectionAvailable(@NonNull Context context, boolean defaultValue) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return defaultValue;
        }
        final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    /**
     * @return буфер размеров в 8кб для I/O. Потокобезопасный.
     */
    public static byte[] getIOBuffer() {
        byte[] buffer = bufferThreadLocal.get();
        if (buffer == null) {
            buffer = new byte[8192];
            bufferThreadLocal.set(buffer);
        }
        return buffer;
    }

    private static final ThreadLocal<byte[]> bufferThreadLocal = new ThreadLocal<>();


    private IOUtils() {}
}
