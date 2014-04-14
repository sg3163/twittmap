package edu.columbia.cs.test;

import java.io.IOException;
import java.util.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

public class TwitterDownloader {
	
	public static void main(String args[]) throws IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("asdf")  // Copy all the twitter API keys accordingly
		.setOAuthConsumerSecret("asdf")
		.setOAuthAccessToken("asdf-asdf")
		.setOAuthAccessTokenSecret("afds");

		// The factory instance is re-useable and thread safe.
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            Query query = new Query("soccer"); // Search tweets for particular words
            QueryResult result;
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {				
					System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
					// You can store the tweets in datastore                   
                }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
	}

}