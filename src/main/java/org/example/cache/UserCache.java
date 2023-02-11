package org.example.cache;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCache {
    private Map<String, User> userMapById = new HashMap<>();
    private Map<String, User> userMapByNumber = new HashMap<>();


}
