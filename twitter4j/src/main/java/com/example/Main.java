package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();

        User user = twitter.verifyCredentials();
        log.info("{}", user);

        ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
        log.info("{}", homeTimeline);
    }
}
