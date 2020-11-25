package Models;

import Config.Config;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable
{
  private String message;
  private Company fromCompany;
  private Company toCompany;
  private Timestamp timestamp;
  private int listingId;
  private boolean isRead = false;

  public Message(){
    super();
  }

  public Message(String message, Company toCompany, Company fromCompany, int listingId)
  {
    this.message = message;
    this.fromCompany = fromCompany;
    this.toCompany = toCompany;
    this.listingId = listingId;
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public int getListingId()
  {
    return listingId;
  }

  public boolean isRead()
  {
    return isRead;
  }
  public void read(){
    isRead = true;
  }
  public String getMessage()
  {
    return message;
  }

  public Company getFromCompany()
  {
    return fromCompany;
  }

  public Company getToCompany()
  {
    return toCompany;
  }

  public Timestamp getTimestamp()
  {
    return timestamp;
  }
}
