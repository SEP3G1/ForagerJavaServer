package Controllers;

import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IChatController extends Remote
{
  Message generateMessage(String mstring) throws RemoteException;
  Message receiveMessage(String str) throws JsonProcessingException;

  String getAllMessages(int max) throws JsonProcessingException;
}
