package rmiserver;

import java.util.UUID;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.HashMap;

import javax.swing.JOptionPane;

import rmiinterface.Part;
import rmiinterface.PartInterface;

public class PartRepository extends UnicastRemoteObject implements PartInterface{
	
	private static final long serialVersionUID = 1L;
    private List<Part> partList;
    
    //Construtor com uma lista de Peças já inicializada
    protected PartRepository(List<Part> list) throws RemoteException {
        super();
        this.partList = list;
    }
    
    //Encontra a peça na lista de peças do servidor a partir do ID
    @Override
    public Part findPart(Part part) throws RemoteException {
        Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        return partList.stream().filter(predicate).findFirst().get();
    }

    //Retorna a lista com todas as peças para o cliente
    //Cliente então exibe a lista
    @Override
    public List<Part> allParts() throws RemoteException {
        return partList;
    }
    
    //Encontra o objeto part na lista de peças do servidor
    //Busca a lista de sub-peças desse part e envia para o cliente
    @Override
    public HashMap<Part,Integer> allSubParts(Part part) throws RemoteException {
    	
    	Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        Part retrieve = partList.stream().filter(predicate).findFirst().get();
    	
    	return retrieve.getSubList();        
    }          
    
    //Adiciona uma part na lista de peças do servidor
    //Gera uma id única
    //Define o tipo a partir da lista de sub-peças recebida
    @Override
    public boolean addPart(String name, String desc, HashMap<Part, Integer> subTemp) throws RemoteException  {
    	
    	String addID = UUID.randomUUID().toString();
    	
    	boolean resposta = true;
    	boolean subExist = subTemp.isEmpty();
    	
    	String type;
    	
    	if (subExist) {
    		type = "Primitiva";
    	}
    	else {
    		type = "Composta";    
    	}
    	
    	Part Insert = new Part(name, desc, addID, type);
    	resposta = this.partList.add(Insert);
    	
    	createSubList(Insert, subTemp);
		return resposta;
    }
     
    //É utilizado no método addPart para criar a lista de sub-peças de uma part nova
    @Override
    public boolean createSubList(Part part, HashMap<Part, Integer> subTemp) throws RemoteException {
    	
    	Predicate<Part> predicate = x -> x.getId().equals(part.getId());
        Part insert = partList.stream().filter(predicate).findFirst().get();
    	
    	insert.addSubList(subTemp);    	
    	return true;
    }       
    
    //Inicializa a lista de parts do servidor
    private static List<Part> initializeList() {
        List<Part> list = new ArrayList<>();                       
        return list;
    }

    public static void main(String[] args) throws RemoteException {
    	
    	boolean dotask;
		do {
			
			//Opçoes para criar e exibir servidores
			String[] options = {"Show All Servers", "Create Server", "Exit"};
			
		    int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
		            JOptionPane.DEFAULT_OPTION,
		            JOptionPane.INFORMATION_MESSAGE,
		            null, options, options[0]);
    	
		    switch (choice) {
		    	case 0:
		    		
		    		final String localhost = "localhost";
		    	    int port = 1099;
		    		Registry registry = LocateRegistry.getRegistry(localhost, port);
		            final String[] boundNames = registry.list();
		    		JOptionPane.showMessageDialog(null, boundNames);
		    		break;
		    		
		    	case 1:
		    		String name = JOptionPane.showInputDialog("Type server name:");	
		    		name = name.replaceAll("\\s+",""); //retira espaços no nome para evitar problemas
		    		boolean teste = false;
		    		
		    		if (name != null && name.length() > 0) {
		    			
		    			port = 1099;
		    			registry = LocateRegistry.getRegistry("localhost", port);
			            String[] names = registry.list();
			            
			            for (final String servers : names)
			            {
			            	//Condição para verificar servers com mesmo nome
			            	if(servers.equals(name)) teste = true;
			            }
			            
			            if(!teste){	
			            	//Tentativa de iniciar um server
			    			try {    
			    				name = name.replaceAll("\\s+","");
					            Naming.rebind("//localhost/" + name, new PartRepository(initializeList()));      
					            System.err.println("Server ready");
					        } catch (Exception e) {
					            System.err.println("Server exception: " + e.getMessage());
					        }
				    		JOptionPane.showMessageDialog(null, "Server created!");
			            }
			            else{			            	
			            	JOptionPane.showMessageDialog(null, "There is already a server named: " + name);
			            }
			    		break;
		    		}
		    		
		    		else {
		    			if (name == null) {		    				
			    			break;
		    			}
		    			else {
		    				JOptionPane.showMessageDialog(null, "Name can't be empty.");
		    				break;
		    			}			    		
		    		}
		    		
		    	default:
		            //System.exit(0);
		            break;	  	
		    }		    	        
	        
		    dotask = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
	            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
		} while (dotask);
    }
	
}
