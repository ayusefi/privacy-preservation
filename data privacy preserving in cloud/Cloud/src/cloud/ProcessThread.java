package cloud;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileOutputStream;
import java.io.FileReader;
public class ProcessThread extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
	
public ProcessThread(Socket soc,JTextArea area){
    socket=soc;
	this.area=area;
    try{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
    try{
      
            Object input[]=(Object[])in.readObject();
            String type=(String)input[0];
			if(type.equals("save")){
				byte b[] = (byte[])input[1];
				FileOutputStream fout = new FileOutputStream("received/dataset.txt");
				fout.write(b,0,b.length);
				fout.flush();
				fout.close();
				area.append("\n");
				area.append("Dataset received and saved in received folder \n");
			}
			if(type.equals("runalgo")){
				PPAlgorithm.step1();
				area.append("Dataset privacy created \n");
			}
			if(type.equals("search")){
				String search = (String)input[1];
				String dbsearch = DBCon.search(search);
				StringBuilder sb = new StringBuilder();
				if(dbsearch.equals("none")){
					BufferedReader br = new BufferedReader(new FileReader("received/dataset.txt"));
					String line = null;
					while((line = br.readLine())!=null){
						String arr[] = line.split(",");
						for(int i=0;i<arr.length;i++){
							if(search.equals(arr[i]))
								sb.append(line+System.getProperty("line.separator"));
						}
					}
					br.close();
				}
				if(!dbsearch.equals("none")){
					sb.append(dbsearch);
				}
				if(sb.length() > 0){
					DBCon.save(search,sb.toString());
				}else{
					sb.append("No match found");
				}
				Object req[]={"response",sb.toString()};
				out.writeObject(req);
				out.flush();
				area.append("Response sent to user\n");
			}
           
            
    }catch(Exception e){
        e.printStackTrace();
    }
}

}