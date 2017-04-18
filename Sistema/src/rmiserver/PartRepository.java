package rmiserver;
//import java.util.UUID;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import rmiinterface.Part;
import rmiinterface.PartInterface;

public class PartRepository extends UnicastRemoteObject implements PartInterface{
	
	private static final long serialVersionUID = 1L;
    private List<Part> partList;
    
    protected PartRepository(List<Part> list) throws RemoteException {
        super();
        this.partList = list;
    }
    
    @Override
    public Part findPart(Part part) throws RemoteException {
        Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        return partList.stream().filter(predicate).findFirst().get();
    }

    @Override
    public List<Part> allParts() throws RemoteException {
        return partList;
    }
    
    @Override
    public List<Part> allSubParts(Part part) throws RemoteException {
    	return part.getSubList();        
    }
    
    @Override
    public List<Integer> allSubPartsQnt(Part part) throws RemoteException {
    	return part.getSubListQnt();        
    }    
    
    //IMPLEMENTAR NA INTERFACE E TESTAR
    @Override
    public boolean eraseParts() throws RemoteException {
        this.partList.clear();
        if (partList.size() == 0) return true;
        else return false;        
    }   
    
    @Override
    public boolean addPart(String name, String desc, String id, String type) throws RemoteException  {
    	//String addID = UUID.randomUUID().toString();
    	
    	this.partList.add(new Part(name, desc, id, type));
		return true;
    }
    
    @Override
    public boolean addSubPart(Part part, Part sub) throws RemoteException {
    	
    	Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        Part insert = partList.stream().filter(predicate).findFirst().get();
    	
    	insert.addSubPart(sub);    	
    	return true;
    }
    
    @Override
    public boolean addSubPartQnt(Part part, Integer qnt) throws RemoteException {
    	
    	Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        Part insert = partList.stream().filter(predicate).findFirst().get();
    	
    	insert.addSubPartQnt(qnt);
    	return true;
    }
    
    private static List<Part> initializeList() {
        List<Part> list = new ArrayList<>();                       
        return list;
    }

    public static void main(String[] args) {
    	
    	boolean dotask;
		do {
			
			String[] options = {"Show All Servers", "Create Server", "Exit"};
			
		    int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
		            JOptionPane.DEFAULT_OPTION,
		            JOptionPane.INFORMATION_MESSAGE,
		            null, options, options[0]);
    	
		    switch (choice) {
		    	case 0:
		    		JOptionPane.showMessageDialog(null, "Pensar em uma maneira de armazenar servidores...");
		    		break;
		    	case 1:
		    		String name = JOptionPane.showInputDialog("Type server name:");
		    		try {    
			            Naming.rebind("//localhost/" + name, new PartRepository(initializeList()));      
			            System.err.println("Server ready");
			        } catch (Exception e) {
			            System.err.println("Server exception: " + e.getMessage());
			        }
		    		JOptionPane.showMessageDialog(null, "Server created!");
		    		
		    		break;
		    	default:
		            //System.exit(0);
		            break;	  	
		    }		    	        
	        
		    dotask = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
	            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		} while (dotask);
    }
	
}
