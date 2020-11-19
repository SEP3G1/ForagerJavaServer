package Models;

public class User
{
  private String name;
  private String email;
  private int securityLevel;
  private String password;
  private int userId;
  private int companyId;

  public  User()
  {
    super();
  }

  public User(String name, String email, int securityLevel, String password,
      int userId, int companyId)
  {
    this.name = name;
    this.email = email;
    this.securityLevel = securityLevel;
    this.password = password;
    this.userId = userId;
    this.companyId = companyId;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public int getSecurityLevel()
  {
    return securityLevel;
  }

  public String getPassword()
  {
    return password;
  }

  public int getUserId()
  {
    return userId;
  }

  public int getCompanyId()
  {
    return companyId;
  }
}
