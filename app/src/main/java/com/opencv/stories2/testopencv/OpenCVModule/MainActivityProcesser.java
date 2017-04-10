package com.opencv.stories2.testopencv.OpenCVModule;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

/**
 * Created by stories2 on 2017. 4. 11..
 */

public class MainActivityProcesser implements CameraBridgeViewBase.CvCameraViewListener2 {

    OpenCVModuleProcesser openCVModuleProcesser;

    public MainActivityProcesser() {
        openCVModuleProcesser = new OpenCVModuleProcesser();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat eachCameraFrameImage;
        eachCameraFrameImage = openCVModuleProcesser.DetectBallFromFrameImage(inputFrame.rgba());
        return eachCameraFrameImage;
    }
}
