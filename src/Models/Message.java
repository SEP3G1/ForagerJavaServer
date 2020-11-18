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

  public Message(String message, Company toCompany, Company fromCompany)
  {
    this.message = message;
    this.fromCompany = fromCompany;
    this.toCompany = toCompany;
    timestamp = new Timestamp(System.currentTimeMillis());
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
