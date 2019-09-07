package pleakage;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class Access extends JFrame{
	Font f1;
	JPanel p1;
	JTable table;
	JScrollPane jsp;
	JPanel p2;
	JLabel l1;
	JTextField tf1;
	JButton b1;
	String columns[]={"Age","Occupation","Gender","Address"};
	DefaultTableModel dtm;
public Access(){
	super("Access Dataset");
	p1 = new JPanel();
	p1.setLayout(new BorderLayout()); 
	f1 = new Font("Courier New",Font.BOLD,14);
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	table.setFont(f1);
	table.getTableHeader().setFont(f1);
	dtm.addColumn(columns[0]);
	dtm.addColumn(columns[1]);
	dtm.addColumn(columns[2]);
	dtm.addColumn(columns[3]);
	jsp = new JScrollPane(table);
	p1.add(jsp,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.CENTER);

	p2 = new JPanel();
	l1 = new JLabel("Search");
	l1.setFont(f1);
	p2.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	p2.add(tf1);

	b1 = new JButton("Submit");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			access();
		}
	});
	getContentPane().add(p2,BorderLayout.NORTH);
}
public void access(){
	try{
		clearTable();
		String s1 = tf1.getText();
		if(s1 == null || s1.trim().length() <= 0){
			JOptionPane.showMessageDialog(this,"Enter no of nodes");
			tf1.requestFocus();
			return;
		}
		Socket socket = new Socket("localhost",1111);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Object req[] = {"search",s1};
		out.writeObject(req);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Object res[] = (Object[])in.readObject();
		String response = (String)res[0];
		if(response.equals("response")){
			String result = (String)res[1];
			if(!result.equals("No match found")){
				String row[] = result.split("\n");
				for(int i=0;i<row.length;i++){
					String rows[] = row[i].split(",");
					dtm.addRow(rows);
				}
			}else{
				JOptionPane.showMessageDialog(this,result);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void clearTable(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
}