package org.example.service.impl;

//import com.twilio.rest.api.v2010.account.Message;
import com.ctc.wstx.util.StringUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.example.ResourceFactory;
import org.example.cache.ContactCache;
import org.example.cache.MessageCache;
import org.example.cache.UserCache;
        import org.example.enums.MessageStatus;
import org.example.models.Contact;

import org.example.models.CustomMessage;
        import org.example.models.User;
import org.example.service.MessageService;
import org.example.util.PrintUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.config.TwilloSmsConfig.ACCOUNT_SID;
import static org.example.config.TwilloSmsConfig.AUTH_TOKEN;

public class MessageServiceImpl implements MessageService {

    private UserCache userCache = ResourceFactory.getUserCache();
    private MessageCache messageCache = ResourceFactory.getMessageCache();
    private ContactCache contactCache = ResourceFactory.getContactCache();

    @Override
    public void sendMessage(String userId, List<String> recipientsUserId, String text) {
        Map<String, User> userMapById = userCache.getUserMapById();
        Map<String, Contact> contactByUserId = contactCache.getContactByUserId();
        if(!userMapById.containsKey(userId))
            throw new RuntimeException("User Does not exists with user id" + userId);
        User srcUser = userMapById.get(userId);
        Contact contact = contactByUserId.get(userId);
        List<User> recipientUser = new ArrayList<>();
        for(String f : recipientsUserId){
            if(contact.getUserMap().containsKey(f))
                recipientUser.add(contact.getUserMap().get(f));
        }
        Map<String, List<CustomMessage>> messageByUserId = messageCache.getMessageByUserId();
        List<CustomMessage> customMessageList = messageByUserId.containsKey(userId) ? messageByUserId.get(userId) : new ArrayList<>();
      try {
        validateMessage(customMessageList, text);
        for(User user : recipientUser){
            CustomMessage currentCustomMessage = CustomMessage.builder().senderId(userId).receiverPhoneNumber(user.getPhoneNo()).message(text).build();
            try {
                send(currentCustomMessage);
                currentCustomMessage.setMessageStatus(MessageStatus.SENT);
            }catch (Exception e){
                currentCustomMessage.setMessageStatus(MessageStatus.FAILED);
            }
            messageCache.updateMessageCache(userId, currentCustomMessage);
        }}catch (Exception e){
          PrintUtil.print(e.getMessage());
      }
    }

    @Override
    public void test() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+918887616912"),
                        new com.twilio.type.PhoneNumber("+19066805490"),
                        "Where's Wallace?")
                .create();

        System.out.println(message.getSid());
    }


    private void send(CustomMessage customMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(customMessage.getReceiverPhoneNumber()),
                        new com.twilio.type.PhoneNumber("+19066805490"),
                        customMessage.getMessage())
                .create();

        if(Objects.nonNull(message.getErrorMessage()))
            throw new RuntimeException("Message not sent");
    }


    private void validateMessage(List<CustomMessage> customMessageList, String text) {
        if(customMessageList.isEmpty())
            return;

        if(Objects.isNull(text))
            throw new RuntimeException("You can not send empty message");

        Set<String> messagesSet = new HashSet<>();
        String[] textArray = getFilteredMessage(text);
        List<CustomMessage> deliveredCustomMessages = customMessageList.stream().toList().stream().filter(f -> MessageStatus.SENT.equals(f.getMessageStatus())).collect(Collectors.toList());
        if(deliveredCustomMessages.isEmpty())
            return;
        for(CustomMessage customMessage : deliveredCustomMessages) {
            String[] result = getFilteredMessage(customMessage.getMessage());
            if(compareString(result,textArray)){
                throw  new RuntimeException("Message was already sent earlier");
            }
        }
    }


    private boolean compareString(String[] result,  String[] textArray){
      if(result.length != textArray.length)
          return false;
      int score = 0;
      for(int i = 0; i < result.length; i++){
          if(result[i].equalsIgnoreCase(textArray[i]))
              score++;
      }
       return score == result.length;
    }

    private String[]  getFilteredMessage(String text) {
        String[] textArray = text.split(" ");
        List<String> stringList = new ArrayList<>();
        for(int i = 0; i < textArray.length; i++){
            if(textArray[i].equals(""))
                continue;
            stringList.add(textArray[i]);
        }
        String[] result = new String[stringList.size()];
        int count = 0;
        for(String s : stringList){
            result[count] = s;
            count++;
        }
        return result;


    }
}
