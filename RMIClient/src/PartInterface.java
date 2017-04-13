import java.rmi.*;

public interface PartInterface extends Remote{
	public int add(int a,int b) throws RemoteException;
}
