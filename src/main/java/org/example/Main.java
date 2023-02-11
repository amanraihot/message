package org.example;

import com.twilio.rest.api.v2010.account.Message;
import org.example.cache.ContactCache;
import org.example.dto.MessageAnalyticsFilter;
import org.example.dto.UserContactDto;
import org.example.enums.MessageStatus;
import org.example.models.Contact;
import org.example.models.User;
import org.example.service.AnalyticService;
import org.example.service.MessageService;
import org.example.service.UserService;
import org.example.service.impl.AnalyticServiceImpl;
import org.example.service.impl.MessageServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        MessageService messageService = new MessageServiceImpl();

        UserService userService = new UserServiceImpl();
        ContactCache contactCache = ResourceFactory.getContactCache();
        AnalyticService analyticService = new AnalyticServiceImpl();
        MessageAnalyticsFilter messageAnalyticsFilter = new MessageAnalyticsFilter();
        Map<String, Contact> contactByUserId = contactCache.getContactByUserId();

        User user = User.builder().name("Aman Rai").phoneNo("+19066805490").build();
        user = userService.createUser(user);
        UserContactDto userContactDto = UserContactDto.builder().userName("Aman Secondary").contactPhoneNumber("+917275096759").userId(user.getUserId()).build();
        userService.addPhoneNumberToContact(userContactDto);

        UserContactDto userContactDto1 = UserContactDto.builder().userName("Adarsh").contactPhoneNumber("+917002620912").userId(user.getUserId()).build();
        userService.addPhoneNumberToContact(userContactDto1);
        UserContactDto userContactDto2 = UserContactDto.builder().userName("Adarsh").contactPhoneNumber("+918887616912").userId(user.getUserId()).build();
        userService.addPhoneNumberToContact(userContactDto2);
        Contact contact = contactByUserId.get(user.getUserId());
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917275096759").getPhoneNo()), "Hey");
        messageAnalyticsFilter.setMessageStatuses(Arrays.asList(MessageStatus.SENT,MessageStatus.FAILED));
        analyticService.showCountOfMessages(messageAnalyticsFilter);
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917275096759").getPhoneNo()), "  Hey");
        analyticService.showCountOfMessages(messageAnalyticsFilter);
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917275096759").getPhoneNo()), "  Hey");
        analyticService.showCountOfMessages(messageAnalyticsFilter);
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917002620912").getPhoneNo()), "Hola");
        analyticService.showCountOfMessages(messageAnalyticsFilter);
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917002620912").getPhoneNo()), " Hola");
        analyticService.showCountOfMessages(messageAnalyticsFilter);
        messageService.sendMessage(user.getUserId(), Arrays.asList(contact.getUserMap().get("+917002620912").getPhoneNo(),contact.getUserMap().get("+918887616912").getPhoneNo()), "  Hi");
        analyticService.showCountOfMessages(messageAnalyticsFilter);
    }
}