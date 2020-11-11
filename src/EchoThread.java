import Controllers.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class EchoThread extends Thread {
    protected Socket socket;
    private IListingController listingController;
    private IUserController userController;
    private ISearchController searchController;
    private ICommunicationController communicationController;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
        communicationController = new CommunicationsController();
        listingController = new ListingController(communicationController);
        userController = new UserController(communicationController);
        searchController = new SearchController(communicationController);
    }

    //Listens for bytes and echos back to sender
    public void run(){
        InputStream is = null;
        OutputStream os = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try{
                // Receiving
                byte[] lenBytes = new byte[4];
                is.read(lenBytes, 0, 4);
                int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                        ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
                byte[] receivedBytes = new byte[len];
                is.read(receivedBytes, 0, len);
                String received = new String(receivedBytes, 0, len);

                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList<String> r = objectMapper.readValue(received, new TypeReference<ArrayList<String>>(){});
                String toSend="";

                //Match action
                switch (r.get(0)){
                    case "search": toSend = searchController.search(r.get(1)); break;
                    case "login": toSend = userController.login(r.get(1)); break;
                    case "getlisting": toSend = listingController.getListing(r.get(1)); break;
                    case "createlisting": toSend = listingController.createListing(r.get(1)); break;
                    case "getproducts": toSend = listingController.getProducts(); break;
                    case "getproductcategories": toSend = listingController.getProductCategories(); break;
                }

                // Sending
                if (received.toLowerCase().equals("exit"))
                    toSend = "Connection closed";

                byte[] toSendBytes = toSend.getBytes();
                int toSendLen = toSendBytes.length;
                byte[] toSendLenBytes = new byte[4];
                toSendLenBytes[0] = (byte)(toSendLen & 0xff);
                toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
                toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
                toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
                os.write(toSendLenBytes);
                os.write(toSendBytes);
            } catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}