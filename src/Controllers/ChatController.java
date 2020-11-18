package Controllers;

import Models.Company;
import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ChatController implements IChatController
{
  public ChatController()
  {
  }

  @Override public Message generateMessage(String mstring)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    try
    {
      ArrayList<String> rinfo = objectMapper.readValue(mstring, new TypeReference<ArrayList<String>>(){});
      Company fromCompany = objectMapper.readValue(rinfo.get(2), new TypeReference<Company>(){});

      Message message = new Message(rinfo.get(0), null, fromCompany); // INSERT TOCOMPANY PLS
      return message;
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
