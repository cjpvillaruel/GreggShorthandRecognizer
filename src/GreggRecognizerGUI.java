import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.sql.*;

public class GreggRecognizerGUI extends JFrame {
	MainPanel mainPanel;
	
	public GreggRecognizerGUI(){
		super("Gregg Shorthand Recognizer");
		this.addComponents();
		// get screen dimensions using Toolkit and Dimension classes.
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		
		this.setSize( 800, 600 );   
		
	
		// centers the frame in screen
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}
	private void addComponents(){
		mainPanel = new MainPanel();
		Container c = this.getContentPane();
		c.add(mainPanel);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GreggRecognizerGUI gui= new GreggRecognizerGUI();
		
	}

}

	class MainPanel extends JPanel{
		private Image background;
		JTabbedPane tabbedPane;
		
		public MainPanel(){
			java.net.URL imgURL = getClass().getResource("background/bg.jpg");
			background = Toolkit.getDefaultToolkit().getImage(imgURL  );
			this.setLayout(null);
			this.addComponents();
		}
		public void addComponents(){
			
			tabbedPane = new JTabbedPane();
			tabbedPane.add("Train", new TrainPanel());
			tabbedPane.add("Test", new JPanel());
			JLabel hi= new JLabel("Hi");
			tabbedPane.setBounds(100, 150, 600, 400);
			this.add(tabbedPane);
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
	}

	class TrainPanel extends JPanel implements ActionListener{
		JButton trainButton, selectFileButton, annButton, svmButton, bnButton;
		JLabel statusLabel;
		JFileChooser fileChooser;
		JTextField path;
		public TrainPanel(){
			this.setLayout(null);
			
			trainButton= new JButton("Train");
			trainButton.setBounds(100, 150, 100, 50);
			//this.add(trainButton);
			
			statusLabel= new JLabel("hi");
			statusLabel.setBounds(100, 250, 200, 200);
			
			path= new JTextField("words");
			path.setBounds(70, 50, 250, 30);
			this.add(path);
			
			//adding select button
			selectFileButton= new JButton("Select Folder");
			selectFileButton.setBounds(340, 50, 150, 30);
			selectFileButton.addActionListener(this);
			
			this.add(selectFileButton);
			
			
			annButton= new JButton("<html>"+"Train with\n ANN".replace("\n", "<br>")+"</html>");
			annButton.setBounds(100, 150, 100, 100);
			this.add(annButton);
			annButton.addActionListener(this);
			
			
			svmButton= new JButton("<html>"+"Train with\n SVM".replace("\n", "<br>")+"</html>");
			svmButton.setBounds(250, 150, 100, 100);
			this.add(svmButton);
			svmButton.addActionListener(this);
			
			bnButton= new JButton("<html>"+"Train with\n BN".replace("\n", "<br>")+"</html>");
			bnButton.setBounds(400, 150, 100, 100);
			this.add(bnButton);
			bnButton.addActionListener(this);
			
			
			this.setBackground(Color.white);
			
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new java.io.File("."));
		    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== selectFileButton){
				int a= fileChooser.showOpenDialog(this);
				if (a == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					path.setText(file.getAbsolutePath());
				}
			}
			else if(e.getSource()== annButton){
				//training ANN
				annButton.setEnabled(false);
				
			}
		}
	}