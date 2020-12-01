package Server;
import Config.Config;
import Controllers.*;
import Models.Company;
import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class SocketServer extends Thread {
    protected Socket socket;
    private IListingController listingController;
    private IUserController userController;
    private ISearchController searchController;
    private ICommunicationController communicationController;
    private ICompanyController companyController;
    private IChatController chatController;
    private InputStream is = null;
    private OutputStream os = null;

    public SocketServer(Socket clientSocket) throws IOException
    {
        this.socket = clientSocket;
        communicationController = new CommunicationsController();
        listingController = new ListingController(communicationController);
        companyController = new CompanyController(communicationController);
        userController = new UserController(communicationController);
        searchController = new SearchController(communicationController);
        chatController = new ChatController(companyController, userController); // Takes companyController as input
    }

    //Listens for bytes and echos back to sender
    public void run(){
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
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                ArrayList<String> r = objectMapper.readValue(received, new TypeReference<ArrayList<String>>(){});
                String toSend="";
                //Match action
                switch (r.get(0)){
                    case "search": toSend = searchController.search(r.get(1)); break;
                    case "lazyFilterSearch": toSend = searchController.lazyFilterSearch(r.get(1), r.get(2), r.get(3)); break;
                    case "getNumberOfResults": toSend = listingController.getNumberOfResults(r.get(1)); break;
                    case "login": toSend = userController.login(r.get(1)); break;
                    case "getlisting": toSend = listingController.getListing(r.get(1)); break;
                    case "getListingPostCodes": toSend = listingController.getListingPostCodes(); break;
                    case "getListingNamesAndCovers": toSend = listingController.getListingNamesAndCovers(); break;
                    case "createlisting": toSend = listingController.createListing(r.get(1)); break;
                    case "updatelisting": toSend = listingController.updateListing(r.get(1)); break;
                    case "getcompany": toSend = companyController.getCompany(r.get(1)); break;
                    case "getcompanyFromUserId": toSend = companyController.getCompany(userController.getUser(r.get(1)).getCompanyId() + ""); break;
                    case "getUser": toSend = userController.getUserAsString(r.get(1)); break;
                    case "createcompany": toSend = companyController.createCompany(r.get(1)); break;
                    case "updatecompany": toSend = companyController.updateCompany(r.get(1)); break;
                    case "getproducts": toSend = listingController.getProducts(); break;
                    case "getproductcategories": toSend = listingController.getProductCategories(); break;
                    case "uploadImage": toSend = listingController.uploadImage(r.get(1)); break;
                    case "reportlisting" : toSend = listingController.reportListing(r.get(1)); break;
                    case "getallreports": toSend = listingController.getAllReports(r.get(1)); break;
                    case "deletelisting" : toSend = listingController.deleteListing(r.get(1)); break;
                    case "deletecompanywish" : toSend = companyController.deleteCompanyWish(r.get(1)); break;
                    case "deletecompany" : toSend = companyController.deleteCompany(r.get(1)); break;
                    case "getcompaniestodelete" : toSend = companyController.getCompaniesToDelete(r.get(1)); break;
                    case "sendMessage" : SendMessageToIp(chatController.generateMessage(r.get(1))); break;
                    case "recieveMessage" : Receive(r.get(1)); break;
                    case "recieveMessage" : chatController.receiveMessage(r.get(1)); break;
                    case "unread" : toSend = chatController.unreadMessages(); break;
                    case "getConversation" : toSend = chatController.getConversation(r.get(1)); break;
                  case "sendMessage" : toSend = SendMessageToIp(chatController.generateMessage(r.get(1))); break;
                  case "sendMessageWM" : toSend = SendMessageToIp(objectMapper.readValue(r.get(1), Message.class)); break;
                  default:
                    System.out.println("Recieved unrecognised command: " + r);
                }

                if (toSend.length() > 0)
                    SendBack(toSend);


            } catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SendBack(String toSend) throws IOException
    {
        byte[] toSendBytes = toSend.getBytes();
        int toSendLen = toSendBytes.length;
        byte[] toSendLenBytes = new byte[4];
        toSendLenBytes[0] = (byte) (toSendLen & 0xff);
        toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
        toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
        toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
        os.write(toSendLenBytes);
        os.write(toSendBytes);
    }

    public String SendMessageToIp(Message message) throws IOException
    {
        Socket socket = null;
        String connectionAddress = message.getToCompany().getConnectionAddress();
            try {
                socket = new Socket(connectionAddress, Config.PORT_T2);

                System.out.println("Connected to: " + connectionAddress);
                // new thread for a client
                System.out.println("Message sent: " + message.getMessage());

                MessageService.getInstance().addNewMessage(message);
                new ChatSocketHandler(message, socket).start();

                return chatController.getConversation(message.getListingId() + "");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            return null;
    }
}