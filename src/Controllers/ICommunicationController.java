package Controllers;

import java.io.BufferedReader;
import java.io.IOException;

public interface ICommunicationController
{
  BufferedReader HttpGetRequest(String uri) throws IOException;
  BufferedReader HttpPostRequest(String uri) throws IOException;
}
