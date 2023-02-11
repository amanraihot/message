package org.example.service.impl;

import org.example.Main;
import org.example.ResourceFactory;
import org.example.cache.ContactCache;
import org.example.cache.UserCache;
import org.example.dto.UserContactDto;
import org.example.models.Contact;
import org.example.models.User;
import org.example.service.UserService;
import org.example.util.PrintUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserCache userCache = ResourceFactory.getUserCache();
    private ContactCache contactCache = ResourceFactory.getContactCache();

    @Override
    public User createUser(User user) {
        Map<String, User> userMapByNumber = userCache.getUserMapByNumber();
        Map<String, Contact> contactByUserId = contactCache.getContactByUserId();

        Map<String, User> userMapById = userCache.getUserMapById();
        if (userMapByNumber.containsKey(user.getPhoneNo())){
            PrintUtil.print("User already Exists");
            return userMapByNumber.get(user.getPhoneNo());
        }
        User newUser  = User.builder().name(user.getName()).phoneNo(user.getPhoneNo()).userId(UUID.randomUUID().toString()).build();
        userMapByNumber.put(newUser.getPhoneNo(), newUser);
        userMapById.put(newUser.getUserId(), newUser);
        Contact contact = Contact.builder().userId(newUser.getUserId()).userMap(new HashMap<>()).build();
        contactByUserId.put(newUser.getUserId(),contact);
        return newUser;
    }

    @Override
    public void addPhoneNumberToContact(UserContactDto userContactDto) {
        Map<String, Contact> contactByUserId = contactCache.getContactByUserId();
        if(!contactByUserId.containsKey(userContactDto.getUserId())){
                PrintUtil.print("No user  Exists with id" + userContactDto.getUserId());
                return;
            }

        Contact contact = contactByUserId.get(userContactDto.getUserId());
        Map<String, User> userMap = contact.getUserMap();
        if(userMap.containsKey(userContactDto.getContactPhoneNumber())){
            PrintUtil.print("Phone number  already exists in contacts" + userContactDto.getUserId());
            return;
        }

        User user = User.builder().name(userContactDto.getUserName()).phoneNo(userContactDto.getContactPhoneNumber()).userId(UUID.randomUUID().toString()).build();
        userMap.put(user.getPhoneNo(), user);
    }


}
