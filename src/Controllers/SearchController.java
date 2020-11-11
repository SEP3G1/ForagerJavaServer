package Controllers;

import Config.Config;
import Models.Listing;
import Models.Product;
import Models.SearchQuery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchController implements ISearchController
{
  @Override public String getAllListings() throws IOException
  {
    HttpClient client = new DefaultHttpClient();
    // Creates the request for the HTTP server
    HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/listing");

    // Executes the HTTP server request
    HttpResponse response = client.execute(request);

    // Get the value of the header in the HTTP request. Expected 200 OK
    String status = String.valueOf(response.getStatusLine());
    System.out.println("Response: " + status);

    // Reads the body of the HTTP response
    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

    String line = "";
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<Listing> listings = new ArrayList<>();
    //Read body
    while ((line = rd.readLine()) != null) {
      if (line != null){
        //Map listing to object
        //listing = (Listing) objectMapper.readValue(line, Listing.class);
        listings = (ArrayList<Listing>) objectMapper.readValue(line, new TypeReference<ArrayList<Listing>>() {});
      }
    }

    for (Listing listing: listings)
    {
      //Get product
      HttpGet request2 = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/product/" + listing.productId);
      HttpResponse response2 = client.execute(request2);
      BufferedReader rd2 = new BufferedReader (new InputStreamReader(response2.getEntity().getContent()));
      Product product = null;
      while ((line = rd2.readLine()) != null) {
        if (line != null){
          product = objectMapper.readValue(line, Product.class);
        }
      }
      //Insert product in listing
      listing.product = product;
    }
    SearchQuery searchQuery = new SearchQuery("Newest listings",listings.size());
    searchQuery.setListings(listings);

    String jsonSQ = objectMapper.writeValueAsString(searchQuery);
    System.out.println(jsonSQ);
    return jsonSQ;
  }
}
