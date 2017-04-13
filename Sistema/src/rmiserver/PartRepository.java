package rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
    public boolean addPart(String name, String desc, String id, String type) {
    	this.partList.add(new Part(name, desc, id, type));
		return true;
    }

    private static List<Part> initializeList() {
        List<Part> list = new ArrayList<>();
        list.add(new Part("Motor", "Peça importante para funcionamento do carro.", "1", "Composta"));
        list.add(new Part("Freios", "Peça para parar o carro.", "2", "Composta"));
        list.add(new Part("Lanternas", "Peça para iluminar o ambiente.", "3", "Primitiva"));
        list.add(new Part("Pneu", "Peça para movimentar o carro.", "4", "Primitiva"));
        list.add(new Part("Maçaneta", "Peça importante para entrar no carro.", "5", "Primitiva"));
        return list;
    }

    public static void main(String[] args) {
        try {
            Naming.rebind("//localhost/ServerTest", new PartRepository(initializeList()));
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
	
}
