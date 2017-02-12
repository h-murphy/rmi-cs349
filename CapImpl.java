import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CapImpl implements Capitalize {
  
  static String[] usernames = new String[10];
  static int numUsers = 0;
  static String user = "";
  
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
  
  public boolean checkIfUserExists(String user){
    for(int i = 0; i < numUsers; i++){
      if(usernames[i].equals(user)){
        return true;
      }
    }
    return false;
  }
  public String registerNewUser(String input){
    
    if(!checkIfUserExists(input)){
      
      usernames[numUsers] = input;
      System.out.println("Username array: " + usernames[numUsers]);
      numUsers++;
      user = input;
      
      System.out.println("Number of users is now " + numUsers);
      System.out.println("New User " + input + " has been added");
      
      try{
        CapImpl obj = new CapImpl();
        Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind(input, stub);
        
      } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
      }
    }else{
      System.out.println("User " + input + " has already been registered.");
    }
    return input;
  }
  
  public String sendMessage(String userTo, String message){
    try{
      if(checkIfUserExists(userTo)){
        Registry registry = LocateRegistry.getRegistry();
        Capitalize stub = (Capitalize) registry.lookup(userTo);
        //String 
      }else{
        System.out.println("User does not exist.");
      }
    }catch(Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
    return message;
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