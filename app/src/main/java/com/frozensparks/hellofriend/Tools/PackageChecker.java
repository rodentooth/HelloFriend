package com.frozensparks.hellofriend.Tools;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Emanuel Graf on 07.09.2017.
 */

public class PackageChecker {
    public static boolean appInstalledOrNot(String uri, Context c) {
        PackageManager pm = c.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
