import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInt extends Remote {
  String registerUserOnServer(IMClient newClient) throws RemoteException;
  public  String getUsers() throws RemoteException;
  String sendMessage(String userTo, String message, String userFrom) throws RemoteException;
}
//public interface ServerInt extends Remote {
//    String capitalize(String text) throws RemoteException;
//    String prettyPrint() throws RemoteException;  
//    //String registerNewUser(String input) throws RemoteException;  
//    String sendMessage(String userTo, String message) throws RemoteException;
//    void sendMessageToClient(String message) throws RemoteException;
//    String registerUserOnServer(Client newClient) throws RemoteException;
//}