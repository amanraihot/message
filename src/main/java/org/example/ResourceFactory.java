package org.example;


import org.example.cache.ContactCache;
import org.example.cache.MessageCache;
import org.example.cache.UserCache;

public class ResourceFactory {

    private ResourceFactory resourceFactory = new ResourceFactory();

    private ResourceFactory(){

    }

    private static ContactCache contactCache;
    private static MessageCache  messageCache;
    private static UserCache userCache;

    public static ContactCache getContactCache() {
        if(contactCache == null){
            contactCache = new ContactCache();
        }
        return contactCache;
    }

    public static MessageCache getMessageCache() {
        if(messageCache == null){
            messageCache = new MessageCache();
        }
        return messageCache;
    }


    public static UserCache  getUserCache() {
        if(userCache == null){
            userCache = new UserCache();
        }
        return userCache;
    }
}

