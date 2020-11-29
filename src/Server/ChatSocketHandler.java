package Server;

import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatSocketHandler extends Thread
{
  private Socket socket;
  private Message message;
  public ChatSocketHandler(Message message, Socket clientSocket) throws IOException
  {
    this.message = message;
    socket = clientSocket;
  }

  @Override public void run()
      {
      try{

        OutputStream outputStream = socket.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();

        String[] toSend = {"recieveMessage", mapper.writeValueAsString(message)};

        byte[] toSendBytes = mapper.writeValueAsString(toSend).getBytes();
        int toSendLen = toSendBytes.length;
        byte[] toSendLenBytes = new byte[4];
        toSendLenBytes[0] = (byte) (toSendLen & 0xff);
        toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
        toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
        toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
        outputStream.write(toSendLenBytes);
        outputStream.write(toSendBytes);

        outputStream.flush();
        outputStream.close();
      }
      catch (JsonProcessingException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      }
}
