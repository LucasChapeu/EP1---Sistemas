package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface PartInterface extends Remote{
	
	Part findPart(Part x) throws RemoteException;
	List<Part> allParts() throws RemoteException;
	boolean addPart(String name, String desc, HashMap<Part, Integer> subTemp) throws RemoteException;	
	HashMap<Part, Integer> allSubParts(Part part) throws RemoteException;	
	boolean createSubList(Part part, HashMap<Part, Integer> subTemp) throws RemoteException;	
	
}
