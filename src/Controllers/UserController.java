package Controllers;

import Config.Config;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserController implements IUserController
{
  private HttpClient client;

  public UserController(HttpClient client)
  {
    this.client = client;
  }

  @Override public String login(String q) throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<String> r = objectMapper.readValue(q, new TypeReference<>()
    {
    });
    HttpClient client = new DefaultHttpClient();

    // Creates the request for the HTTP server
    HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + "/api/userlogin?email=" + r.get(0) + "&pass=" + r.get(1));

    // Executes the HTTP server request
    HttpResponse response = client.execute(request);

    // Get the value of the header in the HTTP request. Expected 200 OK
    String status = String.valueOf(response.getStatusLine());
    System.out.println("Response: " + status);

    // Reads the body of the HTTP response
    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

    String line = "";

    while ((line = rd.readLine()) != null) {
      System.out.println(line);
      return line;
    }
    return "Not implemented";
  }
}
