import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CapClient {
  
  private CapClient() {}
  
  public static void main(String[] args) {
    
    System.out.println("Client Ready");
    String host = (args.length < 1) ? null : args[0];
    try {
      
      //Capitalize stub = null;
      CapImpl obj = new CapImpl();
      
      Registry registry = LocateRegistry.getRegistry();
      Capitalize stub = (Capitalize) registry.lookup("InitialConnection");
      
      // Bind the remote object's stub in the registry
      
      //registry.bind("InitialConnection", stub);
      
      // Registry registry = LocateRegistry.getRegistry(host);
      
      boolean active = true;
      String input = System.console().readLine();
      
      if(input.substring(0,8).equals("register")){
        String newUser = input.substring(9);
        String newUserName = stub.registerNewUser(newUser);
        stub = (Capitalize) registry.lookup(newUserName);
        System.out.println("You are now registered as " + newUserName + ".");
      }
      System.out.println("Input Commands Now: ");
      while(active){
        //if(stub != null){
        String command = System.console().readLine();
          if (command.equals("get")){ //returns list of usernames
            String output = stub.prettyPrint();
            System.out.println(output);
          }else if(command.equals("quit")){
            active = false;
            System.out.println("Quitting Session");
          }
          
        //}
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
    System.out.println("Session Has Ended");
  }
}
