package Controllers;

import Config.Config;
import Models.Listing;
import Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListingController implements IListingController
{
  private HttpClient client;

  public ListingController(HttpClient client)
  {
    this.client = client;
  }

  @Override public String getProductCategories() throws IOException
  {
    HttpClient client = new DefaultHttpClient();
    // Creates the request for the HTTP server
    HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/productcategory");

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

  @Override public String getProducts() throws IOException
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

  @Override public String getListing(String id) throws IOException
  {
    HttpClient client = new DefaultHttpClient();
    // Creates the request for the HTTP server
    HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/listing/" + id);

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

  @Override public String createListing(String str) throws IOException
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
}
