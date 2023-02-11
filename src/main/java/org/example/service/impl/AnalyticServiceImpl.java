package org.example.service.impl;

import org.example.ResourceFactory;
import org.example.cache.MessageCache;
import org.example.dto.MessageAnalyticsFilter;
import org.example.enums.MessageStatus;
import org.example.models.CustomMessage;
import org.example.service.AnalyticService;
import org.example.util.PrintUtil;

import java.util.List;
import java.util.Map;

public class AnalyticServiceImpl implements AnalyticService {
    private MessageCache messageCache = ResourceFactory.getMessageCache();
    @Override
    public void showCountOfMessages(MessageAnalyticsFilter messageAnalyticsFilter) {
        Map<MessageStatus, List<CustomMessage>> messageStatusListMap = messageCache.getMessageStatusListMap();
        if(!messageAnalyticsFilter.getMessageStatuses().isEmpty()){
            for(MessageStatus messageStatus : messageAnalyticsFilter.getMessageStatuses()){
                int count = 0;
                if(messageStatusListMap.containsKey(messageStatus))
                    count = messageStatusListMap.get(messageStatus).size();
                PrintUtil.print(messageStatus.toString() + ":" + count);
            }
        }
    }
}
