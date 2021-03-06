import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CapImpl implements Capitalize {
  
  static String[] usernames = new String[10];
  static CapClient[] clients = new CapClient[10];
  static int numUsers = 0;
  static int numClients = 0;
  private static String user = "";
  
  public CapImpl() { }
  
  public String capitalize(String text) {
    
    usernames[numUsers] = text;
    numUsers++;
    
    return text.toUpperCase();
  }
  
  public String prettyPrint(){
    //print out the usernames
    String s = "Index of users: \n";
    for (int i = 0; i < numUsers; i++){
      s += usernames[i] + "\n";
    }
    return s;
  }
  
  public static boolean checkIfUserExists(String user){
    for(int i = 0; i < numUsers; i++){
      if(usernames[i].equals(user)){
        return true;
      }
    }
    return false;
  }
  public String registerUserOnServer(String input, CapClient newClient){
    
    if(!checkIfUserExists(input)){
      
      usernames[numUsers] = input;
      clients[numClients] = newClient;
      
      System.out.println("Username array: " + usernames[numUsers]);
      numUsers++;
      numClients++;
      user = input;
      
      System.out.println("Number of users is now " + numUsers);
      System.out.println("New User " + input + " has been added");
      
      try{
       // CapImpl obj = new CapImpl();
        //Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
        //Registry registry = LocateRegistry.getRegistry();
        //registry.bind(input, stub);
        
      } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
      }
    }else{
      System.out.println("User " + input + " has already been registered.");
    }
    return input;
  }
  
  public void sendMessageToClient(String message){
    System.out.println(message);
  }
  
  public String sendMessage(String userTo, String message){
    try{
      if(checkIfUserExists(userTo)){
        
        //CapImpl obj = new CapImpl();
        //Capitalize newStub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
        Registry registry = LocateRegistry.getRegistry();
        //registry.bind(userTo, newStub);
        Capitalize newStub = (Capitalize) registry.lookup(userTo);
        //System.out.println("new stub " + newStub.toString());
        //System.out.println("old stub " + stub.toString());
       
        String messageText = user +  ": " + message;
        CapClient receivingClient = getReceivingClient(user);
        
        receivingClient.receiveMessage(messageText);
        //newStub.sendMessageToClient(messageText);
      }else{
        System.out.println("User does not exist.");
      }
    }catch(Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
    return message;
  }
  public static CapClient getReceivingClient(String userToFind){
    if(checkIfUserExists(userToFind)){
      for(int i = 0; i < numUsers; i++){
        if(usernames[i].equals(user)){
          return clients[i];
        }
      }
    }else{
      return null;
    }
     return null;
  }
  public static void main(String args[]) {
    
    try {
      CapImpl obj = new CapImpl();
      Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
      
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