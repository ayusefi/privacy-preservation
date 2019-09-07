package pleakage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
public class PLeakage extends JFrame 
{
	JLabel l1,l2;
	JPanel p1,p2,p3;
	Font f1;
	JButton b1,b2,b3,b4;
	Login login;
	ImageIcon icon;
	JFileChooser chooser;
public PLeakage(Login log){
	super("Privacy Preserving of Intermediate Data Sets in Cloud");
	login = log;
	p1 = new JPanel();
	p1.setBackground(Color.black);
	l1 = new JLabel("<html><body><center><font size=6 color=#f5ea01>Cost-Effective Privacy preserving of Intermediate Data in Cloud</font></center></body></html>");
	p1.add(l1);
	getContentPane().add(p1,BorderLayout.NORTH);

	f1 = new Font("Courier New",Font.BOLD,14);

	p2 = new JPanel();
	icon = new ImageIcon("images/basket.jpg");
	l2 = new JLabel(icon);
	p2.add(l2);

	p3 = new JPanel();
	chooser = new JFileChooser(new File(""));
	b2 = new JButton("Upload Dataset");
	b2.setFont(f1);
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(null);
			if(option == chooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				Upload upload = new Upload(file);
				upload.setVisible(true);
				upload.pack();
				upload.readDataset();
			}
		}
	});

	b3 = new JButton("Access Dataset");
	b3.setFont(f1);
	p3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Access access = new Access();
			access.setVisible(true);
			access.pack();
		}
	});

	b3 = new JButton("Privacy-Preserving Algorithm");
	b3.setFont(f1);
	p3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try{
				Socket socket = new Socket("localhost",1111);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				Object req[] = {"runalgo"};
				out.writeObject(req);
				out.flush();
				JOptionPane.showMessageDialog(PLeakage.this,"Intermediate Dataset privacy preserved");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
	
	b1 = new JButton("Logout");
	b1.setFont(f1);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
			login.clear();
			login.setVisible(true);
		}
	});

	getContentPane().add(p3,BorderLayout.SOUTH);
	getContentPane().add(p2,BorderLayout.CENTER);
}
}