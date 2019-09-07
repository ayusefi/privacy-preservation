package cloud;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
public class PPAlgorithm
{
	static double size,frequency;
	static String pleakage = "3";
	static double sigma = 1000000.0;
	static double hvalue = 0;
	static double ccost = 0;
	static String ed;
	static ArrayList<PQueue> queue = new ArrayList<PQueue>();
public static void step1(){
	try{
		queue.clear();
		Connection con = DBCon.getCon();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from intermediate_dataset");
		while(rs.next()){
			String docname = rs.getString(1);
			byte dataset[] = rs.getBytes(2);
			frequency = rs.getDouble(3);
			ed = rs.getString(4);
			hvalue = rs.getDouble(5);
			size = (double)dataset.length;
			if(ed.equals("encrypt"))
				ccost = 80;
			else
				ccost = 0;
			hvalue = hvalue+ccost;
			PQueue pq = new PQueue();
			pq.setDocName(docname);
			pq.setFrequency(frequency);
			pq.setED(ed);
			pq.setSize(size);
			pq.setHValue(hvalue);
			queue.add(pq);
		}
		rs.close();stmt.close();con.close();
		Collections.sort(queue,new PQueue());
		step2();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void step2(){
	try{
		for(int i=queue.size()-1;i>=0;i--){
			PQueue pq = queue.get(i);
			if(pq.equals("encrypt")){
				step3(pq);
			}else{
				size = pq.getSize();
				frequency = pq.getFrequency();
				ccost = 80.0;
				double pl = size*frequency*ccost;
				System.out.println(pq.getDocName()+" "+pl+" pl");
				if(pl > sigma){
					DBCon.updatePL(pq,pl);
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void step3(PQueue pq){
	try{
		pq.setHValue(pq.getHValue()+80);
		DBCon.updateEncryptionCost(pq);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}