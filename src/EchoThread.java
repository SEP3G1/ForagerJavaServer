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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.nio.client.util.HttpAsyncClientUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class EchoThread extends Thread {
    protected Socket socket;
    public ArrayList<Product> products;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
        products = new ArrayList<Product>();
        products.add(new Product(1,"Cucumber",null, "https://i.ndtvimg.com/mt/cooks/2014-11/cucumber.jpg", null));
        products.add(new Product(1,"Milk",null, "https://i.guim.co.uk/img/media/410573030f4d4b95244a651ad41d70f160c61600/0_0_4136_2788/master/4136.jpg?width=700&quality=85&auto=format&fit=max&s=5f65f2f95cd42c42c9d1eede2e80c6fd", null));
        products.add(new Product(1,"Melon",null, "https://www.euroharvest.co.uk/wp-content/uploads/2020/07/Cantaloupe-melon.jpeg", null));
        products.add(new Product(1,"Tomato",null, "https://cdn.shopify.com/s/files/1/0244/4961/3905/products/tomato@2x.jpg?v=1576807420", null));
        products.add(new Product(1,"Lettuce",null, "https://images-na.ssl-images-amazon.com/images/I/41CGtIyWgML._AC_.jpg", null));
        products.add(new Product(1,"Hotdog Bread",null, "https://5.imimg.com/data5/XN/GP/MY-48605426/hot-dog-buns-500x500.jpg", null));
        products.add(new Product(1,"Wine",null, "https://cdn-a.william-reed.com/var/wrbm_gb_food_pharma/storage/images/publications/food-beverage-nutrition/foodnavigator-asia.com/headlines/markets/wine-will-be-fine-outlook-for-online-sales-in-china-remains-positive-despite-coronavirus-outbreak/10756649-1-eng-GB/Wine-will-be-fine-Outlook-for-online-sales-in-China-remains-positive-despite-coronavirus-outbreak_wrbm_large.jpg", null));
        products.add(new Product(1,"Sugar",null, "https://chriskresser.com/wp-content/uploads/467009529.jpg", null));
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
                    case "getlisting": toSend = getListing(r.get(1)); break;
                    case "createlisting": toSend = createListing(r.get(1)); break;
                    case "getproducts": toSend = getProducts(); break;
                    case "getproductcategories": toSend = getProductCategories(); break;
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

    private String getProductCategories() throws IOException
    {
        HttpClient client = new DefaultHttpClient();
        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/productcatagory");

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

    private String getProducts() throws IOException
    {
        HttpClient client = new DefaultHttpClient();
        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/product");

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

    private String getListing(String id) throws IOException
    {
        HttpClient client = new DefaultHttpClient();
        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/listing?id=" + id);

        // Executes the HTTP server request
        HttpResponse response = client.execute(request);

        // Get the value of the header in the HTTP request. Expected 200 OK
        String status = String.valueOf(response.getStatusLine());
        System.out.println("Response: " + status);

        // Reads the body of the HTTP response
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Listing listing = null;
        //Read body
        while ((line = rd.readLine()) != null) {
            if (line != null){
                //Map listing to object
                listing = (Listing) objectMapper.readValue(line, Listing.class);
            }
        }


        //Get product
        HttpGet request2 = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/product/" + listing.productId);
        HttpResponse response2 = client.execute(request2);
        BufferedReader rd2 = new BufferedReader (new InputStreamReader(response2.getEntity().getContent()));
        Product product = null;
        while ((line = rd2.readLine()) != null) {
            if (line != null){
                System.out.println("Mapping: " + line);
                product = objectMapper.readValue(line, Product.class);
            }
            System.out.println(line);
        }
        //Insert product in listing
        listing.product = product;
        String listingJSON = objectMapper.writeValueAsString(listing);
        System.out.println("Sent: " + listingJSON);
        return listingJSON;
    }

    private String createListing(String str) throws IOException
    {
        HttpClient client = new DefaultHttpClient();
        String uri = ("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/listing?listingAsString=");
        String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
        // Creates the request for the HTTP server
        HttpPost request = new HttpPost(uri + query);

        System.out.println(request.getMethod() + request.toString());
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

    public String getJSONQuery(String str) throws JsonProcessingException
    {
        ArrayList<Listing> listings = new ArrayList<Listing>();
        Random rand = new Random();
        for (int i = 0; i < (9+rand.nextInt(8)); i++)
        {
            Product product = products.get(rand.nextInt(products.size()));
            listings.add(new Listing(i, 1, product.productId, product, rand.nextInt(50), rand.nextInt(5000)/1000,"kg",9999,11111,"Address", "8700", (rand.nextInt(2) == 0 ? true : false ), "", 1, false, "No comment", null));
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
        ArrayList<String> r = objectMapper.readValue(q, new TypeReference<>()
        {
        });
        HttpClient client = new DefaultHttpClient();

        // Creates the request for the HTTP server
        HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/userlogin?email=" + r.get(0) + "&pass=" + r.get(1));

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