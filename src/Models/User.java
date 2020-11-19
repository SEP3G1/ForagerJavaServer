package Models;

public class User
{
  private String userName;
  private String domain;
  private String city;
  private int birthYear;
  private String role;
  private int securityLevel;
  private String password;
  private int id;
  private int companyId;

  public int getCompanyId()
  {
    return companyId;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getDomain()
  {
    return domain;
  }

  public void setDomain(String domain)
  {
    this.domain = domain;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public int getBirthYear()
  {
    return birthYear;
  }

  public void setBirthYear(int birthYear)
  {
    this.birthYear = birthYear;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public int getSecurityLevel()
  {
    return securityLevel;
  }

  public void setSecurityLevel(int securityLevel)
  {
    this.securityLevel = securityLevel;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  @Override public String toString()
  {
    return "User{" + "userName='" + userName + '\'' + ", password='" + password
        + '\'' + '}';
  }
}
