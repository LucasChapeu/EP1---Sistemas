package rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import rmiinterface.Part;
import rmiinterface.PartInterface;

public class Client {
	
	private static PartInterface look_up;
	
	public static void main(String[] args) throws
    MalformedURLException, RemoteException, NotBoundException {				
		
		//Sublista temporaria do cliente
		HashMap<Part, Integer> subList = new HashMap<Part, Integer>();
		
		boolean dotask = true;
		do {			
			
			String[] optionsServer = {"Show All Servers", "Find Server", "Exit"};
			
		    int choiceServer = JOptionPane.showOptionDialog(null, "Choose an action", "Server options",
		            JOptionPane.DEFAULT_OPTION,
		            JOptionPane.INFORMATION_MESSAGE,
		            null, optionsServer, optionsServer[0]);
			
		    switch (choiceServer) {
	    	case 0:
	    		
	    		final String localhost = "localhost";
	    	    int port = 1099;
	    		final Registry registry = LocateRegistry.getRegistry(localhost, port);
	            final String[] names = registry.list();
	    		JOptionPane.showMessageDialog(null, names);
	    		
	    		break;
	    	case 1:	    		
	    		
	    		String servername = JOptionPane.showInputDialog("Type server name:");
	    		
	    		
	    		if (servername != null && servername.length() > 0) {
	    			servername = servername.replaceAll("\\s+","");
	    				    				
	    			try{
	    				look_up = (PartInterface) Naming.lookup("//localhost/" + servername);
	    			} catch (NotBoundException e) {
	    				JOptionPane.showMessageDialog(null,"There is no server named: " + servername);
	    	            //e.printStackTrace();
	    	        }
					
					if (look_up != null) JOptionPane.showMessageDialog(null, "Connected to " + servername + ".");					
					else break;
					
					boolean findmore;
					do {
					
					    String[] options = {"Show Parts", "Find Part", "Add Part", "Show SubList", "Clear SubList","Exit"};
					
					    int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Server: " + servername,
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
					            				            
					            JTextArea ta = new JTextArea(10, 50);
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
					                JOptionPane.showMessageDialog(null,
					                				"Server: " + servername + "\n" +
					                				"Id: " + response.getId() + "\n" +
					                				"Name: " + response.getName() + "\n" +
					                                "Type: " + response.getType() + "\n" +
					                                response.getDesc(),
					                        "Results", JOptionPane.INFORMATION_MESSAGE);
					                
					                
					                //Opções após busca de uma part				                				                
					                
					                String[] optionsPart = {"Add to Temporary SubList", "Show all subparts", "Exit"};
					    			
					    		    int choicePart = JOptionPane.showOptionDialog(null, "Choose an action for this part", "Part: " + response.getName(),
					    		            JOptionPane.DEFAULT_OPTION,
					    		            JOptionPane.INFORMATION_MESSAGE,
					    		            null, optionsPart, optionsPart[0]);
					    			
					    		    switch (choicePart) {
					    		    	case 0:
					    		    		String qntSub = JOptionPane.showInputDialog("Type the number of "+ response.getName() + ":");
					    		    		if (qntSub == null) break;
					    		    		
					    		    		if (qntSub.length() == 0) {
					    		    			JOptionPane.showMessageDialog(null, "Quantity can't be empty.");
					    		    			break;
					    		    		}
					    		    		
					    		    		else {
					    		    								    		    			
					    		    			if(qntSub.matches(".*\\d.*")){
					    		    				int qnt = Integer.parseInt(qntSub);
						    		    			if (qnt > 0) {
							    		    			boolean insertSub = (JOptionPane.showConfirmDialog(null, "Do you want to add " + qnt + 
								    		    				" " + response.getName() + " to your temporary sublist?", "Yes",
								    				            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
								    		    		
								    		    		if (!insertSub) {				    		    			
								    		    			subList.put(response, qnt);
								    		    			JOptionPane.showMessageDialog(null, "Subpart added!");
								    		    			break;
								    		    		}
								    		    		else {
								    		    			JOptionPane.showMessageDialog(null, "Subart not added.");
								    		    			break;
								    		    		}
							    		    		}
						    		    			else {
						    		    				JOptionPane.showMessageDialog(null, "Can't add zero.");
						    		    				break; 
						    		    			}
					    		    			}					    		    			
					    		    			else {
					    		    				JOptionPane.showMessageDialog(null, "You must use only numbers.");
					    		    				break;					    		    								    		    								    		    									    
					    		    			}
					    		    		}					    		    		
					    		    		
					    		    	case 1:				    		    						    		    					    		    		
					    		    		if(response != null){
					    		    			HashMap<Part, Integer> showSub = look_up.allSubParts(response);
						    		    		StringBuilder messageSub = new StringBuilder();								            
						    		    		showSub.forEach((x,y) -> messageSub.append("ID - " + x.getId() + " | Name - "+x.getName() + " | Quantity: "+ y + "\n"));					    		    							    		    		
									            JOptionPane.showMessageDialog(null, new String(messageSub));
					    		    		}								           
								            break;
								            				    		    
					    		    	default:
					    		    		break;
					    		    }				                				            				    		    
					    		    
					    		    
					            } catch (NoSuchElementException ex) {
					                JOptionPane.showMessageDialog(null, "Part Not found");
					            }
					            break;
					            
					        case 2:					        	
					        	String name = JOptionPane.showInputDialog("Type the name:");					        	
					        	if (name == null) break;
					        	
					        	String desc = JOptionPane.showInputDialog("Type the description:");				        						        	
					        	if (desc == null) break;
					        	
					        	if ((name != null && name.length() > 0) && (desc != null && desc.length() > 0)) {
					        		boolean add = look_up.addPart(name, desc, subList);
						        	
						        	if (add == true) JOptionPane.showMessageDialog(null, "Part added!");
						        	else JOptionPane.showMessageDialog(null, "Part not added.");
						        	
						            break;
					        	}
					        	
					        	else {					        							    		
					    			JOptionPane.showMessageDialog(null, "Name/Description can't be empty.");
					    			break;					    						 
					        	}
					        	
					            
					        case 3:
					        	StringBuilder messageSubTemp = new StringBuilder();
					            
		    		    		subList.forEach((x,y) -> messageSubTemp.append("ID - " + x.getId() + " | Name - "+x.getName() + " | Quantity - "+ y + "\n"));	    		    		
		    		    		
					            JOptionPane.showMessageDialog(null, new String(messageSubTemp));
					            break;
					        case 4:				     
					        	boolean clearSub = (JOptionPane.showConfirmDialog(null, "Do you want to clear "
					        			+ "your temporary sublist?", "Yes",
		    				            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		    		    		
		    		    		if (!clearSub) {				    		    			
		    		    			subList.clear();
		    		    			JOptionPane.showMessageDialog(null, "SubList erased.");
		    		    			break;
		    		    		}
		    		    		else {
		    		    			JOptionPane.showMessageDialog(null, "Subart not erased.");
		    		    			break;
		    		    		}				        					        				        	
					        default:
					            //System.exit(0);
					            break;
					
					    }
					    findmore = (JOptionPane.showConfirmDialog(null, "Do you want to exit this server?", "Exit",
					            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
					} while (findmore);
		    		
	    		}
	    		
	    		//Caso tenha apertado cancelar na busca de um servidor ou digitou nada.
	    		else {
	    			if (servername == null) {		    				
		    			break;
	    			}
	    			else {
	    				JOptionPane.showMessageDialog(null, "Server name can't be empty.");
	    				break;
	    			}
	    		}
	    						
	    	default:
	            //System.exit(0);
	            break;	  	
		    }		  		    
			
			dotask = (JOptionPane.showConfirmDialog(null, "Do you want to stop looking for servers?", "Exit",
		            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		} while (dotask);
		
	}
}
