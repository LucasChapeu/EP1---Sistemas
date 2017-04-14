package rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import rmiinterface.Part;
import rmiinterface.PartInterface;

public class Client {
	
	private static PartInterface look_up;
	
	public static void main(String[] args) throws
    MalformedURLException, RemoteException, NotBoundException {

		look_up = (PartInterface) Naming.lookup("//localhost/ServerTest");
		
		boolean findmore;
		do {
		
		    String[] options = {"Show All", "Find a Part", "Add Part","Exit"};
		
		    int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
		            JOptionPane.DEFAULT_OPTION,
		            JOptionPane.INFORMATION_MESSAGE,
		            null, options, options[0]);
		
		    switch (choice) {
		
		        case 0:
		            List<Part> list = look_up.allParts();
		            StringBuilder message = new StringBuilder();
		            list.forEach(x -> {
		                message.append(x.toString() + "\n");
		            });
		            JOptionPane.showMessageDialog(null, new String(message));
		            break;
		        case 1:
		            String id = JOptionPane.showInputDialog("Type the ID of the Part you want to find.");
		            try {
		                Part response = look_up.findPart(new Part(id));
		                JOptionPane.showMessageDialog(null, "Name: " +
		                                response.getName() + "\n" +
		                                "Type: " + response.getType() + "\n" +
		                                response.getDesc(),
		                        response.getId(), JOptionPane.INFORMATION_MESSAGE);
		            } catch (NoSuchElementException ex) {
		                JOptionPane.showMessageDialog(null, "Part Not found");
		            }
		            break;
		        case 2:
		        	String name = JOptionPane.showInputDialog("Type the name.");
		        	String desc = JOptionPane.showInputDialog("Type the desc.");
		        	String ident = JOptionPane.showInputDialog("Type the id.");
		        	String type = JOptionPane.showInputDialog("Type the type.");
		        	
		        	boolean add = look_up.addPart(name, desc, ident, type);
		        	
		        	if (add == true) JOptionPane.showMessageDialog(null, "Part added!");
		        	else JOptionPane.showMessageDialog(null, "Part not added.");
		        	
		            break;
		        default:
		            System.exit(0);
		            break;
		
		    }
		    findmore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
		            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		} while (findmore);
	}
}
