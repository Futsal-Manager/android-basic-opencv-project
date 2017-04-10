package com.opencv.stories2.testopencv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.opencv.stories2.testopencv.LogManager.LogManager;
import com.opencv.stories2.testopencv.OpenCVModule.MainActivityProcesser;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    CameraBridgeViewBase javaCameraViewSimple;
    MainActivityProcesser mainActivityProcesser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mainActivityProcesser = new MainActivityProcesser();

        javaCameraViewSimple = (CameraBridgeViewBase) findViewById(R.id.javaCameraViewSimple);
        javaCameraViewSimple.setVisibility(SurfaceView.VISIBLE);
        javaCameraViewSimple.setCvCameraViewListener(mainActivityProcesser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCameraViewSimple != null) {
            javaCameraViewSimple.disableView();
        }
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    LogManager.PrintLog("MainActivity", "onManagerConnected", "OpenCV loaded successfully", DefineManager.LOG_LEVEL_INFO);
                    //mOpenCvCameraView.enableView();
                    //import error fix
                    //go project structure change build tool version latest
                    javaCameraViewSimple.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            LogManager.PrintLog("MainActivity", "onResume", "Internal OpenCV library not found. Using OpenCV Manager for initialization", DefineManager.LOG_LEVEL_WARN);
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            LogManager.PrintLog("MainActivity", "onResume", "OpenCV library found inside package. Using it!", DefineManager.LOG_LEVEL_INFO);
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
