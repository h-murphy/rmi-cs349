import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServerImpl implements ServerInt {
  static IMClient[] clients;
  int numClients;
    
  public ServerImpl(){
    numClients = 0;
    clients = new IMClient[10];
    
  }
  
  public String getUsers() throws RemoteException{
    String s = "***Users***\n";
    for(int i = 0; i < numClients; i++){
      s += clients[i].getUserName() + "\n";
    }
    return s;
  }
  
  private boolean checkIfUserExists(String userName) throws RemoteException{
    for(int i = 0; i < numClients; i++){
      if(clients[i].getUserName().equals(userName)){
        return true;
      }
    }
    return false;
  }
  
  private int getClientIndex(String user){
    try{
    for(int i = 0; i < numClients; i++){
      if(clients[i].getUserName().equals(user)){
        return i;
      }
    }
    }catch(Exception e){}
    return -1;
  }
  public String sendMessage(String userTo, String message, String userFrom) throws RemoteException{
    if (checkIfUserExists(userTo)){
      String messageText = "["+ userFrom + "] " + message;
      int receiverIndex = getClientIndex(userTo);
      clients[receiverIndex].receiveMessage(messageText);
      return "sent";
    }
    return "failure to send, specified user does not exist";
  }
  
  public String registerUserOnServer(IMClient newClient) throws RemoteException{
    System.out.println("Registering new User (printing from server");
    if(!checkIfUserExists(newClient.getUserName())){
      System.out.println("User does not already exist");

      clients[numClients] = newClient;
      System.out.println("added to array");

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