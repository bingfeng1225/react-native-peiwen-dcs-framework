package com.qd.peiwen.dcsframework.tools;


import android.content.Context;

import java.io.File;

/**
 * Created by nick on 2018/1/2.
 */

public class FPCreator {
    public static String generateAlertPath(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append(context.getExternalFilesDir(null).getAbsolutePath());
        builder.append(File.separator);
        builder.append(context.getPackageManager().getApplicationLabel(context.getApplicationInfo()));
        builder.append(File.separator);
        builder.append("devices");
        builder.append(File.separator);
        builder.append("alert");
        builder.append(File.separator);
        return builder.toString();
    }

    public static String generateSpeakPath(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append(context.getExternalFilesDir(null).getAbsolutePath());
        builder.append(File.separator);
        builder.append(context.getPackageManager().getApplicationLabel(context.getApplicationInfo()));
        builder.append(File.separator);
        builder.append("devices");
        builder.append(File.separator);
        builder.append("speak");
        builder.append(File.separator);
        return builder.toString();
    }
}
