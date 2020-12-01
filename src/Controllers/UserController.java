package Controllers;

import Models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

  @Override public User getUser(String id) throws IOException
  {
    rd = communicationController.HttpGetRequest("/api/userlogin/" + id);

    String line = "";
    ObjectMapper objectMapper = new ObjectMapper();
    User user = null;
    //Read body
    while ((line = rd.readLine()) != null) {
      if (line != null){
        //Map listing to object
        user = objectMapper.readValue(line, User.class);
      }
    }

    return user;
  }

  @Override public String getUserAsString(String id) throws IOException
  {
    User u = getUser(id);
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(u);
  }

  @Override public String createUser(String str) throws IOException {
    System.out.println("Jeg har ramt t2");
      String query = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
      rd = communicationController.HttpPostRequest("/api/userlogin?userAsString=" + query);

      String line = "";
      while ((line = rd.readLine()) != null) {
        System.out.println(line);
        return line;
      }
      return "Something Really Bad Happened";
    }


}
