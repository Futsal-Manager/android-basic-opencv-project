package com.opencv.stories2.testopencv.OpenCVModule;

import com.opencv.stories2.testopencv.DefineManager;
import com.opencv.stories2.testopencv.LogManager.LogManager;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by stories2 on 2017. 4. 11..
 */

public class OpenCVModuleProcesser {

    public Mat DetectBallFromFrameImage(Mat cameraFrameImage) {
        try {

            Size sizeRgba = cameraFrameImage.size(), resizeImage = new Size(320, 240);
            Mat rgbaInnerWindow = new Mat((int)sizeRgba.width, (int)sizeRgba.height, CvType.CV_8UC1);
            Mat mIntermediateMat = new Mat();
            //originImage.copyTo(rgbaInnerWindow);

            int rows = (int) sizeRgba.height;
            int cols = (int) sizeRgba.width;

            int left = cols / 8;
            int top = rows / 8;

            int width = cols * 3 / 4;
            int height = rows * 3 / 4;

            //Log.d(applicationContext.getString(R.string.app_name), "info: " + rows + " " + cols + " " + left + " " + top + " " + width + " " + height);

            //rgbaInnerWindow = originImage
            //        .submat(left, left + width, top, top + height);
            //Imgproc.cvtColor(rgbaInnerWindow, originImage,
            //        Imgproc.COLOR_RGBA2GRAY);
            Imgproc.cvtColor(cameraFrameImage, rgbaInnerWindow, Imgproc.COLOR_BGRA2GRAY);

            //Imgproc.resize(rgbaInnerWindow, rgbaInnerWindow, resizeImage);
            Mat circles = rgbaInnerWindow.clone();
            Imgproc.GaussianBlur(rgbaInnerWindow, rgbaInnerWindow, new Size(9, 9), 2, 2);
            Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 10, 90);
            Imgproc.HoughCircles(mIntermediateMat, circles,
                    Imgproc.CV_HOUGH_GRADIENT, 1, 75, 50, 13, 35, 40);//파라미터가 의미하는 것을 찾아볼 것
            Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow,
                    Imgproc.COLOR_GRAY2BGRA, 4);

            for (int x = 0; x < circles.cols(); x++) {
                double vCircle[] = circles.get(0, x);
                if (vCircle == null)
                    break;
                Point pt = new Point(Math.round(vCircle[0]),
                        Math.round(vCircle[1]));
                int radius = (int) Math.round(vCircle[2]);
                //Log.d("cv", pt + " radius " + radius);
                LogManager.PrintLog("OpenCVModuleProcesser", "DetectBallFromFrameImage", "position: " + pt + " radius: " + radius, DefineManager.LOG_LEVEL_INFO);
                Imgproc.circle(rgbaInnerWindow, pt, 3, new Scalar(0, 0, 255), 5);
                Imgproc.circle(rgbaInnerWindow, pt, radius, new Scalar(255, 0, 0),
                        5);
            }

            circles.release();
            mIntermediateMat.release();
            //originImage = rgbaInnerWindow;
            //rgbaInnerWindow.release();
            rgbaInnerWindow.copyTo(cameraFrameImage);
            rgbaInnerWindow.release();

            //Imgproc.cvtColor(cameraFrameImage, cameraFrameImage, Imgproc.COLOR_BGRA2GRAY);

            return cameraFrameImage;
        }
        catch (Exception err) {
            //Log.d(applicationContext.getString(R.string.app_name), "Error in DetectBallPosition: " + err.getMessage());
            LogManager.PrintLog("OpenCVModuleProcesser", "DetectBallFromFrameImage", "Error: " + err.getMessage(), DefineManager.LOG_LEVEL_ERROR);
        }
        return cameraFrameImage;
    }
}
