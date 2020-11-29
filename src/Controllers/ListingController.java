package Controllers;

import Models.Listing;
import Models.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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

    String listingJSON = objectMapper.writeValueAsString(listing);
    return listingJSON;
  }

  @Override
  public String getListingPostCodes() throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/listing/postcode");

    String line = "";
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<String> listingPostCodes = new ArrayList<>();
    //Read body
    while ((line = rd.readLine()) != null) {
        //Map listing to object
        //listing = (Listing) objectMapper.readValue(line, Listing.class);
        listingPostCodes = (ArrayList<String>) objectMapper.readValue(line, new TypeReference<ArrayList<String>>() {});
    }

    String jsonListingPostCodes = objectMapper.writeValueAsString(listingPostCodes);
    return jsonListingPostCodes;
  }

  @Override
  public String getListingNamesAndCovers() throws IOException {
    rd = communicationController.HttpGetRequest("/api/listing/namescovers");  //#patrick er dette best practice eller ikke?

    String line = "";
    ObjectMapper objectMapper = new ObjectMapper();
    Object listingNamesAndCovers = new Object();
    //Read body
    while ((line = rd.readLine()) != null) {
        //Map listing to object
        //listing = (Listing) objectMapper.readValue(line, Listing.class);
        listingNamesAndCovers = objectMapper.readValue(line, new TypeReference<Object>() {});
    }

    String jsonListingPostCodes = objectMapper.writeValueAsString(listingNamesAndCovers);
    return jsonListingPostCodes;
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

  @Override public String updateListing(String str) throws IOException
  {
    String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    System.out.println(str);
    rd = communicationController.HttpPostRequest("/api/listing/" + query);

    String line = "";
    while ((line = rd.readLine()) != null) {
      System.out.println(line);
      return line;
    }
    return "Something Really Bad Happened";
  }
  @Override public String uploadImage(String str) throws IOException
  {
    String[] strings = str.split("\"");
    return strings[3];
  }
}
