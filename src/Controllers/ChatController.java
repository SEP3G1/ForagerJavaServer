package Controllers;

import Models.Company;
import Models.Message;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController implements IChatController
{
  private  ICompanyController companyController;
  private IUserController userController;
  public ChatController(ICompanyController companyController, IUserController userController)
  {
    this.companyController = companyController;
    this.userController = userController;
  }

  @Override public Message generateMessage(String mstring)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    try
    {
      ArrayList<String> rinfo = objectMapper.readValue(mstring, new TypeReference<ArrayList<String>>(){});
      Company fromCompany = objectMapper.readValue(companyController.getCompany(rinfo.get(2)), new TypeReference<Company>(){});
      User user = userController.getUser(rinfo.get(1));
      Company toCompany = objectMapper.readValue(companyController.getCompany(user.getCompanyId() + ""), new TypeReference<Company>(){});

      Message message = new Message(rinfo.get(0), toCompany, fromCompany); // INSERT TOCOMPANY PLS
      return message;
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    } return null;
  }
}
