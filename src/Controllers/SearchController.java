package Controllers;

import Models.Listing;
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
      return getAllListings(q);
  }

  @Override
  public String lazyFilterSearch(String q, String filter, String sequenceNumber) throws IOException {
    return getLazyFilterListings(q, filter, sequenceNumber);
  }

  @Override public String getAllListings(String q) throws IOException
  {
    StringBuilder queryString = new StringBuilder().append("/api/listing");

    if (q != null)
    {
      queryString.append("?parameter=" + q);
    }
    rd = communicationController.HttpGetRequest(queryString.toString().replace(" ", "%20"));

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

    SearchQuery searchQuery = new SearchQuery((q == null)?"Newest listings":q,listings.size());
    searchQuery.setListings(listings);
    String jsonSQ = objectMapper.writeValueAsString(searchQuery);
    return jsonSQ;
  }

  private String getLazyFilterListings(String q, String filter, String sequenceNumber) throws IOException
  {
    StringBuilder queryString = new StringBuilder().append("/api/listing");

    if (q != null)
    {
      queryString.append("?parameter=" + q);
      queryString.append("&filter=" + filter);
      queryString.append("&sequencenumber=" + sequenceNumber);
    }
    rd = communicationController.HttpGetRequest(queryString.toString().replace(" ", "%20"));

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

    SearchQuery searchQuery = new SearchQuery((q == null)?"Newest listings":q,listings.size());
    searchQuery.setListings(listings);
    String jsonSQ = objectMapper.writeValueAsString(searchQuery);
    return jsonSQ;
  }
}
