import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.ml.CvANN_MLP;



	
	public class RecognizerGUI{ 
		public static void main(String[] args){
			//creates a new object of a centered frame
			ANN neuralNetwork= new ANN();
			CenteredFrame frame = new CenteredFrame(neuralNetwork);
			
		}
	}
	  

	class CenteredFrame extends JFrame{ 
		CenterPanel centerpanel;
		SelectionPanel selectionpanel;
		TopPanel toppanel;
		SidePanel sidepanel1,sidepanel2;

		public CenteredFrame(ANN nnetwork){
			super("Gregg Shorthand Recognizer");
			Container c = getContentPane();
			
			
			centerpanel = new CenterPanel(nnetwork);
			selectionpanel = new SelectionPanel();
			toppanel = new TopPanel();
			//sidepanel1 = new SidePanel();
			//sidepanel2 = new SidePanel();
			
			c.add(centerpanel,BorderLayout.CENTER);
			c.add(selectionpanel,BorderLayout.SOUTH);
			c.add(toppanel,BorderLayout.NORTH);
//			c.add(sidepanel1,BorderLayout.WEST);
//			c.add(sidepanel2,BorderLayout.EAST);
			
			// get screen dimensions using Toolkit and Dimension classes.
			Toolkit kit = Toolkit.getDefaultToolkit(); 
			Dimension screenSize = kit.getScreenSize(); 
			int screenHeight = screenSize.height; 
			int screenWidth = screenSize.width;
			//sets the size of the frame, half of the screen's width and height
			this.setSize(screenWidth, screenHeight); 
			// centers the frame in screen
			 this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setVisible(true); 
			 this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			this.setSize( 600, 600 );
		}
	}
	
	class ImageRenderer extends  DefaultTableCellRenderer{
		ImageIcon icon = null;  
		public ImageRenderer(){
			super();
			icon = new ImageIcon(getClass().getResource("images/result2.png"));
			
		}
		
	}

	class CenterPanel extends JPanel{
		ANN nnetwork;
		public CenterPanel(ANN nnetwork){
			this.nnetwork = nnetwork;
			this.setPreferredSize(new Dimension(200, 300));
	        String[] columnNames = {"Character", "Classification","Result"};
	        Object[][] data ={       
	        };
	        DefaultTableModel model = new DefaultTableModel(data, columnNames);
	        JTable table = new JTable( model )
	        {
	            //  Returning the Class of each column will allow different
	            //  renderers to be used based on Class
	            public Class getColumnClass(int column)
	            {
	                return getValueAt(0, column).getClass();
	            }
	        };
	        this.initializeTable(table);
	        JScrollPane scrollPane = new JScrollPane( table );
	        this.add(scrollPane);
	        table.setRowHeight(70);
		}
		private ImageIcon getImage(String path)
	    {
	        java.net.URL url = getClass().getResource(path);
	        if (url != null)
	            return (new ImageIcon(url));
	        else
	        {
	            //System.out.println(url);
	            return null;
	        }
	    }
		public void initializeTable(JTable testTable){
			String[] letters = {"l","r"};
			//openfile for the test set
			int counter=0;
			
			for(int j = 0 ; j < letters.length ; j++){
				int files = new File("images/letters/"+letters[j]).listFiles().length;
				for(int k=1; k <= files; k++){
					//insert images and classification in table
					//System.out.print("letters/"+letters[j]+"/"+"letter ("+k+").jpg");
					ImageIcon image = getImage("letters/"+letters[j]+"/"+"letter ("+k+").jpg");
					int index= nnetwork.predict(counter);
					Object[] row= {image, letters[j], letters[index]};
					DefaultTableModel model = (DefaultTableModel)testTable.getModel();
					model.addRow(row);		 
					counter++;
					
				}
			}
		}
	}




	class SelectionPanel extends JPanel implements ActionListener{

		public SelectionPanel(){	
			this.setPreferredSize(new Dimension(0, 70));
			//this.setBackground(new Color(0,143,219));
			
		}
		
		public void actionPerformed(ActionEvent e){
		
			
		}
	}

	class SidePanel extends JPanel{
		public SidePanel(){
			this.setBackground(new Color(0,143,219));
		}
	}

	class TopPanel extends JPanel {
		
		public TopPanel()
		{
			
			//this.setBackground(new Color(0,143,219));
			//this.setPreferredSize(new Dimension(0, 70));
		}
		
		

	}



