
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

public class ANN {
	 private static final int ATTRIBUTES = 1250;
	 private static final int TRAINING_SAMPLES = 21; 
	 private static final int CLASSES = 3; 
	 CvANN_MLP ann;
	 Mat classificationResult;
	 Mat testing_matrix;
	 Mat training_matrix, training_matrix_class, testing_matrix_class;
	 CvANN_MLP_TrainParams params;
	 public ANN(){
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 training_matrix= new Mat(TRAINING_SAMPLES,ATTRIBUTES,CvType.CV_64F);
		 training_matrix_class= new Mat(TRAINING_SAMPLES,CLASSES,CvType.CV_64F);
		 testing_matrix_class= new Mat(TRAINING_SAMPLES,CLASSES,CvType.CV_32F);
		 testing_matrix= new Mat(TRAINING_SAMPLES,ATTRIBUTES,CvType.CV_64F);
		 
		
		 
		 this.readFile("trainingdata3.txt", training_matrix, training_matrix_class);
		 this.readFile("testdata.txt", testing_matrix, testing_matrix_class);
		 classificationResult= new Mat(1,CLASSES,CvType.CV_64F);
		 
		 Mat ann_layers= new Mat(3,1, CvType.CV_32S);
		 ann_layers.put(0,0,ATTRIBUTES);
		 ann_layers.put(1,0,100);
		 ann_layers.put(2,0,CLASSES);
		 ann= new CvANN_MLP(ann_layers);
		
		// System.out.print(training_matrix_class.dump());
		 params= new CvANN_MLP_TrainParams();
		 params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER+TermCriteria.EPS, 2000, 0.000001));
		 params.set_train_method( CvANN_MLP_TrainParams.BACKPROP);
		 params.set_bp_dw_scale(0.1);
		 params.set_bp_moment_scale(0.1);
		 //System.out.print(training_matrix_class.dump());
		 //this.train();
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
						
						//classes.put(i, Integer.parseInt(data[j]), 1);
						if(data[j].equals("l")){
							classes.put(i, 0, 1);
							
						}
						else if(data[j].equals("r")){
							classes.put(i, 1, 1);
						}
						else {
							classes.put(i, 2, 1);
							
						}
						
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
		 int iterations = ann.train(training_matrix, training_matrix_class,new Mat(),new Mat(),params,CvANN_MLP.NO_INPUT_SCALE);
		 System.out.print(training_matrix_class.dump());
		 System.out.println(iterations);
		 ann.save("ann");
		 //ann.load("neuralnetwork");
//		 System.out.print(classificationResult.dump()); 
	 }
	 public int predict(int index){
		 ann.load("ann");
		 ann.predict(testing_matrix.row(index), classificationResult);
		 //System.out.print(classificationResult.dump());
		 return getMaximum();
	 }
	 private int getMaximum(){
		 int index=0;
		 double max=0;
		 for(int i=0;i<CLASSES;i++){
			 if(classificationResult.get(0, i)[0] > index){
				 index= i;
				 max= classificationResult.get(0, i)[0];
			 }
		 }
		 return index;
	 }
}
