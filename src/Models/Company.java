package Models;

import java.util.ArrayList;

public class Company
{
  private int companyId;
  private String cvr;
  private double trustScore;
  private int numberOfVotes;
  private String name;
  private String address;
  private String postCode;
  private String logo;
  private boolean wishDeletion;
  private String connectionAddress;
  private ArrayList<Employee> employees;

  public Company(){
    //Required for jackson to serialize company
    super();
  }

  public Company(int companyId, String cvr, double trustScore,
      int numberOfVotes, String name, String address, String postCode,
      String logo, boolean wishDeletion, String connectionAddress, ArrayList<Employee> employees)
  {
    this.companyId = companyId;
    this.cvr = cvr;
    this.trustScore = trustScore;
    this.numberOfVotes = numberOfVotes;
    this.name = name;
    this.address = address;
    this.postCode = postCode;
    this.logo = logo;
    this.wishDeletion = wishDeletion;
    this.connectionAddress = connectionAddress;
    this.employees = employees;
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

  public String getAddress()
  {
    return address;
  }

  public String getPostCode()
  {
    return postCode;
  }

  public String getLogo()
  {
    return logo;
  }

  public boolean getWishDeletion() {
    return wishDeletion;
  }

  public String getConnectionAddress()
  {
    return connectionAddress;
  }

  public ArrayList<Employee> getEmployees()
  {
    return employees;
  }
}
