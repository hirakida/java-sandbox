package com.example;

import org.springframework.stereotype.Component;

import com.example.config.SlackProperties;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SlackMessagePostedListenerImpl implements SlackMessagePostedListener {
    private final SlackProperties slackProperties;

    public SlackMessagePostedListenerImpl(SlackSession slackSession, SlackProperties slackProperties) {
        slackSession.addMessagePostedListener(this);
        this.slackProperties = slackProperties;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        String messageContent = event.getMessageContent();
        SlackUser sender = event.getSender();
        log.info("{} {} {}", event, messageContent, sender);

        SlackUser slackUser = session.findUserByUserName(slackProperties.getBotUserName());
        if (!slackUser.getId().equals(sender.getId())) {
            session.sendMessage(event.getChannel(), messageContent);
        }
    }
}
