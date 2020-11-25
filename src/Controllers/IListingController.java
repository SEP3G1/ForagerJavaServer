package Controllers;

import java.io.IOException;

public interface IListingController
{
  String getProductCategories() throws IOException;
  String getProducts() throws IOException;
  String getListing(String id) throws IOException;
  String createListing(String str) throws IOException;
  String updateListing(String str) throws IOException;
  String uploadImage(String str) throws IOException;
  String reportListing(String str) throws IOException;
  String getAllReports(String str) throws IOException;
}
