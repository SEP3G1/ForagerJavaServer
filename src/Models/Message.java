package Models;

import Config.Config;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable
{
  private String message;
  private String fromCompanyIp;
  private int fromCompanyId;
  private String toCompanyIp;
  private Timestamp timestamp;

  public Message(String message, String toCompany)
  {
    this.message = message;
    this.fromCompanyIp = Config.IP_T2 + ":" + Config.PORT_T2;
    this.toCompanyIp = toCompany;
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public String getMessage()
  {
    return message;
  }

  public String getFromCompanyIp()
  {
    return fromCompanyIp;
  }

  public String getToCompanyIp()
  {
    return toCompanyIp;
  }

  public Timestamp getTimestamp()
  {
    return timestamp;
  }
}
