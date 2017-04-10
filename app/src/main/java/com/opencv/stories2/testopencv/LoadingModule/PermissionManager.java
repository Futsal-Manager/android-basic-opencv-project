package com.opencv.stories2.testopencv.LoadingModule;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.opencv.stories2.testopencv.DefineManager;
import com.opencv.stories2.testopencv.LogManager.LogManager;
import com.opencv.stories2.testopencv.MainActivity;
import com.opencv.stories2.testopencv.R;

import java.util.Arrays;

import static com.opencv.stories2.testopencv.DefineManager.ANDROID_VERSION_OF_MARSHMALLOW;
import static com.opencv.stories2.testopencv.DefineManager.LOG_LEVEL_INFO;
import static com.opencv.stories2.testopencv.DefineManager.PERMISSION_REQUESTED_ORDER;

/**
 * Created by stories2 on 2017. 4. 11..
 */

public class PermissionManager extends AppCompatActivity {

    String[] needPermissionList;
    PermissionManagerProcesser permissionManagerProcesser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_manager);
        LogManager.PrintLog("PermissionManager", "onCreate", "Android Version: " + Build.VERSION.SDK_INT, DefineManager.LOG_LEVEL_INFO);

        permissionManagerProcesser = new PermissionManagerProcesser();

        if(Build.VERSION.SDK_INT >= ANDROID_VERSION_OF_MARSHMALLOW) {
            needPermissionList = new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            };
            if(!permissionManagerProcesser.CheckNeedPermissionStatus(getApplicationContext(), needPermissionList)) {
                ActivityCompat.requestPermissions(this, needPermissionList, PERMISSION_REQUESTED_ORDER);
            }
            else {
                MoveToMainActivity();
            }
        }
        else {
            MoveToMainActivity();
        }
    }

    void MoveToMainActivity() {
        Intent moveToMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(moveToMainActivityIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isPermissionGrantedSuccessfully = true;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTED_ORDER:
                LogManager.PrintLog("PermissionManager", "onRequestPermissionsResult", "permissions: " + Arrays.toString(permissions), LOG_LEVEL_INFO);
                LogManager.PrintLog("PermissionManager", "onRequestPermissionsResult", "grantResults: " + Arrays.toString(grantResults), LOG_LEVEL_INFO);
                for(int indexOfGrantResult : grantResults) {
                    if(indexOfGrantResult == -1) {
                        isPermissionGrantedSuccessfully = false;
                    }
                }
                if(isPermissionGrantedSuccessfully) {
                    MoveToMainActivity();
                }
                else {
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
