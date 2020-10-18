package com.example;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.SlackProperties;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SlackMessageController {
    private final SlackSession slackSession;
    private final SlackProperties slackProperties;

    @PostMapping
    public SlackMessageHandle<SlackMessageReply> sendMessage(@RequestBody MessageRequest request) {
        SlackChannel channel = slackSession.findChannelByName(slackProperties.getChannel());
        return slackSession.sendMessage(channel, request.getMessage());
    }

    @Data
    public static class MessageRequest {
        private String message;
    }
}
