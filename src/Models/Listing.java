package Models;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;

public class Listing
{
  public int listingId;
  public int userId;
  public int productId;
  public int companyId;
  public Product product = null;
  public double price;
  public double quantity;
  public String unit;
  public String startDate;
  public String bestBefore;
  public String pickupAddress;
  public String postcode;
  public boolean hasDelivery;
  public String pictureList;
  public int numberOfViews;
  public boolean isArchived;
  public String comment;
  public ArrayList<String> pictures = new ArrayList<String>();

  public Listing(){
    //Required for jackson to serialize to Listing
    super();
  }

  public Listing(int listingId, int userId, int productId, int companyId, Product product,
      double price, double quantity, String unit, String startDate, String bestBefore,
      String pickupAddress, String postcode, boolean hasDelivery,
      String pictureList, int numberOfViews, boolean isArchived, String comment,
      ArrayList<String> pictures)
  {
    this.listingId = listingId;
    this.userId = userId;
    this.productId = productId;
    this.product = product;
    this.companyId = companyId;
    this.price = price;
    this.quantity = quantity;
    this.unit = unit;
    this.startDate = startDate;
    this.bestBefore = bestBefore;
    this.pickupAddress = pickupAddress;
    this.postcode = postcode;
    this.hasDelivery = hasDelivery;
    this.pictureList = pictureList;
    this.numberOfViews = numberOfViews;
    this.isArchived = isArchived;
    this.comment = comment;
    this.pictures = pictures;
  }

  public int getCompanyId()
  {
    return companyId;
  }

  public int getListingId()
  {
    return listingId;
  }

  public void setListingId(int listingId)
  {
    this.listingId = listingId;
  }

  public int getUserId()
  {
    return userId;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
  }

  public int getProductId()
  {
    return productId;
  }

  public void setProductId(int productId)
  {
    this.productId = productId;
  }

  public Product getProduct()
  {
    return product;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public double getQuantity()
  {
    return quantity;
  }

  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }

  public String getUnit()
  {
    return unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public String getPickupAddress()
  {
    return pickupAddress;
  }

  public void setPickupAddress(String pickupAddress)
  {
    this.pickupAddress = pickupAddress;
  }

  public String getPostcode()
  {
    return postcode;
  }

  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
  }

  public boolean isHasDelivery()
  {
    return hasDelivery;
  }

  public void setHasDelivery(boolean hasDelivery)
  {
    this.hasDelivery = hasDelivery;
  }

  public String getPictureList()
  {
    return pictureList;
  }

  public void setPictureList(String pictureList)
  {
    this.pictureList = pictureList;
  }

  public int getNumberOfViews()
  {
    return numberOfViews;
  }

  public void setNumberOfViews(int numberOfViews)
  {
    this.numberOfViews = numberOfViews;
  }

  public boolean isArchived()
  {
    return isArchived;
  }

  public void setArchived(boolean archived)
  {
    isArchived = archived;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public ArrayList<String> getPictures()
  {
    return pictures;
  }

  public void setPictures(ArrayList<String> pictures)
  {
    this.pictures = pictures;
  }
}
