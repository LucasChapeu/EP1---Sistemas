import java.rmi.*;

public class PartClient {
	
	public static void main (String[] args) {
		PartInterface hello;
		try {
			hello = (PartInterface)Naming.lookup("rmi://localhost:1234/ABC"); 
			int result = hello.add(9,10);
			System.out.println("Result is :"+result);
 
			}catch (Exception e) {
				System.out.println("PartClient exception: " + e);
				}
		}
}
