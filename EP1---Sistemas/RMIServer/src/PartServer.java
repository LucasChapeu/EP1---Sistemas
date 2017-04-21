import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*; 

public class PartServer {
	public static void main (String[] argv) {
		   try {
			   Part Hello = new Part();	
			   LocateRegistry.createRegistry(1234);
			   Naming.rebind("rmi://localhost:1234/ABC", Hello);

			   System.out.println("Part Server is ready.");
			   }catch (Exception e) {
				   System.out.println("Part Server failed: " + e);
				}
		   }
}
