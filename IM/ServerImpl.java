/*
 * Hannah Murphy & Anna M. Pfoertsch
 * CS349a Assignment 2
 * Server Class
 */

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
    //returns a string of users, prints to client via get command
    String s = "\n***USERS***\n";
    for (String key : clientMap.keySet()) {
      s += key + "\n";
    }
    return s;
  }
  
  private boolean checkIfUserExists(String userName) throws RemoteException{
    //returns true if user exists, false if otherwise
    return clientMap.containsKey(userName);
  }
  
  public String sendMessage(String userTo, String message, String userFrom) throws RemoteException{
    if (checkIfUserExists(userTo)){
      //format text to "[userFrom] message"
      String messageText = "["+ userFrom + "] " + message;
      //find specific userTo client in hashmap, then print out the String in their console
      clientMap.get(userTo).receiveMessage(messageText);
      System.out.println("Message Sent!");
      return "sent";
    }
    else{
      //send userFrom an error message
      clientMap.get(userFrom).receiveMessage("Failure to send, user does not exist.");
      return "failed to send";
    }
  }
  
  public String registerUserOnServer(IMClient newClient) throws RemoteException{
    if(!checkIfUserExists(newClient.getUserName())){
      //add to hashmap and increase count
      clientMap.put(newClient.getUserName(), newClient);
      numClients++;
      //Server side prints
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