package Models;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;

public class Listing
{
  public int id;
  public Product product;
  public double price;
  public double weight;
  public String bestBy;
  public String comment;
  public boolean delivery;

  public ArrayList<String> images;

  public Listing(int id, Product product, double price, double weight, String bestBy, String comment, boolean delivery){
    this.id = id;
    this.product = product;
    this.price = price;
    this.weight = weight;
    this.bestBy = bestBy;
    this.comment = comment;
    this.delivery = delivery;
    images = new ArrayList<String>();
  }
}
