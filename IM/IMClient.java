/*
* Hannah Murphy & Anna M. Pfoertsch
* CS349a Assignment 2
* Client Interface
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMClient extends Remote {
    /* 
    * getUserName()
    * Returns the username of the IMClient. The username is used to index the           
    * stubs on the server and display the name. 
    */
   public String getUserName() throws RemoteException;
    /* 
    * receiveMessage(String messageText)
    * Prints the username of the sender and the message content on the receiving 
    * client’s console.
    */
   public void receiveMessage(String messageText) throws RemoteException;
}
