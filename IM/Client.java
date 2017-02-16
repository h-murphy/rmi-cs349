/*
* Hannah Murphy & Anna M. Pfoertsch
* CS349a Assignment 2
* Client Class
*/

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
  
//client specific method that allows a system print to client console
//param messageText is inputed by server, String is received from another client
  public void receiveMessage(String messageText){
    System.out.println(messageText);
  }
  
  
  public static void main(String[] args){
    
    //null for local testing
    String host = (args.length < 1) ? null : args[0];
    
    try {
      ServerImpl obj = new ServerImpl();
      // Bind the remote object's stub in the registry
      Registry registry = LocateRegistry.getRegistry();
      ServerInt stub = (ServerInt) registry.lookup("InitialConnection");
      System.out.println("Client Ready");
      String userNameForSending = "";

      Client newUser;
      
      boolean user = true;
      while(user){

        System.out.println("To start chatting, please register by typing register-name");
        String input = System.console().readLine(); //cmd line input
              
        if(input.length() > 8 && input.substring(0,8).equals("register")){
          //registering a new client
          String newUserName = input.substring(9);
          newUser = new Client(newUserName);
          userNameForSending = newUserName;
          String c = (String) stub.registerUserOnServer((IMClient)newUser);
          System.out.println("You are now registered as " + newUserName + ".");
          user = false;
          break;
        }
          System.out.println("\nDid you not read the instructions???");
      }
      
      String commands = "\n\n***COMMANDS***\nget: to get the list of names from the server's directory."
        +"\nsnd-user1-text: to instantly send a text message to user1.\nquit: to quit the application.\n\n";
      
      System.out.println(commands);
      
      System.out.println("Type commands below:");
      
      boolean active = true;
      while(active){
        
       if(stub != null){
        String command = System.console().readLine();
        System.out.println("Command: " + command); //print out the command typed
        
        if(command.equals("get")){ //returns list of usernames
          System.out.println("Trying to get usernames");
          String output = stub.getUsers();
          System.out.println(output);
            
          }else if(command.equals("quit")){ //quit out of the program
            System.out.println("Quitting Session");
            System.exit(0);
            
          }else if(command.substring(0,3).equals("snd")){ //send a message to another client
            String userTo = command.substring(command.indexOf('-')+1, command.lastIndexOf('-'));
            String message = command.substring(command.lastIndexOf('-') + 1);
            String t = stub.sendMessage(userTo, message, userNameForSending); //method in ServerImpl
          }
          else{
          System.out.println("Invalid Command, the commands you can use are:");
          System.out.println(commands);
          }
       }
      } 
    }catch(Exception e){
      System.out.println("Exception in Client: " + e.toString());
    }
  }
}