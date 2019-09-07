package cloud;
import java.util.Comparator;
public class PQueue implements Comparator
{
	String docname;
	double frequency;
	String ed;
	double size;
	double hvalue;
public void setDocName(String docname){
	this.docname = docname;
}
public String getDocName(){
	return docname;
}
public void setFrequency(double frequency){
	this.frequency = frequency;
}
public double getFrequency(){
	return frequency;
}
public void setED(String ed){
	this.ed = ed;
}
public String getED(){
	return ed;
}
public void setSize(double size){
	this.size = size;
}
public double getSize(){
	return size;
}
public void setHValue(double hvalue){
	this.hvalue = hvalue;
}
public double getHValue(){
	return hvalue;
}
public int compare(Object sr1, Object sr2){
	PQueue p1 =(PQueue)sr1;
	PQueue p2 =(PQueue)sr2;
	double s1 = p1.getHValue();
    double s2 = p2.getHValue();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}