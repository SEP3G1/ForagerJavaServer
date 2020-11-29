package Controllers;

import Models.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class MessageService
{
  private static MessageService messageService_instance = null;
  private ArrayList<Message> messages;
  private ArrayList<Message> unreadMessages;
  private ArrayList<Integer> conversations;

  private MessageService(){
    messages = new ArrayList<>();
    unreadMessages = new ArrayList<>();
    conversations = new ArrayList<>();
  }

  public static MessageService getInstance()
  {
    if (messageService_instance == null)
      messageService_instance = new MessageService();

    return messageService_instance;
  }
  public void addNewMessage(Message message){
    if (!unreadMessages.contains(message))
      unreadMessages.add(message);
    if (!conversations.contains(message.getListingId()))
      conversations.add(message.getListingId());
  }
  public int unreadMessages(){
    return unreadMessages.size();
  }

  public ArrayList<Message> getConversation(int listingId){
    ArrayList<Message> _conversations = new ArrayList<>();
    ArrayList<Message> _messages = unreadMessages;
    _messages.addAll(messages);
    for (Message m:
         _messages)
    {
      if (m.getListingId() == listingId)
        _conversations.add(m);
    }
    return _conversations;
  }
}
