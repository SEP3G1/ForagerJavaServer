package Models;

import java.util.ArrayList;

public class Product
{
  public int productId;
  public String name;
  public String imagesString;
  public ArrayList<String> images;
  public String productCategory;

  public Product(){
    //Required for jackson to serialize to Product
    super();
  }
  public Product(int productId, String name, String productCategory,
      String imagesString, ArrayList<String> images)
  {
    this.productId = productId;
    this.name = name;
    this.productCategory = productCategory;
    this.imagesString = imagesString;
    this.images = images;
  }
}
