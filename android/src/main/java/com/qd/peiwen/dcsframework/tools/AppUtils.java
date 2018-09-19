package com.qd.peiwen.dcsframework.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jeffreyliu on 17/1/16.
 */

public class AppUtils {
    /**
     * 获取PackageManager
     **/
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取PackageManager
     **/
    public static PackageManager getPackageManager(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager;
    }

    /**
     * 获取单个App的Package信息
     **/
    public static PackageInfo getPackageInfo(Context context,@NonNull String packageName) {
        PackageManager packageManager = getPackageManager(context);
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取单个App的Application信息
     **/
    public static ApplicationInfo getApplicationInfo(Context context,@NonNull String packageName) {
        PackageManager packageManager = getPackageManager(context);
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo;
    }

    /**
     * 获取单个App图标
     **/
    public static Drawable getAppIcon(Context context,@NonNull String packageName) {
        PackageManager packageManager = getPackageManager(context);
        Drawable icon = null;
        try {
            icon = packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return icon;
    }

    /**
     * 获取单个App名称
     **/
    public static String getAppName(Context context,String packageName) {
        PackageManager packageManager = getPackageManager(context);
        if (null == packageName) {
            packageName = getPackageName(context);
        }
        ApplicationInfo applicationInfo = getApplicationInfo(context,packageName);
        if (null != applicationInfo) {
            String appName = packageManager.getApplicationLabel(applicationInfo).toString();
            return appName;
        } else {
            return null;
        }
    }

    /**
     * 获取单个App版本号Name
     **/
    public static String getAppVersionName(Context context,@NonNull String packageName) {
        PackageInfo packageInfo = getPackageInfo(context,packageName);
        if (null != packageInfo) {
            return packageInfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 获取单个App版本号Code
     **/
    public static int getAppVersionCode(Context context,@NonNull String packageName) {
        PackageInfo packageInfo = getPackageInfo(context,packageName);
        if (null != packageInfo) {
            return packageInfo.versionCode;
        } else {
            return -1;
        }
    }


    public static boolean isAppDebug(Context context) {
        try {
            ApplicationInfo info = getApplicationInfo(context,getPackageName(context));
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void openAppByPkg(Context context,String packageName) {
        try {
            PackageManager packageManager = getPackageManager(context);
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            if (intent == null) {
                System.out.println("APP not found!");
                return;
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //安装 apk
    public static void installApk(Context context,String fileName) {
        File apk = new File(fileName);
        if (apk.exists()) {
            try {
                String cmd = "chmod 777 " + fileName;
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri = Uri.fromFile(apk);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

    }
    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static  boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
