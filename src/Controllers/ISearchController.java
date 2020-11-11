package Controllers;

import java.io.IOException;

public interface ISearchController
{
  String search(String q) throws IOException;
  String getAllListings() throws IOException;
}
