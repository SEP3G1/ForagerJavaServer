import Config.Config;
import Models.Listing;
import Models.Product;
import Models.SearchQuery;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class EchoThread extends Thread {
    protected Socket socket;
    public ArrayList<Product> products;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
        products = new ArrayList<Product>();
        products.add(new Product("Cucumber", "https://i.ndtvimg.com/mt/cooks/2014-11/cucumber.jpg"));
        products.add(new Product("Milk", "https://i.guim.co.uk/img/media/410573030f4d4b95244a651ad41d70f160c61600/0_0_4136_2788/master/4136.jpg?width=700&quality=85&auto=format&fit=max&s=5f65f2f95cd42c42c9d1eede2e80c6fd"));
        products.add(new Product("Melon", "https://www.euroharvest.co.uk/wp-content/uploads/2020/07/Cantaloupe-melon.jpeg"));
        products.add(new Product("Tomato", "https://cdn.shopify.com/s/files/1/0244/4961/3905/products/tomato@2x.jpg?v=1576807420"));
        products.add(new Product("Lettuce", "https://images-na.ssl-images-amazon.com/images/I/41CGtIyWgML._AC_.jpg"));
        products.add(new Product("Hotdog Bread", "https://5.imimg.com/data5/XN/GP/MY-48605426/hot-dog-buns-500x500.jpg"));
        products.add(new Product("Wine", "https://cdn-a.william-reed.com/var/wrbm_gb_food_pharma/storage/images/publications/food-beverage-nutrition/foodnavigator-asia.com/headlines/markets/wine-will-be-fine-outlook-for-online-sales-in-china-remains-positive-despite-coronavirus-outbreak/10756649-1-eng-GB/Wine-will-be-fine-Outlook-for-online-sales-in-China-remains-positive-despite-coronavirus-outbreak_wrbm_large.jpg"));
        products.add(new Product("Sugar", "https://chriskresser.com/wp-content/uploads/467009529.jpg"));
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

                System.out.println("Server received: " + received);
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList<String> r = objectMapper.readValue(received, new TypeReference<ArrayList<String>>(){});
                String toSend="";

                //Match action
                switch (r.get(0)){
                    case "search": toSend = search(r.get(1)); break;
                    case "login": toSend = login(r.get(1)); break;
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
    public String getJSONQuery(String str) throws JsonProcessingException
    {
        ArrayList<Listing> listings = new ArrayList<Listing>();
        Random rand = new Random();
        for (int i = 0; i < (9+rand.nextInt(8)); i++)
        {
            listings.add(new Listing(10, products.get(rand.nextInt(products.size())), rand.nextInt(50), rand.nextInt(5000)/1000, "20/10/2020", "No comment.", (rand.nextInt(2) == 0 ? true : false )));
        }
        SearchQuery q = new SearchQuery(str, listings.size() + rand.nextInt(50));
        q.setListings(listings);

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(q);
        System.out.println("Send: " + result);
        return result;
    }
    private String search(String q) throws JsonProcessingException
    {
        return getJSONQuery(q);
    }
    //Expects username, password
    private String login(String q) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> r = objectMapper.readValue(q, new TypeReference<ArrayList<String>>(){});
        HttpClient client = new DefaultHttpClient();

        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/userlogin?userName=" + r.get(0) + "&pass=" + r.get(1));

        // Executes the HTTP server request
        HttpResponse response = client.execute(request);

        // Get the value of the header in the HTTP request. Expected 200 OK
        String status = String.valueOf(response.getStatusLine());
        System.out.println("Response: " + status);

        // Reads the body of the HTTP response
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        String line = "";

        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            return line;
        }
        return "Not implemented";
    }
}