package Models;

import Config.Config;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable
{
  private String message;
  private Company fromCompany;
  private Company toCompany;
  private String timestamp;
  private int listingId;

  public Message(){
    super();
  }

  public Message(String message, Company fromCompany, Company toCompany,
      String timestamp, int listingId)
  {
    this.message = message;
    this.fromCompany = fromCompany;
    this.toCompany = toCompany;
    this.timestamp = timestamp;
    this.listingId = listingId;
  }

  public int getListingId()
  {
    return listingId;
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

  public String getTimestamp()
  {
    return timestamp;
  }
}
