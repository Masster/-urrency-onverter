package com.andrey.testsber.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class InputStreamUtils {

    public static String toString(InputStream istream) {
        Writer writer = new StringWriter();
        char[] buffer = new char[8192];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(istream, "windows-1251"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Log.e(InputStreamUtils.class.getCanonicalName(),e.getMessage());
        } finally {
            try {
                istream.close();
            } catch (Exception e) {
                Log.e(InputStreamUtils.class.getCanonicalName(),e.getMessage());
            }
        }
        return writer.toString();
    }
}
