import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Capitalize extends Remote {
    String capitalize(String text) throws RemoteException;
    String prettyPrint() throws RemoteException;  
    //String registerNewUser(String input) throws RemoteException;  
    String sendMessage(String userTo, String message) throws RemoteException;
    void sendMessageToClient(String message) throws RemoteException;
    String registerUserOnServer(String input, CapClient newClient) throws RemoteException;
}