import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl implements ServerInt {
  static HashMap<String, IMClient> clientMap;
  int numClients;
    
  public ServerImpl(){
    numClients = 0;
    clientMap = new HashMap<String, IMClient>();
    
  }
  
  public String getUsers() throws RemoteException{
    String s = "";
    
    for (String key : clientMap.keySet()) {
      s += key + "\n";
}
    return s;
  }
  
  private boolean checkIfUserExists(String userName) throws RemoteException{
    return clientMap.containsKey(userName);
  }

  public String sendMessage(String userTo, String message, String userFrom) throws RemoteException{
    String messageText = "["+ userFrom + "] " + message;
    clientMap.get(userTo).receiveMessage(messageText);
 
    return "sent";
  }
 
  public String registerUserOnServer(IMClient newClient) throws RemoteException{
    if(!checkIfUserExists(newClient.getUserName())){
      
      clientMap.put(newClient.getUserName(), newClient);

      numClients++;
      
      System.out.println("Number of users is now " + numClients);
      System.out.println("New User " + newClient.getUserName() + " has been added");
      

    }else{
      System.out.println("User " + newClient.getUserName() + " has already been registered.");
    }
    return newClient.getUserName();
  }
  
  public static void main(String[] args){
    try {
            ServerImpl obj = new ServerImpl();
            ServerInt stub = (ServerInt) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("InitialConnection", stub);

            System.err.println("Server ready");
            
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
  }
}