package com.xys.accessibilitydemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import com.xys.accessibilitydemo.utils.BaseAccessibilityService;
import com.xys.accessibilitydemo.utils.FileUtil;
import com.xys.accessibilitydemo.utils.T;

import java.io.File;

public class MainActivity extends Activity {

    private PackageManager mPackageManager;
    private String[] mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseAccessibilityService.getInstance().init(this);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{"com.android.settings"};
    }

    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    public void goApp(View view) {
        Intent intent = mPackageManager.getLaunchIntentForPackage(mPackages[0]);
        if (intent == null) {
            T.show("打开 " + mPackages[0] + " 失败！请确认已经安装！");
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanProcess(View view) {
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public void autoInstall(View view) {


        File f = new File(srcAppName);
        if (!f.exists()) {
            FileUtil.copyApkFromAssets(this, srcAppName, targetAppName);
        }
        Uri uri = Uri.fromFile(new File(targetAppName));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    private String targetAppName = "/sdcard/sqlites.apk";
    private String srcAppName = "sqlites.apk";
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

}
