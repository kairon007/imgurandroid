package example.mobilab.mzorz.com.imgurtest.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import example.mobilab.mzorz.com.imgurtest.R;


public class VersionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        String version = "";
        try{
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch(NameNotFoundException ex){
            ex.printStackTrace();
        }



        TextView tvVersion = (TextView) findViewById(R.id.txtVersion);
        tvVersion.setText(getDeviceName() + "\n\n" + "OS Version: " + Build.VERSION.RELEASE + "\n\n" + "App version: " + version + "\n\n" + "Date built: " + getDateBuilt() + "\n\n" + "Author: Mario Zorz" + "\n\n" + "Email: mariozorz@gmail.com");
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private String getDateBuilt(){
        //NOTE: code found here http://stackoverflow.com/questions/7607165/how-to-write-build-time-stamp-into-apk
        String s = "";
        try{
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), 0);
            ZipFile zf = new ZipFile(ai.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();
            s = SimpleDateFormat.getInstance().format(new java.util.Date(time));
            zf.close();
        }catch(Exception e){
        }
        return s;
    }
}
