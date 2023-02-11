package org.example.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.Contact;
import org.example.models.User;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactCache {
    private Map<String, Contact> contactByUserId = new HashMap<>();
}
