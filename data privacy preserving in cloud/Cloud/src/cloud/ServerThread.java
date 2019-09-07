package cloud;
public class ServerThread extends Thread
{
	Cloud cloud;
public ServerThread(Cloud cloud){
	this.cloud=cloud;
	start();
}
public void run(){
	cloud.start();
}
}