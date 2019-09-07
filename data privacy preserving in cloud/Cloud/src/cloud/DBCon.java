package cloud;
//Connection: Interface to contact the database.
import java.sql.Connection;
//DriverManager: Manage a list of database drivers. 
import java.sql.DriverManager;
//PreparedStatement: to submit SQL statements to database.
import java.sql.PreparedStatement;
//ResultSet: Objects to hold data retrieved form DB after execution.
import java.sql.ResultSet;
//Statement: to submit SQL statements to database.
import java.sql.Statement;
import java.util.ArrayList;
public class DBCon{
    private static Connection con;
	
public static Connection getCon()throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/pleakage_cloud","root","root");
     return con;
}
public static void updatePL(PQueue queue,double hvalue)throws Exception{
	con = getCon();
	PreparedStatement stat=con.prepareStatement("update intermediate_dataset set di_status=?,hvalue=? where search_string=?");
	stat.setString(1,"encrypt");
	stat.setDouble(2,hvalue);
	stat.setString(3,queue.getDocName());
	stat.executeUpdate();
}
public static void updateEncryptionCost(PQueue queue)throws Exception{
	con = getCon();
	PreparedStatement stat=con.prepareStatement("update intermediate_dataset set hvalue=? where search_string=?");
	stat.setDouble(1,queue.getHValue());
	stat.setString(2,queue.getDocName());
	stat.executeUpdate();
}
public static String search(String search)throws Exception{
	String msg = "none";
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select di_status,intermediate_set from intermediate_dataset where search_string='"+search+"'");
    while(rs.next()){
		String status = rs.getString(1);
        byte b[] = rs.getBytes(2);
		if(status.equals("encrypt"))
			msg = encrypt(new String(b));
		else
			msg = new String(b);
    }
	return msg;
}
public static String encrypt(String data)throws Exception{
	StringBuilder sb = new StringBuilder();
	String s1[] = data.split("\n");
	for(int i=0;i<s1.length;i++){
		String arr[] = s1[i].split(",");
		sb.append(arr[0]+","+Enc.encrypt(arr[1])+","+arr[2]+","+arr[3]+System.getProperty("line.separator"));
	}
	return sb.toString();
}
public static void save(String search,String intermediate)throws Exception{
    boolean flag=false;
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select search_string from intermediate_dataset where search_string='"+search+"'");
    if(rs.next()){
        flag=true;
		PreparedStatement stat=con.prepareStatement("update intermediate_dataset set access_frequency=access_frequency+1 where search_string=?");
		stat.setString(1,search);
		stat.executeUpdate();
    }
    if(!flag){
		PreparedStatement stat=con.prepareStatement("insert into intermediate_dataset values(?,?,?,?,?)");
		stat.setString(1,search);
		stat.setBytes(2,intermediate.getBytes());
		stat.setInt(3,1);
		stat.setString(4,"no");
		stat.setDouble(5,0);
		stat.executeUpdate();
    }
}
}