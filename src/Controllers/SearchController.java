package Controllers;

import Models.Listing;
import Models.Product;
import Models.SearchQuery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchController implements ISearchController
{
  private ICommunicationController communicationController;
  private BufferedReader rd;

  public SearchController(ICommunicationController communicationController)
  {
    this.communicationController = communicationController;
  }

  @Override public String search(String q) throws IOException
  {
    if (q == null)
    {
      return getAllListings();
    }
    return "Something Really Bad Happened";
  }


  @Override public String getAllListings() throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/listing");

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
      rd = communicationController.HttpGetRequest("/api/product/" + listing.productId);
      Product product = null;
      while ((line = rd.readLine()) != null) {
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
    return jsonSQ;
  }
}
