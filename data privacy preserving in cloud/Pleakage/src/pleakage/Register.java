package pleakage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
public class Register extends JFrame
{
	CustomPanel p1;
	JLabel l1,l2,l3;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	Font f1;
	Login login;
public Register(Login log){
	super("Registration Screen");
	login = log;
	p1 = new CustomPanel();
	p1.setTitle("    Registration Screen");
	p1.setLayout(null);
	
	JPanel main = new JPanel();
	main.setLayout(new BorderLayout());

	f1 = new Font("Microsoft Sanserif",Font.BOLD,11);
	JPanel pan1 = new JPanel(); 
	l1 = new JLabel("Username");
	l1.setForeground(Color.white);
	l1.setFont(f1);
	pan1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	pan1.add(tf1);
	
	JPanel pan2 = new JPanel(); 
	l2 = new JLabel("Password");
	l2.setForeground(Color.white);
	l2.setFont(f1);
	pan2.add(l2);
	tf2 = new JPasswordField(15);
	tf2.setFont(f1);
	pan2.add(tf2);

	JPanel pan3 = new JPanel(); 
	pan3.setLayout(new BorderLayout());

	JPanel pan4 = new JPanel();
	l3 = new JLabel("Password");
	l3.setForeground(Color.white);
	l3.setFont(f1);
	pan4.add(l3);
	tf3 = new JPasswordField(15);
	tf3.setFont(f1);
	pan4.add(tf3);

	JPanel pan5 = new JPanel();
	b1 = new JButton("Register");
	b1.setFont(f1);
	pan5.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});
	b2 = new JButton("Reset");
	b2.setFont(f1);
	pan5.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
		}
	});

	

	main.setBackground(new Color(128, 128, 128));
	pan1.setBackground(new Color(128, 128, 128));
	pan2.setBackground(new Color(128, 128, 128));
	pan3.setBackground(new Color(128, 128, 128));
	pan4.setBackground(new Color(128, 128, 128));
	pan5.setBackground(new Color(128, 128, 128));
	pan3.add(pan4,BorderLayout.NORTH);
	pan3.add(pan5,BorderLayout.CENTER);
	main.add(pan1,BorderLayout.NORTH);
	main.add(pan2,BorderLayout.CENTER);
	main.add(pan3,BorderLayout.SOUTH);
	main.setBounds(50,80,300,120);
	p1.add(main);
	getContentPane().add(p1,BorderLayout.CENTER);
}

public void register(){
	String user = tf1.getText();
	String pass = tf2.getText();
	String cpass = tf3.getText();
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(pass == null || pass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	if(cpass == null || cpass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Confirm Password must be enter");
		tf2.requestFocus();
		return;
	}
	if(!pass.equals(cpass)){
		JOptionPane.showMessageDialog(this,"Password and Confirm Password must be same");
		tf2.requestFocus();
		return;
	}
	try{
		String input[] = {user,pass,cpass};
		String msg = DBCon.register(input);
		if(msg.equals("pass")){
			setVisible(false);
			login.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this,"Error in registration");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

}