package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.MessageStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomMessage {
    private String message;
    private String senderId;
    private String receiverPhoneNumber;
    private MessageStatus messageStatus;

}
