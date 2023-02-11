package org.example.service;

import org.example.dto.MessageAnalyticsFilter;

public interface AnalyticService {

    void showCountOfMessages(MessageAnalyticsFilter messageAnalyticsFilter);
}
