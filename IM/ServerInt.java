import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInt extends Remote {
  String registerUserOnServer(IMClient newClient) throws RemoteException;
  public  String getUsers() throws RemoteException;
  String sendMessage(String userTo, String message, String userFrom) throws RemoteException;
}
