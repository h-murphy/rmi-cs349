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
    String s = "";
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
    String messageText = "["+ userFrom + "] " + message;
    int receiverIndex = getClientIndex(userTo);
    clients[receiverIndex].receiveMessage(messageText);
    return "sent";
  }
  public String registerUserOnServer(IMClient newClient) throws RemoteException{
    System.out.println("Registering new User (printing from server");
    if(!checkIfUserExists(newClient.getUserName())){
      System.out.println("User does not already exist");
      //usernames[numUsers] = input;
      clients[numClients] = newClient;
      System.out.println("added to array");
      //System.out.println("Username array: " + usernames[numUsers]);
      //numUsers++;
      numClients++;
     // user = ;
      
      System.out.println("Number of users is now " + numClients);
      System.out.println("New User " + newClient.getUserName() + " has been added");
      
//      try{
//        // CapImpl obj = new CapImpl();
//        //Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
//        //Registry registry = LocateRegistry.getRegistry();
//        //registry.bind(input, stub);
//        
//      } catch (Exception e) {
//        System.err.println("Server exception: " + e.toString());
//        e.printStackTrace();
//      }
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



//import java.rmi.registry.Registry;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//
//
//public class ServerImpl implements ServerInt {
//  
//  static String[] usernames = new String[10];
//  static Client[] clients = new Client[10];
//  static int numUsers = 0;
//  static int numClients = 0;
//  private static String user = "";
//  
//  public ServerImpl() { }
//  
//  public String capitalize(String text) {
//    
//    usernames[numUsers] = text;
//    numUsers++;
//    
//    return text.toUpperCase();
//  }
//  
//  public String prettyPrint(){
//    //print out the usernames
//    String s = "Index of users: \n";
//    for (int i = 0; i < numUsers; i++){
//      s += usernames[i] + "\n";
//    }
//    return s;
//  }
//  
//  public static boolean checkIfUserExists(String user){
//    for(int i = 0; i < numUsers; i++){
//      if(usernames[i].equals(user)){
//        return true;
//      }
//    }
//    return false;
//  }
//  public String registerUserOnServer(String input, Client newClient){
//    
//    if(!checkIfUserExists(input)){
//      
//      usernames[numUsers] = input;
//      clients[numClients] = newClient;
//      
//      System.out.println("Username array: " + usernames[numUsers]);
//      numUsers++;
//      numClients++;
//      user = input;
//      
//      System.out.println("Number of users is now " + numUsers);
//      System.out.println("New User " + input + " has been added");
//      
//      try{
//       // CapImpl obj = new CapImpl();
//        //Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
//        //Registry registry = LocateRegistry.getRegistry();
//        //registry.bind(input, stub);
//        
//      } catch (Exception e) {
//        System.err.println("Server exception: " + e.toString());
//        e.printStackTrace();
//      }
//    }else{
//      System.out.println("User " + input + " has already been registered.");
//    }
//    return input;
//  }
//  
//  public void sendMessageToClient(String message){
//    System.out.println(message);
//  }
//  
//  public String sendMessage(String userTo, String message){
//    try{
//      if(checkIfUserExists(userTo)){
//        
//        //CapImpl obj = new CapImpl();
//        //Capitalize newStub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
//        Registry registry = LocateRegistry.getRegistry();
//        //registry.bind(userTo, newStub);
//        ServerInt newStub = (ServerInt) registry.lookup(userTo);
//        //System.out.println("new stub " + newStub.toString());
//        //System.out.println("old stub " + stub.toString());
//       
//        String messageText = user +  ": " + message;
//        Client receivingClient = getReceivingClient(user);
//        
//        receivingClient.receiveMessage(messageText);
//        //newStub.sendMessageToClient(messageText);
//      }else{
//        System.out.println("User does not exist.");
//      }
//    }catch(Exception e) {
//      System.err.println("Server exception: " + e.toString());
//      e.printStackTrace();
//    }
//    return message;
//  }
//  public static Client getReceivingClient(String userToFind){
//    if(checkIfUserExists(userToFind)){
//      for(int i = 0; i < numUsers; i++){
//        if(usernames[i].equals(user)){
//          return clients[i];
//        }
//      }
//    }else{
//      return null;
//    }
//     return null;
//  }
//  public static void main(String args[]) {
//    
//    try {
//      ServerImpl obj = new ServerImpl();
//      ServerInt stub = (ServerInt) UnicastRemoteObject.exportObject(obj, 0);
//      
//      // Bind the remote object's stub in the registry
//      Registry registry = LocateRegistry.getRegistry();
//      registry.bind("InitialConnection", stub);
//      
//      System.err.println("Server ready");
//      
//      
//      
//    } catch (Exception e) {
//      System.err.println("Server exception: " + e.toString());
//      e.printStackTrace();
//    }
//  }
//}