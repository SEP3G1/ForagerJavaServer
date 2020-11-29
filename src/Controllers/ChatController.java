package Controllers;

import Models.Company;
import Models.Message;
import Models.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatController implements IChatController
{
  private  ICompanyController companyController;
  private IUserController userController;
  private ObjectMapper mapper;

  public ChatController(ICompanyController companyController, IUserController userController)
  {
    this.companyController = companyController;
    this.userController = userController;
    mapper  = new ObjectMapper();
  }

  @Override public Message generateMessage(String mstring)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    try
    {
      ArrayList<String> rinfo = objectMapper.readValue(mstring, new TypeReference<ArrayList<String>>(){});
      Company toCompany = objectMapper.readValue(companyController.getCompany(rinfo.get(2)), new TypeReference<Company>(){});
      User user = userController.getUser(rinfo.get(1));
      Company fromCompany = objectMapper.readValue(companyController.getCompany(user.getCompanyId() + ""), new TypeReference<Company>(){});

      Message message = new Message(rinfo.get(0), toCompany, fromCompany,
          System.currentTimeMillis() + "", Integer.parseInt(rinfo.get(3)));
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

  @Override public String receiveMessage(String str)
      throws JsonProcessingException
  {
    Message message = mapper.readValue(str, Message.class);
    MessageService.getInstance().addNewMessage(message);
    System.out.println("Message: " + message.getMessage() + ", From: " + message.getFromCompany().getName());

    return mapper.writeValueAsString(message);
  }

  @Override public String getConversation(String id)
      throws JsonProcessingException
  {
    return mapper.writeValueAsString(MessageService.getInstance().getConversation(Integer.parseInt(id)));
  }

  @Override public String unreadMessages()
  {
    return MessageService.getInstance().unreadMessages() + "";
  }
}
