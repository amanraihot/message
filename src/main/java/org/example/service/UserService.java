package org.example.service;

import org.example.dto.UserContactDto;
import org.example.models.Contact;
import org.example.models.User;

public interface UserService {

   User createUser(User user);

   void addPhoneNumberToContact(UserContactDto userContactDto);
}
