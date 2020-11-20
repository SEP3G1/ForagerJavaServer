package Controllers;

import Models.User;

import java.io.IOException;

public interface IUserController
{
  String login(String q) throws IOException;

  User getUser(String id) throws  IOException;
  String getUserAsString(String id) throws  IOException;
}
