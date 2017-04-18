package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartInterface extends Remote{
	
	Part findPart(Part x) throws RemoteException;
	List<Part> allParts() throws RemoteException;
	boolean addPart(String name, String desc, String type) throws RemoteException;
	boolean eraseParts() throws RemoteException;
	
}
