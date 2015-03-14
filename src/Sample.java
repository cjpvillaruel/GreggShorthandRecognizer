
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

public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // Read an image.
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	
        Mat source= Highgui.imread("images/test/training (1).jpg",Highgui.IMREAD_GRAYSCALE);
        Mat destination= source.clone();
        Imgproc.threshold(source,destination,210,255,Imgproc.THRESH_BINARY);
       
//        Mat source2= Highgui.imread("images/gregg.jpg",Highgui.IMREAD_GRAYSCALE);
//        Mat destination2= source.clone();
//        Imgproc.threshold(source2,destination2,210,255,Imgproc.THRESH_BINARY);
        Highgui.imwrite("images/newdata/result2.png", destination);

	}

}
