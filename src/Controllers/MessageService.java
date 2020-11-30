package Controllers;

import Models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
  }

  public static MessageService getInstance()
  {
    if (messageService_instance == null)
      messageService_instance = new MessageService();

    return messageService_instance;
  }
  public void addNewMessage(Message message){
    if (!messages.contains(message))
    {
      messages.add(message);
      try {
        FileWriter myWriter = new FileWriter("messages.txt");
        ObjectMapper mapper = new ObjectMapper();
        myWriter.write(mapper.writeValueAsString(message));
        myWriter.close();
      } catch (IOException e) {
        System.out.println("An error occurred while writing message to file.");
        e.printStackTrace();
      }
    }
  }
  public int unreadMessages(){
    return messages.size();
  }

  public ArrayList<Message> getConversation(int listingId){
    ArrayList<Message> _conversations = new ArrayList<>();
    ArrayList<Message> _messages = messages;
    _messages.addAll(messages);
    for (Message m:
         _messages)
    {
      if (m.getListingId() == listingId)
        _conversations.add(m);
    }
    Collections.reverse(_conversations);
    return _conversations;
  }
}
