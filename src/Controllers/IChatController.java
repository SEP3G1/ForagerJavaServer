package Controllers;

import Models.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatController extends Remote
{
  Message generateMessage(String mstring) throws RemoteException;
}
