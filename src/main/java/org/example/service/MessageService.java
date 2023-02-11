package org.example.service;

import java.util.List;

public interface MessageService {

     void sendMessage(String userId, List<String> recipientsUserId, String text);

     void test();
}
