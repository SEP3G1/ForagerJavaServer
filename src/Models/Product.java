package Models;

import java.util.ArrayList;

public class Product
{
  public String type;
  ArrayList<String> images;

  public Product(String type, String url){
    this.type = type;
    images = new ArrayList<String>();
    images.add(url);
  }

  public String getType()
  {
    return type;
  }

  public ArrayList<String> getImages()
  {
    return images;
  }

}
