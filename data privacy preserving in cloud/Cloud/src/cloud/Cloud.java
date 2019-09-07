package cloud;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.net.Socket;
import java.net.ServerSocket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.net.InetAddress;
public class Cloud extends JFrame
{	
	ProcessThread thread;
	JPanel p1,p2;
	JLabel l1;
	JButton b1;
	JScrollPane jsp;
	JTextArea area;
	Font f1,f2;
	ServerSocket server;
	Socket socket;
	
public void start(){
	try{
			server = new ServerSocket(1111);
			area.append("Cloud Server Started\n\n");
			while(true){
				//accept() method: Listens for a connection to be made to this socket and accepts it.
				socket = server.accept();
				//area.append("Created sensors connected\n");
				thread=new ProcessThread(socket,area);
				thread.start();
			}
	}catch(Exception e){
				e.printStackTrace();
	}
}

public Cloud(){
	setTitle("Cloud Server");
	f1 = new Font("Castellar", 1, 24);
    p1 = new JPanel();
    l1 = new JLabel("<html><body><center><font size=6 color=#f5ea01>Cost-Effective Privacy Preserving of Intermediate Data in Cloud</font></center></body></html>");
	//l1.setFont(this.f1);
    //l1.setForeground(Color.white);
    p1.add(l1);
    p1.setBackground(Color.black);

    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(new BorderLayout());
    area = new JTextArea();
    area.setFont(f2);
    jsp = new JScrollPane(area);
    area.setEditable(false);
    p2.add(jsp);

	
	
    getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	
    addWindowListener(new WindowAdapter(){
            @Override
        public void windowClosing(WindowEvent we){
            try{
				if(socket != null){
					socket.close();
				}
             server.close();
            }catch(Exception e){
                //e.printStackTrace();
            }
        }
    });
}
public static void main(String a[])throws Exception
	{
		Cloud bs=new Cloud();
		bs.setVisible(true);
		bs.setSize(900,400);
		new ServerThread(bs);
	}

}