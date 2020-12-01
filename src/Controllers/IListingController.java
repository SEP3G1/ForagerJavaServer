package Controllers;

import java.io.IOException;

public interface IListingController
{
  String getProducts() throws IOException;
  String getProductCategories() throws IOException;
  String getNumberOfResults(String q) throws IOException;
  String getListing(String id) throws IOException;
  String getListingPostCodes() throws IOException;
  String getListingNamesAndCovers() throws IOException;
  String createListing(String str) throws IOException;
  String updateListing(String str) throws IOException;
  String uploadImage(String str) throws IOException;
  String reportListing(String str) throws IOException;
  String getAllReports(String str) throws IOException;
  void deleteListing(String str) throws IOException;
}
