package Models;

public class Company
{
  private int companyId;
  private String cvr;
  private double trustScore;
  private int numberOfVotes;
  private String name;
  private String location;
  private String logo;
  private String connectionAddress;

  public Company(int companyId, String cvr, double trustScore,
      int numberOfVotes, String name, String location, String logo,
      String connectionAddress)
  {
    this.companyId = companyId;
    this.cvr = cvr;
    this.trustScore = trustScore;
    this.numberOfVotes = numberOfVotes;
    this.name = name;
    this.location = location;
    this.logo = logo;
    this.connectionAddress = connectionAddress;
  }

  public int getCompanyId()
  {
    return companyId;
  }

  public String getCvr()
  {
    return cvr;
  }

  public double getTrustScore()
  {
    return trustScore;
  }

  public int getNumberOfVotes()
  {
    return numberOfVotes;
  }

  public String getName()
  {
    return name;
  }

  public String getLocation()
  {
    return location;
  }

  public String getLogo()
  {
    return logo;
  }

  public String getConnectionAddress()
  {
    return connectionAddress;
  }

}
