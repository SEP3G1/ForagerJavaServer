package Controllers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatController extends Remote
{
  void Send(String mstring) throws RemoteException;
}
