import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.rmi.RemoteException;

public class Client extends UnicastRemoteObject implements IMClient{
  String userName;
  
  public Client(String n) throws RemoteException{
      super();
      userName = n;
  }
  
  public String getUserName(){
    return userName;
  }
  
  public void receiveMessage(String messageText){
    System.out.println(messageText);
  }
  public static void main(String[] args){

    System.out.println("\nClient Ready\n");
    
    System.out.println("register-name: to register the client's user name.");

    String host = (args.length < 1) ? null : args[0];
    try {
      
      ServerImpl obj = new ServerImpl();
      
      Registry registry = LocateRegistry.getRegistry();
      ServerInt stub = (ServerInt) registry.lookup("InitialConnection");

      String userNameForSending = "";
      boolean active = true;
      String input = System.console().readLine();
      Client newUser;
      
      if(input.substring(0,8).equals("register")){
        String newUserName = input.substring(9);
        newUser = new Client(newUserName);
        userNameForSending = newUserName;
        System.out.println("Made new Client");

        String c = (String) stub.registerUserOnServer((IMClient)newUser);
        System.out.println("Registered User on Server");

        System.out.println("You are now registered as " + newUserName + ".");
        
      }
      
      String commands = "\n***COMMANDS***\nget: to get the list of names from the server's directory\n"
        +"snd-name-text: to isntantly send a test message to name.\nquit: to quit the application.\n";
      
      System.out.println(commands);

      while(active){
       if(stub != null){
        String command = System.console().readLine();
        System.out.println("Command: " + command);
        if(command.equals("get")){ //returns list of usernames
            System.out.println("Trying to get usernames");
            String output = stub.getUsers();
            System.out.println(output);
          }else if(command.equals("quit")){
            System.out.println("Quitting Session");
            System.exit(0);
          }else if(command.substring(0,3).equals("snd")){
            String userTo = command.substring(command.indexOf('-')+1, command.lastIndexOf('-'));
            String message = command.substring(command.lastIndexOf('-') + 1);
            String t = stub.sendMessage(userTo, message, userNameForSending);
            System.out.println(t);
          }else{
           System.out.println("\nInvalid command. These are the commands you can use:"); 
           System.out.println(commands);
          }
       }
      }
    }catch(Exception e){
      System.out.println("Exception in Client: " + e.toString());
    }
  }
}