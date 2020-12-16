package Controllers;

import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MessageService
{
  private static MessageService messageService_instance = null;
  private ArrayList<Message> messages;

  private MessageService(){
    messages = new ArrayList<>();
    try {
      File myObj = new File("messages.txt");
      myObj.createNewFile();
      Scanner myReader = new Scanner(myObj);
      ObjectMapper mapper = new ObjectMapper();
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        messages.add(mapper.readValue(data, Message.class));
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred while reading messages from file");
      e.printStackTrace();
    }
    catch (JsonMappingException e)
    {
      e.printStackTrace();
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

  public static MessageService getInstance()
  {
    if (messageService_instance == null)
      messageService_instance = new MessageService();

    return messageService_instance;
  }
  public void addNewMessage(Message message){
    if (!messages.contains(message) && !messages.get(messages.size()-1).getMessage().equals(message.getMessage()))
    {
      messages.add(message);
      System.out.println("writes to file: " + message.getMessage());
      ObjectMapper mapper = new ObjectMapper();
      try(FileWriter fw = new FileWriter("messages.txt", true);
          BufferedWriter bw = new BufferedWriter(fw);
          PrintWriter out = new PrintWriter(bw))
      {
        out.println(mapper.writeValueAsString(message));
      } catch (IOException e) {
        //exception handling left as an exercise for the reader
      }
    }
  }
  public int unreadMessages(){
    return messages.size();
  }

  public ArrayList<Message> getConversation(int listingId){
    ArrayList<Message> _conversations = new ArrayList<>();
    for (Message m:
         messages)
    {
      if (m.getListingId() == listingId)
        _conversations.add(m);
    }
    Collections.reverse(_conversations);
    return _conversations;
  }
}
