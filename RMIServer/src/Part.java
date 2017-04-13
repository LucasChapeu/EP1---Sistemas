import java.rmi.*;
import java.rmi.server.*;

public class Part extends UnicastRemoteObject implements PartInterface {
	 
	public Part () throws RemoteException {   }
	 
    public int add(int a, int b) throws RemoteException {
   	 	int result=a+b;
   		return result;
    }
}
