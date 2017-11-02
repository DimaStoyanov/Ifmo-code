package ru.ifmo.droid2016.tmdb.utils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by Dima Stoyanov on 07.11.2016.
 * Project homework2
 * Start time : 15:50
 */

public final class ObjectUtils {
    private final static String TAG = ObjectUtils.class.getSimpleName();

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        byte[] result;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            result = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                Log.d(TAG, "Can't close baos " + ex.getMessage());
            }
        }
        return result;
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object result;
        try {
            in = new ObjectInputStream(bis);
            result = in.readObject();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Log.d(TAG, "Can't close input stream" + ex.getMessage());
            }
        }
        return result;
    }
}
