package cloud;
import org.apache.commons.codec.binary.Base64;
public class Enc {  
public static String encrypt(String ano) {  
	String anoni = null;  
	try {  
		byte[] encodedBytes = Base64.encodeBase64(ano.getBytes());
		anoni = new String(encodedBytes);
	} catch (Exception e) {  
		System.out.println(e.getMessage()); 
	}  
		return anoni;  
}  
} 