
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


public class Preprocessing {

	Mat[] samples;
	Mat sample;
	public Preprocessing() {
		//open files from a folder
		
		int fileNumber = new File("images/testdata").listFiles().length;
		//samples = new Mat[20];
		
		/*
		 * for each image, align, crop the table. Crop the table and put it in 
		 * respective folder
		 * 
		 * */
		// Read an image.
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		int count=0;
		int height=45;
		int width = 90;
    	for(int i=1 ; i <= fileNumber; i++){
			sample = Highgui.imread("images/test/training ("+i+").jpg",Highgui.IMREAD_GRAYSCALE);
			Mat destination= sample.clone();
	        Imgproc.threshold(sample,destination,210,255,Imgproc.THRESH_BINARY);
	        
	        for(int y=208; y < destination.height()-200; y+=height+5){
	        	for(int x=83;x<destination.width()-150; x+= width+5){
	        		Rect letter= new Rect(x, y, width, height);
	        		Mat result = destination.submat(letter);
	            	Highgui.imwrite("images/test/result"+i+"_"+count+".jpg", result);
	            	count++;
	        	}
	        }
			//Highgui.imwrite("images/crop/crop"+i+".jpg", destination);
		}
	}
	
	public void convertToBits(){
		String[] letters = {"l","r"};
		String test="";
		for(int j = 0 ; j < letters.length ; j++){
			int files = new File("images/letters/"+letters[j]).listFiles().length;
			for(int k=1; k <= files; k++){
				Mat letter = Highgui.imread("images/letters/"+letters[j]+"/"+"letter ("+k+").jpg",Highgui.IMREAD_GRAYSCALE);
				
            	Mat resizeimage = new Mat();
            	Size sz = new Size(50,25);
            	Imgproc.resize( letter, resizeimage, sz );
            	Highgui.imwrite("images/letters/"+letters[j]+"/"+"resize"+k+".png", resizeimage);
				for(int x =0; x< resizeimage.height(); x++){
					for(int y=0; y< resizeimage.width(); y++){
						//System.out.println(letter.get(x,y)[0]);
						if(resizeimage.get(x, y)[0] > 0.0)
						test+="0";
						else test+="1";
						test+=",";
					}
				}
				test+=letters[j]+"\n";
			}
			
		}
		//System.out.print(test);
		try{
		File file = new File("trainingdata2.txt");
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(test);
		bw.close();
		}
		catch(Exception e){
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Preprocessing preprocess = new Preprocessing(); 
		//preprocess.convertToBits();
		
		ANN neuralNetwork= new ANN();
		
		//neuralNetwork.predict();
		//SimpleANN a= new SimpleANN();
	}

}
