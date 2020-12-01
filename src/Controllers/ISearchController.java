package Controllers;

import java.io.IOException;

public interface ISearchController
{
  String search(String q) throws IOException;
  String lazyFilterSearch(String q, String filter, String sequenceNumber) throws IOException;
  String getAllListings(String q) throws IOException;
}
