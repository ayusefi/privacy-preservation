package pleakage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class DBCon{
    private static Connection con;
	
public static Connection getCon()throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/pleakage","root","root");
     return con;
}

public static String register(String[] input)throws Exception{
    String msg="fail";
    boolean flag=false;
    boolean flag1=false;
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select user from newuser where user='"+input[0]+"'");
    if(rs.next()){
        flag=true;
        msg = "Username already exist";
    }
    stmt=con.createStatement();
    rs=stmt.executeQuery("select pass from newuser where pass='"+input[1]+"'");
    if(rs.next() && !flag){
        flag1=true;
        msg = "Password already exist";
    }
    if(!flag && !flag1){
    PreparedStatement stat=con.prepareStatement("insert into newuser values(?,?,?)");
    stat.setString(1,input[0]);
    stat.setString(2,input[1]);
    stat.setString(3,input[2]);
	int i=stat.executeUpdate();
    if(i > 0){
		msg = "pass";
	}
    }
    return msg;
}
public static String login(String input[])throws Exception{
    String msg="fail";
    con = getCon();
    System.out.println(input[0]);
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select user,pass from newuser where user='"+input[0]+"' && pass='"+input[1]+"'");
    if(rs.next()){
        msg = "pass";
    }
    System.out.println(msg);
    return msg;
}

}