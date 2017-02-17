/*
 * Hannah Murphy & Anna M. Pfoertsch
 * CS349a Assignment 2
 * Server Class
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote {
  /* 
  * registerUserOnServer(IMClient newClient)
  * Adds the received client to the registry of clients stored on the server. 
  * Client stubs and their usernames are held in a hashmap.
  */
  String registerUserOnServer(IMClient newClient) throws RemoteException;
  /* 
  * getUsers()
  * Returns a String containing all usernames of current registered users. 
  */
  public  String getUsers() throws RemoteException;
  /*
  * sendMessage(String userTo, String message, String userFrom)
  * Looks up user to send message to and calls receiveMessage for the selected * client 
  */
  String sendMessage(String userTo, String message, String userFrom) throws RemoteException;
}
