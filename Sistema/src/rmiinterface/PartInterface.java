package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartInterface extends Remote{
	
	Part findPart(Part x) throws RemoteException;
	List<Part> allParts() throws RemoteException;
	boolean addPart(String name, String desc, String id, String type ) throws RemoteException;
	boolean eraseParts() throws RemoteException;
	List<Part> allSubParts(Part part) throws RemoteException;
	List<Integer> allSubPartsQnt(Part part) throws RemoteException;
	boolean addSubPart(Part part, Part sub) throws RemoteException;
	boolean addSubPartQnt(Part part, Integer qnt) throws RemoteException;
	
}
