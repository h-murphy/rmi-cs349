/*
 * Hannah Murphy & Anna M. Pfoertsch
 * CS349a Assignment 2
 * Server Class
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote {
  String registerUserOnServer(IMClient newClient) throws RemoteException;
  public  String getUsers() throws RemoteException;
  String sendMessage(String userTo, String message, String userFrom) throws RemoteException;
}
