package org.example.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.MessageStatus;
import org.example.models.CustomMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageCache {

    private List<CustomMessage> customMessageList = new ArrayList<>();
    private Map<MessageStatus, List<CustomMessage>> messageStatusListMap = new HashMap<>();
    private Map<String, List<CustomMessage>> messageByUserId = new HashMap<>();


    public void updateMessageCache(String userId, CustomMessage customMessage){
        this.customMessageList.add(customMessage);
        List<CustomMessage> customMessages = messageStatusListMap.containsKey(customMessage.getMessageStatus()) ? messageStatusListMap.get(customMessage.getMessageStatus()) : new ArrayList<>();
        customMessages.add(customMessage);
        this.messageStatusListMap.put(customMessage.getMessageStatus(), customMessages);
        List<CustomMessage> messagesByUser = messageByUserId.containsKey(userId) ? messageByUserId.get(userId) : new ArrayList<>();
        messagesByUser.add(customMessage);
        messageByUserId.put(userId, messagesByUser);
    }
}
