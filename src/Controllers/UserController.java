package Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserController implements IUserController
{
  private ICommunicationController communicationController;
  private BufferedReader rd;

  public UserController(ICommunicationController communicationController)
  {
    this.communicationController = communicationController;
  }

  @Override public String login(String q) throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<String> r = objectMapper.readValue(q, new TypeReference<>()
    {
    });

    rd = communicationController.HttpGetRequest("/api/userlogin?email=" + r.get(0) + "&pass=" + r.get(1));

    String line = "";
    while ((line = rd.readLine()) != null) {
      return line;
    }
    return "Something Really Bad Happened";
  }
}
