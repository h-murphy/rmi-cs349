import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CapClient {

    private CapClient() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Capitalize stub = (Capitalize) registry.lookup("CapService");
            
            String input = System.console().readLine();
            
            if (input.equals("get")){ //returns list of usernames
            	String output = stub.prettyPrint();
                System.out.println(output);
            }
            else { //capitalize and return inputed string
                String response = stub.capitalize(input);
                System.out.println("response: " + response);

            }            
            
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}