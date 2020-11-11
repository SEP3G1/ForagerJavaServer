package Controllers;

import Models.Listing;
import Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ListingController implements IListingController
{
  private ICommunicationController communicationController;
  private BufferedReader rd;

  public ListingController(ICommunicationController communicationController)
  {
    this.communicationController = communicationController;
  }

  @Override public String getProductCategories() throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/productcategory");

    String line = "";
    while ((line = rd.readLine()) != null) {
      return line;
    }
    return "Something Really Bad Happened";
  }

  @Override public String getProducts() throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/product");
    String line = "";

    while ((line = rd.readLine()) != null) {
      return line;
    }
    return "Something Really Bad Happened";
  }

  @Override public String getListing(String id) throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/listing/" + id);

    String line = "";
    ObjectMapper objectMapper = new ObjectMapper();
    Listing listing = null;
    //Read body
    while ((line = rd.readLine()) != null) {
      if (line != null){
        //Map listing to object
        listing = objectMapper.readValue(line, Listing.class);
      }
    }
    BufferedReader rd2 = communicationController.HttpGetRequest("/api/product/" + listing.productId);

    Product product = null;
    while ((line = rd2.readLine()) != null) {
      if (line != null){
        product = objectMapper.readValue(line, Product.class);
      }
      System.out.println(line);
    }
    //Insert product in listing
    listing.product = product;
    String listingJSON = objectMapper.writeValueAsString(listing);
    return listingJSON;
  }

  @Override public String createListing(String str) throws IOException
  {
    String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    rd = communicationController.HttpPostRequest("/api/listing?listingAsString=" + query);

    String line = "";
    while ((line = rd.readLine()) != null) {
      System.out.println(line);
      return line;
    }
    return "Something Really Bad Happened";
  }
}
