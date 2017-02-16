/*
* Hannah Murphy & Anna M. Pfoertsch
* CS349a Assignment 2
* Client Interface
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMClient extends Remote {
   public String getUserName() throws RemoteException;
   public void receiveMessage(String messageText) throws RemoteException;
}