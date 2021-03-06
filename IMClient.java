import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMClient extends Remote {
  
   public String getName() throws RemoteException;
   public String getMessage() throws RemoteException;
   public void receiveMessage(String messageText) throws RemoteException;
   
}