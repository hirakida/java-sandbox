package com.example;

import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketSession;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;

@JBot
public class SlackBot extends Bot {
    private final String slackToken;

    public SlackBot(@Value("${slackBotToken}") String slackToken) {
        this.slackToken = slackToken;
    }

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = { EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE })
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName());
    }

    @Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
    public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
        reply(session, event, "First group: " + matcher.group(0) + '\n' +
                              "Second group: " + matcher.group(1) + '\n' +
                              "Third group: " + matcher.group(2) + '\n' +
                              "Fourth group: " + matcher.group(3));
    }

    @Controller(events = EventType.PIN_ADDED)
    public void onPinAdded(WebSocketSession session, Event event) {
        reply(session, event, "Pin added.");
    }

    @Controller(events = EventType.PIN_REMOVED)
    public void onPinRemoved(WebSocketSession session, Event event) {
        reply(session, event, "Pin removed.");
    }

    @Controller(events = EventType.FILE_SHARED)
    public void onFileShared(WebSocketSession session, Event event) {
        reply(session, event, "File shared.");
    }

    /**
     * Conversation
     */
    @Controller(pattern = "(start conversation)", next = "conversation2")
    public void conversation1(WebSocketSession session, Event event) {
        startConversation(event, "conversation2");
        reply(session, event, "Start conversation.");
    }

    @Controller(next = "conversation3")
    public void conversation2(WebSocketSession session, Event event) {
        reply(session, event, "text: " + event.getText());
        nextConversation(event);
    }

    @Controller
    public void conversation3(WebSocketSession session, Event event) {
        reply(session, event, "Stop conversation. " + event.getText());
        stopConversation(event);
    }
}
