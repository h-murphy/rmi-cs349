import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
        
public class CapImpl implements Capitalize {
        
	String[] usernames;
	int num;
	
    public CapImpl() {
    	usernames = new String[10];
    	num = 0; //keeping track of how many users there are
    }

    public String capitalize(String text) {
    	
    	usernames[num] = text;
    	num++;
 
        return text.toUpperCase();
    }
    
    public String prettyPrint(){
    	//print out the usernames
    	String s = "";
    	
    	for (int i = 0; i < num; i++){
    		s += usernames[i] + "\n";
    	}
		return s;
    }
        
    public static void main(String args[]) {
        
        try {
            CapImpl obj = new CapImpl();
            Capitalize stub = (Capitalize) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("CapService", stub);

            System.err.println("Server ready");
                        
            
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}