package Controllers;

import Config.Config;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommunicationsController implements ICommunicationController
{
  private HttpClient client;

  public CommunicationsController()
  {
    this.client = new DefaultHttpClient();
  }

  @Override public BufferedReader HttpGetRequest(String uri) throws IOException
  {
    // HTTP Request
    HttpGet request = new HttpGet("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + uri);

    // Executes the HTTP server request
    HttpResponse response = client.execute(request);

    // Get the value of the header in the HTTP request. Expected 200 OK
    String status = String.valueOf(response.getStatusLine());
    System.out.println("Response: " + status);

    // Reads the body of the HTTP response
    return new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
  }

  @Override public BufferedReader HttpPostRequest(String uri) throws IOException
  {
    // HTTP Request
    HttpPost request = new HttpPost("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + uri);

    // Executes the HTTP server request
    HttpResponse response = client.execute(request);

    // Get the value of the header in the HTTP request. Expected 200 OK
    String status = String.valueOf(response.getStatusLine());
    System.out.println("Response: " + status);

    // Reads the body of the HTTP response
    return new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
  }

  @Override
  public BufferedReader HttpPutRequest(String uri) throws IOException {
    // HTTP Request
    HttpPut request = new HttpPut("http://"+ Config.IP_T3 + ":" + Config.PORT_T3 + uri);

    // Executes the HTTP server request
    HttpResponse response = client.execute(request);

    // Get the value of the header in the HTTP request. Expected 200 OK
    String status = String.valueOf(response.getStatusLine());
    System.out.println("Response: " + status);

    // Reads the body of the HTTP response
    return new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
  }
}
