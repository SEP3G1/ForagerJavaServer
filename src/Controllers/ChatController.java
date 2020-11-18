package Controllers;

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

  @Override public void Send(String mstring)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    Message message;
    try
    {
      ArrayList<String> rinfo = objectMapper.readValue(mstring, new TypeReference<ArrayList<String>>(){});

      message = new Message(rinfo.get(0), "GetCompanyFromUserID");
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }


  }
}
