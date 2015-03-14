
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


import java.util.*;
import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.sun.speech.freetts.lexicon.Lexicon;
import com.sun.speech.freetts.en.us.CMULexicon;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;



public class Hello {

    public static void main(String[] args) {
        
        // Read an image.
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	
        Mat source= Highgui.imread("images/001.jpg",Highgui.IMREAD_GRAYSCALE);
        Mat destination= source.clone();
        Imgproc.threshold(source,destination,210,255,Imgproc.THRESH_BINARY);
       
        Mat source2= Highgui.imread("images/gregg.jpg",Highgui.IMREAD_GRAYSCALE);
        Mat destination2= source.clone();
        Imgproc.threshold(source2,destination2,210,255,Imgproc.THRESH_BINARY);
        Highgui.imwrite("images/result2.png", destination2);
        //Imgproc.blur(m,n,new Size(30,30));
        
        Highgui.imwrite("images/new.jpg",source);
        Highgui.imwrite("images/new1.jpg",destination);
        int erosion_size = 1;
        int dilation_size = 1;
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(erosion_size + 1, erosion_size+1));
        Imgproc.dilate(destination, source, element);
        Imgproc.erode(source, destination, element);
        Highgui.imwrite("erosion.jpg", destination);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // reading image 
        Mat original =destination.clone();
        // thresholding the image to make a binary image
        Imgproc.threshold(destination, destination, 100, 128, Imgproc.THRESH_BINARY_INV);
        // find the center of the image
     

        // finding the contours
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // finding best bounding rectangle for a contour whose distance is closer to the image center that other ones
        double d_min = Double.MAX_VALUE;
        Rect rect_min = new Rect();
        int i=0;
        MatOfPoint2f         approxCurve = new MatOfPoint2f();
        for (MatOfPoint contour : contours) {
           
        	
            MatOfPoint2f contour2f = new MatOfPoint2f( contour.toArray() );
            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint( approxCurve.toArray() );

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

          
            if(rect.width > 20 && rect.width >20){
            	//crop
            	Mat result = original.submat(rect);
            	Highgui.imwrite("result"+i+".png", result);
            	Core.rectangle(original,new Point(rect.x-5,rect.y-5),new Point(rect.x+rect.width+5,rect.y+rect.height+5), new Scalar(0, 255, 255),3);
            	i++;
            	//resize
//            	Mat resizeimage = new Mat();
//            	Size sz = new Size(80,40);
//            	Imgproc.resize( result, resizeimage, sz );
//            	Highgui.imwrite("resize"+i+".png", resizeimage);
            	
            	Mat resizeimage = new Mat();
            	
            }
        }
        Imgproc.threshold(destination, destination, 100, 128, Imgproc.THRESH_BINARY_INV);
       
        Highgui.imwrite("result.png", original);
        
        
        Lexicon lexicon;
        lexicon= new CMULexicon();
		try {
			lexicon = CMULexicon.getInstance(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        String[] phones = lexicon.getPhones("beg","n");
        System.out.print("beg pronunciation: ");
       
        for(String phone : phones) System.out.print(phone + " ");
    }
}