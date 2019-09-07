package pleakage;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Upload extends JFrame{
	JPanel p1,p2;
	Font f1;
	JScrollPane jsp;
	JButton b1,b2,b3;
	JTable table;
	DefaultTableModel dtm;
	String columns[]={"Age","Occupation","Gender","Address"};
	File file;
	ArrayList<String[]> general = new ArrayList<String[]>();
public Upload(File file){
	super("View Dataset Form");
	this.file = file;
	f1 = new Font("Courier New",Font.BOLD,14);
	p1 = new JPanel();
	p1.setBackground(Color.white);
	p1.setLayout(new BorderLayout());
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
	
	
	p2 = new JPanel();
	p2.setBackground(Color.white);

	b2 = new JButton("Generalize Algorithm");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			generalizedAlgorithm();
		}
	});

	b1 = new JButton("Load To Cloud");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try{
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<table.getRowCount();i++){
					String age = table.getValueAt(i,0).toString();
					String occ = table.getValueAt(i,1).toString();
					String gender = table.getValueAt(i,2).toString();
					String address = table.getValueAt(i,3).toString();
					sb.append(age+","+occ+","+gender+","+address+System.getProperty("line.separator"));
				}
				Socket socket = new Socket("localhost",1111);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				Object req[] = {"save",sb.toString().getBytes()};
				out.writeObject(req);
				out.flush();
				JOptionPane.showMessageDialog(Upload.this,"Dataset uploaded to cloud");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
	
	
	b3 = new JButton("Clear");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			clearTable();
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.SOUTH);
}
public void generalizedAlgorithm(){
	int start_split_point[] = {20,30,40,50,60};
	int end_split_point[] = {30,40,50,60,70};
	for(int i=0;i<start_split_point.length;i++){
		for(int j=0;j<table.getRowCount();j++){
			int age1 = Integer.parseInt(table.getValueAt(j,0).toString().trim());
			if(age1 >= start_split_point[i] && age1 <= end_split_point[i]){
				String occ = table.getValueAt(j,1).toString().trim();
				String gender = table.getValueAt(j,2).toString().trim();
				String address = table.getValueAt(j,3).toString().trim();
				String row[]={"["+start_split_point[i]+"-"+end_split_point[i]+"]",occ,gender,address};
				general.add(row);
			}
		}
	}
	clearTable();
	for(int i=0;i<general.size();i++){
		String s[] = general.get(i);
		dtm.addRow(s);
	}
}
public void clearTable(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public void readDataset(){
	try{
		clearTable();
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while((line = br.readLine())!=null){
			if(line.indexOf("?") == -1){
				String data[] = line.split(",");
				Object row[] = {data[0],data[6],data[9],data[13]};
				dtm.addRow(row);
			}
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}