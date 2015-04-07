
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.Highgui;
import org.opencv.ml.*;

import java.util.*;
import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.sun.speech.freetts.lexicon.Lexicon;
import com.sun.speech.freetts.en.us.CMULexicon;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
public class SimpleANN {

	 private static final int ATTRIBUTES = 2;
	 private static final int TRAINING_SAMPLES = 4; 
	 private static final int CLASSES = 2; 
	 
	 public SimpleANN(){
		 this.train();
	 }
	 public void readFile(String e, Mat training_set, Mat classes){
		 String  line = null;
		try{
			// open input stream test.txt for reading purpose.
			FileReader fl = new FileReader(e);
			BufferedReader br = new BufferedReader(fl);
			
			for(int i = 0; i< TRAINING_SAMPLES; i++){
				line = br.readLine();
				String[] data = line.split(",");
				for(int j = 0; j <= ATTRIBUTES; j++ ){
					if(j< ATTRIBUTES)
						training_set.put(i, j,Float.parseFloat(data[j]));
					else{
						
							classes.put(i, Integer.parseInt(data[j]), 1);			
						
					}
						
				}
				//System.out.println(i);
				
			}
			br.close();
	  }catch(Exception ex){
	     ex.printStackTrace();
	  }
	 }
	 public void train(){
		 // Read an image.
	    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 Mat training_matrix= new Mat(TRAINING_SAMPLES,ATTRIBUTES,CvType.CV_32F);
		 Mat training_matrix_class= new Mat(TRAINING_SAMPLES,CLASSES,CvType.CV_32F);
		 Mat testing_matrix= new Mat(TRAINING_SAMPLES,ATTRIBUTES,CvType.CV_32F);
		 
		 this.readFile("training.txt", training_matrix, training_matrix_class);
		 this.readFile("training.txt", testing_matrix, training_matrix_class);
		 Mat classificationResult= new Mat(1,CLASSES,CvType.CV_32F);
		
		 Mat ann_layers= new Mat(3,1, CvType.CV_32S);
		 ann_layers.put(0,0,ATTRIBUTES);
		 ann_layers.put(1,0,2);
		 ann_layers.put(2,0,CLASSES);
		 CvANN_MLP ann= new CvANN_MLP(ann_layers);
		
		 CvANN_MLP_TrainParams params= new CvANN_MLP_TrainParams();
		 params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER+TermCriteria.EPS, 1000, 0.000001));
		 params.set_train_method( CvANN_MLP_TrainParams.BACKPROP);
		 params.set_bp_dw_scale(0.1);
		 params.set_bp_moment_scale(0.1);
		 
		 int iterations = ann.train(training_matrix, training_matrix_class,new Mat(),new Mat(),params,CvANN_MLP.NO_INPUT_SCALE);
		 //System.out.print(training_matrix.dump());
		 System.out.println(iterations);
		 ann.save("neuralnetwork");
		 
		 ann.predict(testing_matrix.row(2), classificationResult);
		 System.out.println(classificationResult.dump());
		 
	 
	 }


}
