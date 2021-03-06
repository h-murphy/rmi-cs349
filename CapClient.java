import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.rmi.RemoteException;

public class CapClient extends UnicastRemoteObject implements IMClient{
  String name;
  
  private CapClient(String n) throws RemoteException{
    super();
    this.name = n;
  }
  
  public void receiveMessage(String messageText) throws RemoteException{
    System.out.println(messageText);
  }
  public void initializeServerConnection() throws RemoteException{
    try{
    CapImpl obj = new CapImpl();
      
    Registry registry = LocateRegistry.getRegistry();
    Capitalize stub = (Capitalize) registry.lookup("InitialConnection");
    
    }catch(Exception e){
    }
  }
  
  public static CapClient registerUser(String name){
    try{
    CapImpl obj = new CapImpl();
      
    Registry registry = LocateRegistry.getRegistry();
    
    Capitalize stub = (Capitalize) registry.lookup("InitialConnection");
    
    CapClient newUser = new CapClient(name);
    
    String newUserName = stub.registerUserOnServer(name, newUser);
    stub = (Capitalize) registry.lookup(newUserName);
    
    return newUser;
    }catch(Exception e){
    }
    return null;
  }
  
  
  public String getName(){
    
  return "";
  }
  
  public String getMessage(){
    return"";
  }
  
  
  public static void main(String[] args) {
    
    System.out.println("Client Ready");
    String host = (args.length < 1) ? null : args[0];
    try {
      
      //Capitalize stub = null;
      CapImpl obj = new CapImpl();
      
      Registry registry = LocateRegistry.getRegistry();
     // Capitalize stub = (Capitalize) registry.lookup("InitialConnection");
      
      // Bind the remote object's stub in the registry
      
      //registry.bind("InitialConnection", stub);
      
      // Registry registry = LocateRegistry.getRegistry(host);
      
      boolean active = true;
      String input = System.console().readLine();
      CapClient newUser;
      if(input.substring(0,8).equals("register")){
        String newUserName = input.substring(9);
        newUser = registerUser(newUserName);
        Capitalize stub = (Capitalize) registry.lookup(newUserName);
        System.out.println("You are now registered as " + newUserName + ".");
      
      System.out.println("Input Commands Now: ");
      while(active){
        //if(stub != null){
        String command = System.console().readLine();
          if (command.equals("get")){ //returns list of usernames
            //String output = stub.prettyPrint();
            //System.out.println(output);
          }else if(command.equals("quit")){
            active = false;
            System.out.println("Quitting Session");
          }else if(command.substring(0,3).equals("snd")){
            String userTo = command.substring(command.indexOf('-')+1, command.lastIndexOf('-'));
            System.out.println(userTo);
            String message = command.substring(command.lastIndexOf('-') + 1);
     
            System.out.println(message);
            
            String value = stub.sendMessage(userTo, message);
            System.out.println(value);
          }
          
        //}
      }
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
    System.out.println("Session Has Ended");
  }
}
