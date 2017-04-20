package rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rmiinterface.Part;
import rmiinterface.PartInterface;
//import rmiserver.PartRepository;

public class Client {
	
	private static PartInterface look_up;
	
	public static void main(String[] args) throws
    MalformedURLException, RemoteException, NotBoundException {				
		
		Part hold = new Part("dummy", "dummy", "dummy", "dummy");
		
		boolean dotask = true;
		do {			
			
			String[] optionsServer = {"Show All Servers", "Find Server", "Exit"};
			
		    int choiceServer = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
		            JOptionPane.DEFAULT_OPTION,
		            JOptionPane.INFORMATION_MESSAGE,
		            null, optionsServer, optionsServer[0]);
			
		    switch (choiceServer) {
	    	case 0:
	    		
	    		final String localhost = "localhost";
	    	    int port = 1099;
	    		final Registry registry = LocateRegistry.getRegistry(localhost, port);
	            final String[] boundNames = registry.list();
	    		JOptionPane.showMessageDialog(null, boundNames);
	    		
	    		break;
	    	case 1:	    		
	    		
	    		String servername = JOptionPane.showInputDialog("Type server name:");
				look_up = (PartInterface) Naming.lookup("//localhost/" + servername);
				if (look_up != null) JOptionPane.showMessageDialog(null, "Connected to " + servername + ".");
				
				boolean findmore;
				do {
				
				    String[] options = {"Show All", "Find a Part", "Add Part","Exit"};
				
				    int choice = JOptionPane.showOptionDialog(null, "Choose an action", servername,
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
				            				            
				            JTextArea ta = new JTextArea(10, 10);
			                ta.setText(new String(message));
			                ta.setWrapStyleWord(true);
			                ta.setLineWrap(true);
			                ta.setCaretPosition(0);
			                ta.setEditable(false);
			                
				            JOptionPane.showMessageDialog(null, new JScrollPane(ta));
				           
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
				                
				                
				                //Opções após busca de uma part				                				                
				                
				                String[] optionsPart = {"Hold Part", "Show all subparts", "Add subpart", "Exit"};
				    			
				    		    int choicePart = JOptionPane.showOptionDialog(null, "Choose an action for this part", response.getName(),
				    		            JOptionPane.DEFAULT_OPTION,
				    		            JOptionPane.INFORMATION_MESSAGE,
				    		            null, optionsPart, optionsPart[0]);
				    			
				    		    switch (choicePart) {
				    		    	case 0:
				    		    		hold = response;
				    		    		JOptionPane.showMessageDialog(null, "Peça HOLD: \n" + "Name: " +
				                                hold.getName() + "\n" +
				                                "Type: " + hold.getType() + "\n" +
				                                hold.getDesc(),
				                        hold.getId(), JOptionPane.INFORMATION_MESSAGE);
				    		    		break;
				    		    		
				    		    	case 1:				    		    						    		    	
				    		    		
				    		    		if(response != null){
				    		    			Map<Part, Integer> listSub = look_up.allSubParts(response);
					    		    		StringBuilder messageSub = new StringBuilder();
								            
								            listSub.forEach((x,y) -> {
								                messageSub.append("Nome: " + x.getName().toString() + "Quantidade: " + y + "\n");
								            });
								            JOptionPane.showMessageDialog(null, new String(messageSub));
				    		    		}
							           
							            break;
							            
				    		    	case 2:
				    		    		if (hold.getName() == "dummy") {
				    		    			JOptionPane.showMessageDialog(null, "Must hold a piece first!");
				    		    			break;
				    		    		}
				    		    		else {
				    		    			System.out.println("1");
				    		    			int qnt = Integer.parseInt(JOptionPane.showInputDialog("Type the number of "+ hold.getName() + ":"));
				    		    			System.out.println("2");
				    		    			boolean addSub = look_up.addSubPart(response, hold, qnt);
				    		    			System.out.println("5");			    		    			
								        	if (addSub == true) JOptionPane.showMessageDialog(null, "Subpart added!");
								        	else JOptionPane.showMessageDialog(null, "Subpart not added.");
								        	break;
				    		    		}				    		    		
				    		    		
				    		    	default:
				    		    		break;
				    		    }				                
				            
				    		    
				    		    
				    		    
				            } catch (NoSuchElementException ex) {
				                JOptionPane.showMessageDialog(null, "Part Not found");
				            }
				            break;
				        case 2:
				        	
				        	String name = JOptionPane.showInputDialog("Type the name:");			        			        			        
				        	String desc = JOptionPane.showInputDialog("Type the desc:");
				        	String idAdd = JOptionPane.showInputDialog("Type the id:");
				        	
				        	String type = "";		        	
				        	String[] values = {"Composta", "Primitiva"};
				        	Object selected = JOptionPane.showInputDialog(null, "Choose type:", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
				        	if ( selected != null ){ 
				        	    type = selected.toString();		        	    
				        	}else{
				        	    System.out.println("Part not created.");
				        	}
				        	
				        	boolean add = look_up.addPart(name, desc, idAdd, type);
				        	
				        	if (add == true) JOptionPane.showMessageDialog(null, "Part added!");
				        	else JOptionPane.showMessageDialog(null, "Part not added.");
				        	
				            break;
				        default:
				            //System.exit(0);
				            break;
				
				    }
				    findmore = (JOptionPane.showConfirmDialog(null, "Do you want to exit this server?", "Exit",
				            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
				} while (findmore);
	    		
	    	default:
	            //System.exit(0);
	            break;	  	
		    }		  		    
			
			dotask = (JOptionPane.showConfirmDialog(null, "Do you want to stop looking for servers?", "Yes",
		            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		} while (dotask);
		
	}
}
