package Models;

import java.util.ArrayList;

public class SearchQuery
{
  public String query;
  public int results;
  public ArrayList<Listing> listings;
  public SearchQuery(String query, int results){
    this.query = query;
    this.results = results;
    listings = new ArrayList<Listing>();
  }
  public void setListings(ArrayList<Listing> listings){
    this.listings = listings;
  }
  public void setListing(Listing listing){
    listings.add(listing);
  }
}
