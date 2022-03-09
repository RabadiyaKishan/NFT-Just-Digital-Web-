package com.jdw.nftcreator;

import android.os.Environment;

import java.io.File;

public class APPUtility {

    final static public String Account_name = "Elvee Infotech";
    final static public String PrivacyPolicy = "https://www.google.com/";
    public static File getAppDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }
}
